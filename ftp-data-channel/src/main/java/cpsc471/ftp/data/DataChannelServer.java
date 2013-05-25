package cpsc471.ftp.data;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server for a data channel
 */
public class DataChannelServer extends DataChannel {

    private ServerSocket socket;

    public DataChannelServer() throws IOException {

        socket = new ServerSocket(0);
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

        socket.close();
    }

    public int getPort() {

        return socket.getLocalPort();
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }
}
