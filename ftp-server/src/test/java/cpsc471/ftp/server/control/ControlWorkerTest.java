package cpsc471.ftp.server.control;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

/**
 * Tests for the ControlWorker server class
 */
@Test
public class ControlWorkerTest {

    private Socket socket;

    private ControlWorker worker;

    private ByteArrayOutputStream outputStream;

    private ByteArrayInputStream inputStream;

    // thanks to http://stackoverflow.com/questions/5577274/testing-java-sockets
    // for the testing pattern

    @BeforeMethod
    public void setUp() throws Exception {

        // create a normal instance of the object
        worker = new ControlWorker();

        // mock the socket
        socket = mock(Socket.class);

        // when socket method calls, return test objects
        // this allows for complete control over the internal socket and state
        outputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    /**
     * Client sends "ls" command to server
     */
    public void testLs() throws Exception {

        // populate a fake socket buffer with the ls command
        String command = "ls\n";
        inputStream = new ByteArrayInputStream(command.getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        worker.setSocket(socket);

        // create a worker spy so we can confirm which methods were invoked
        ControlWorker workerSpy = spy(worker);

        // the method to test
        workerSpy.run();

        verify(workerSpy, times(1)).ls();

        Assert.assertEquals(outputStream.toByteArray(), "connecting".getBytes(),
                "\"connecting\" was not written to output socket");
    }
}
