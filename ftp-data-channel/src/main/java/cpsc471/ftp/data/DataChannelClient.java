package cpsc471.ftp.data;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client for a data channel
 */
public class DataChannelClient extends DataChannel {

    private Socket socket;

    /**
     * Opens a data channel connection with a server
     * @param address IP address of server
     * @param port port name of server
     * @throws IOException if unable to open connection
     */
    public DataChannelClient(InetAddress address, short port)
        throws IOException {

        socket = new Socket(address, port);
    }

    @Override
    public void download(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void upload(File file) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}