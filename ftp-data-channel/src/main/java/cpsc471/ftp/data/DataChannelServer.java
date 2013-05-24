package cpsc471.ftp.data;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server for a data channel
 */
public class DataChannelServer extends DataChannel {

    private ServerSocket socket;

    public DataChannelServer() {

    }

    @Override
    public void download(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void upload(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getPort() {
        return (short)socket.getLocalPort();
    }
}
