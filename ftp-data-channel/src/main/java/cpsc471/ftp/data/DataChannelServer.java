package cpsc471.ftp.data;

import java.io.*;
import java.net.ServerSocket;

/**
 * Server for a data channel
 */
public class DataChannelServer extends DataChannel {

    private ServerSocket serverSocket;

    /**
     * Opens a DataChannel server. Begins listening on an ephemeral port.
     * @throws IOException
     */
    public DataChannelServer() throws IOException {

        serverSocket = new ServerSocket(0); // bind to ephemeral port
    }

    @Override
    public void download(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void upload(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void upload(String s) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {

        serverSocket.close();
    }

    public int getPort() {

        return serverSocket.getLocalPort();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
