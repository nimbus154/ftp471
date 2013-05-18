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
    public void testBasCommand() {

        String[] args = {"test"};
        Shell shellSpy = spy(shell);

        boolean continueExec = shellSpy.invokeCmd(args);

        // an invalid command should not terminate the shell
        Assert.assertEquals(continueExec, true,
                "An arbitrary command should not cause the shell to exit");
        // an interactive command should have invoked the help method
        verify(shellSpy, times(1)).help();
    }

}
