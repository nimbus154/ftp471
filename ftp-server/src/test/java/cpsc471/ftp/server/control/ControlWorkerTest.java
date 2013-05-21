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
        fakeCommand("ls\n");

        // create a worker spy so we can confirm which methods were invoked
        ControlWorker workerSpy = spy(worker);

        // the method to test
        workerSpy.run();

        verify(workerSpy, times(1)).ls();

        Assert.assertEquals(
                outputStream.toByteArray(),
                ControlWorker.CONNECTING_MESSAGE.getBytes(),
                "\"connecting\" was not written to output socket"
        );
    }

    /**
     * Test basic put
     */
    public void testPut() throws Exception {

        fakeCommand("put\nfileToUpload\n");

        // create a worker spy so we can confirm which methods were invoked
        ControlWorker workerSpy = spy(worker);

        // the method to test
        workerSpy.run();

        verify(workerSpy, times(1)).put();

        Assert.assertEquals(
                outputStream.toByteArray(),
                ControlWorker.CONNECTING_MESSAGE.getBytes(),
                "\"connecting\" was not written to output socket"
        );
    }

    /**
     * User gets a file; file exists
     */
    public void testGetFileFound() throws Exception {

        fakeCommand("get\nfile1\n");
        // create a worker spy so we can confirm which methods were invoked
        ControlWorker workerSpy = spy(worker);

        // the method to test
        workerSpy.run();

        verify(workerSpy, times(1)).get();

        Assert.assertEquals(
                outputStream.toByteArray(),
                ControlWorker.CONNECTING_MESSAGE.getBytes(),
                "\"connecting\" was not written to output socket"
        );
    }

    /**
     * Omit file name from message
     * @throws Exception
     */
    public void testGetInsufficientArgs() throws Exception {

        fakeCommand("");

        worker.get();

        Assert.assertEquals(
                outputStream.toByteArray(),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE.getBytes(),
                "\"connecting\" was not written to output socket"
        );
    }

    /**
     * Populate the mock socket with a command, as if sent from client
     * @param commandsSent
     */
    private void fakeCommand(String commandsSent) throws Exception {

        // populate a fake socket buffer with the ls command
        String command = commandsSent;
        inputStream = new ByteArrayInputStream(command.getBytes());
        when(socket.getInputStream()).thenReturn(inputStream);
        worker.setSocket(socket);
    }
}
