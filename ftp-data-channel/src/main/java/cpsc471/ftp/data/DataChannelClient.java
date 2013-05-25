package cpsc471.ftp.data;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Client for a data channel
 */
public class DataChannelClient extends DataChannel {

    /**
     * Opens a data channel connection with a server
     * @param ipAddress IP address of server
     * @param port port name of server
     * @throws IOException if unable to open connection
     */
    public DataChannelClient(InetAddress ipAddress, int port)
        throws IOException {

        // set up socket channel through which to transfer data
        SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
        setSocketChannel(SocketChannel.open(socketAddress));
    }
}
