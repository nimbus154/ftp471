package cpsc471.ftp.client.control;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Test for the control client
 */
@Test
public class ControlClientTest {

    private ControlClientImpl client;
    private Socket socket;

    private ByteArrayOutputStream outputStream;

    @BeforeMethod
    public void setUp() throws Exception {

        // mock the socket
        socket = mock(Socket.class);

        // when socket method calls, return test objects
        // this allows for complete control over the internal socket and state
        outputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(outputStream);
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

    /**
     * Execute the ls command
     */
    public void testLs() throws Exception {

        client.setSocket(socket);

        client.ls();
    }
}
