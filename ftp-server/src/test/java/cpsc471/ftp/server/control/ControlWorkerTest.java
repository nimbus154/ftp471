package cpsc471.ftp.server.control;

import org.powermock.api.mockito.PowerMockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

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

//        // populate a fake socket buffer with the ls command
//        fakeCommand("ls\n1234\n");
//
//        // create a worker spy so we can confirm which methods were invoked
//        String lsResponse = "file1 file2";
//        ControlWorker workerSpy = spy(worker);
//        PowerMockito.when(
//                workerSpy,
//                method(ControlWorker.class, "doLs")
//        ).withNoArguments()
//        .thenReturn(lsResponse);
//
//        // the method to test
//        workerSpy.handleCmd();
//
//        verify(workerSpy, times(1)).ls();
//
//        String[] responses = new String(outputStream.toByteArray()).split("\n");
//        Assert.assertEquals(
//                responses[0],
//                lsResponse.getBytes().length,
//                "length of ls command in bytes was not written to output socket"
//        );
//        Assert.assertEquals(
//                responses[1],
//                ControlWorker.CONNECTING_MESSAGE,
//                "\"connecting\" was not written to output socket"
//        );
    }

    /**
     * Insufficient args supplied to ls
     * @throws Exception
     */
    public void testLsInsufficientArgs() throws Exception {

        fakeCommand("");

        worker.ls();

        Assert.assertEquals(
                new String(outputStream.toByteArray()),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE + "\n",
                "Insufficient args message not returned"
        );
    }

    /**
     * Test basic put
     */
    public void testPut() throws Exception {
// todo test stubbing file creation on upload
//        fakeCommand("put\nfileToUpload\n1000\n1234\n");
//
//        // create a worker spy so we can confirm which methods were invoked
//        ControlWorker workerSpy = spy(worker);
//
//        // the method to test
//        workerSpy.handleCmd();
//
//        verify(workerSpy, times(1)).put();
//
//        Assert.assertEquals(
//                new String(outputStream.toByteArray()),
//                ControlWorker.CONNECTING_MESSAGE + "\n",
//                "\"connecting\" was not written to output socket"
//        );
    }

    /**
     * User gets a file; file exists
     */
    public void testGetFileFound() throws Exception {
          // todo fix stubbing private method's file found validity check
//        fakeCommand("get\nfile\n");
//        // create a worker spy so we can confirm which methods were invoked
//        ControlWorker workerSpy = spy(worker);
//        PowerMockito.when(
//                workerSpy,
//                method(ControlWorker.class, "isValidFile", String.class)
//        ).withArguments(anyString())
//        .thenReturn(true);
//
//        // the method to test
//        workerSpy.run();
//
//        verify(workerSpy, times(1)).get();
//
//        Assert.assertEquals(
//                new String(outputStream.toByteArray()),
//                ControlWorker.CONNECTING_MESSAGE + "\n",
//                "\"connecting\" was not written to output socket"
//        );
    }

    /**
     * Insufficient args supplied to put
     * @throws Exception
     */
    public void testPutInsufficientArgs() throws Exception {

        fakeCommand("");

        worker.put();

        Assert.assertEquals(
                new String(outputStream.toByteArray()),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE + "\n",
                "Insufficient args message not returned"
        );
    }

    /**
     * Omit port; commands need port to connect to client on data channel
     * @throws Exception
     */
    public void testPutOmitPort() throws Exception {

        fakeCommand("filename\n");

        worker.put();

        Assert.assertEquals(
                new String(outputStream.toByteArray()),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE + "\n",
                "Insufficient args message not returned"
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
                new String(outputStream.toByteArray()),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE + "\n",
                "Insufficient args message not returned"
        );
    }

    /**
     * Omit port from message
     * @throws Exception
     */
    public void testGetOmitPort() throws Exception {

        fakeCommand("fileName\n");

        worker.get();

        // this is a bit of a hack: the file is invalid
        // but since the algorithm checks number of arguments first,
        // it's safe to check for insufficient args
        Assert.assertEquals(
                new String(outputStream.toByteArray()),
                ControlWorker.INSUFFICIENT_ARGS_MESSAGE + "\n",
                "Insufficient args message not returned"
        );
    }

    /**
     * Client requests file that server doesn't have
     * @throws Exception
     */
    public void testGetFileNotFound() throws Exception {

        fakeCommand("fileName\n1234\n");

        worker.get();

        Assert.assertEquals(
                new String(outputStream.toByteArray()),
                ControlWorker.FILE_NOT_FOUND_MESSAGE + "\n",
                "Not found message not returned"
        );
    }

    /**
     * Ensure the quit() method is invoked when the command is sent
     */
    public void testCallQuit() throws Exception {

        fakeCommand("quit\n");

        // create a worker spy so we can confirm which methods were invoked
        ControlWorker workerSpy = spy(worker);

        // the method to test
        workerSpy.handleCmd();

        verify(workerSpy, times(1)).quit();
    }

    /**
     * Ensure quit closes the connection
     * @throws Exception
     */
    public void testQuitCloseSocket() throws Exception {

        fakeCommand("quit\n"); // ensures socket won't throw null pointer
        worker.quit();
        verify(socket, times(1)).close();
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
