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

    @BeforeMethod
    public void setUp() {

        ControlClient client = mock(ControlClient.class);
        shell = new Shell(client);
    }

    /**
     * Quit when "quit" is passed
     */
    public void testQuit() {

        String[] args = {"quit"};
        Assert.assertEquals(shell.invokeCmd(args), false);
    }



}
