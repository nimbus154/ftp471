package cpsc471.ftp.data;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * Server for a data channel
 */
public class DataChannelServer extends DataChannel {

    private ServerSocketChannel serverSocketChannel;

    /**
     * Opens a DataChannel server. Begins listening on an ephemeral port.
     * @throws IOException
     */
    public DataChannelServer() throws IOException {

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(0));
        serverSocketChannel.configureBlocking(true);
    }

    /**
     * Accept a client connection; set up the client connection for
     * interaction
     * @throws IOException on socket setup errors
     */
    public void accept() throws IOException {

        setSocketChannel(serverSocketChannel.accept());
    }

    public int getPort() {

        try {
            InetSocketAddress address =
                    (InetSocketAddress)serverSocketChannel.getLocalAddress();
            return address.getPort();
        }
        catch(IOException e) {
            return 0;
        }
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }
}
