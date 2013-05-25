package cpsc471.ftp.data;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client for a data channel
 */
public class DataChannelClient extends DataChannel {

    /**
     * Opens a data channel connection with a server
     * @param address IP address of server
     * @param port port name of server
     * @throws IOException if unable to open connection
     */
    public DataChannelClient(InetAddress address, int port)
        throws IOException {

        setSocket(new Socket(address, port));
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
}
