package cpsc471.ftp.client.control;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * Test for Client Control shell
 */
@Test
public class ShellTest {

    private Shell shell;

    private ControlClient client;

    @BeforeMethod
    public void setUp() {

        client = mock(ControlClient.class);
        shell = new Shell(client);
    }

    /**
     * Quit when "quit" is passed
     */
    public void testQuit() {

        String[] args = {"quit"};

        Assert.assertEquals(shell.invokeCmd(args), false,
                "The quit command should cause invokeCmd to return false.");

        // invoking the quit command should call the client's quit method
        verify(client, times(1)).quit();
    }

    /**
     * Enter an invalid command
     */
    public void testBadCommand() {

        String[] args = {"test"};
        Shell shellSpy = spy(shell);

        boolean continueExec = shellSpy.invokeCmd(args);

        // an invalid command should not terminate the shell
        Assert.assertEquals(continueExec, true,
                "An arbitrary command should not cause the shell to exit");
        // an interactive command should have invoked the help method
        verify(shellSpy, times(1)).help();
    }


    /**
     * Get command
     */
    public void testGet() {

        String[] args = {"get", "remoteFile"};
        // Get command should stay in shell
        Assert.assertEquals(shell.invokeCmd(args), true,
                "Shell should run after GET invoked");
        // an interactive command should have invoked the help method
        verify(client, times(1)).get(args[1]);
    }

    /**
     * Get command invalid number of args
     */
    public void testGetInvalidNumArgs() {

        String[] args = {"get", "remoteFile", "remoteFile2"};
        // Get command should stay in shell
        Assert.assertEquals(shell.invokeCmd(args), true,
                "Shell should run after GET invoked");
        // get should not have been invoked at all
        verify(client, times(0)).get(anyString());
    }

    /**
     * Invoke the ls command
     */
    public void testLs() {

        String[] args = {"ls"};
        // Get command should stay in shell
        Assert.assertEquals(shell.invokeCmd(args), true,
                "Shell should run after ls invoked");
        // get should not have been invoked at all
        verify(client, times(1)).ls();
    }

//    /**
//     * Put command with file not found
//     */
//    public void testPutFileNotFound() {
//
//        String[] args = {"put", "notAFile"};
//        // Get command should stay in shell
//        Assert.assertEquals(shell.invokeCmd(args), true,
//                "Shell should run after PUT invoked");
//        // put should not have been invoked at all
//    }
}
