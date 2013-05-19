package cpsc471.ftp.client.control;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.Socket;
import java.net.UnknownHostException;

import static org.mockito.Mockito.mock;

/**
 * Test for the control client
 */
@Test
public class ControlClientTest {

    private ControlClient client;
    private Socket socket;

    @BeforeMethod
    public void setUp() {

        client = new ControlClientImpl();
        socket = mock(Socket.class);
    }

    /**
     * Instantiate client with invalid domain name
     */
    @Test(expectedExceptions = UnknownHostException.class)
    public void testInvalidDomainName() throws Exception {

        new ControlClientImpl("@%$!", (short)12);
    }

    /**
     * Valid domain name
     */
    public void testValidDomainName() throws Exception {

        // todo find a better way to test
        // these values are important. This test actually establishes
        // a socket with Google. There must be a better way to do this.
        // new ControlClientImpl("google.com", (short)80);
    }
}
