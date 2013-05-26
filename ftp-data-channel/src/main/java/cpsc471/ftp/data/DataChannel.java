package cpsc471.ftp.data;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Used to transfer files and large messages.
 * The actual file transfer portion of the FTP connection.
 */
public abstract class DataChannel {

    // thanks to https://www.ibm.com/developerworks/library/j-zerocopy/
    // for the introduction to SocketChannels, FileChannels, and the
    // transferTo method

    protected SocketChannel socketChannel;

    /**
     * Set the socket channel to use for communications
     * @param socketChannel socketChannel to set
     * @throws IOException if unable to read from socket
     */
    public void setSocketChannel(SocketChannel socketChannel)
            throws IOException {

        this.socketChannel = socketChannel;
        // block allows for more data to transfer more easily, though it's less scalable
        socketChannel.configureBlocking(true);
    }

    /**
     * Download data to stdout
     * @param length length of byte stream to receive
     * @throws IOException
     */
    public void download(long length) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)length);
        socketChannel.read(byteBuffer);

        byteBuffer.rewind(); // rewind buffer to start, otherwise charbuffer will be empty

        CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
        System.out.println(charBuffer.toString());
    }

    /**
     * Download a file
     * @param file file in which to store download
     * @param size size of file to download
     */
    public void download(File file, long size) throws IOException {

        FileChannel fileChannel = new FileOutputStream(file).getChannel();

        fileChannel.transferFrom(
                socketChannel, // channel from which to retrieve bytes
                0,
                size
        );
        fileChannel.close();
    }

    /**
     * Upload a file
     * @param file file to upload
     */
    public void upload(File file) throws IOException {

        FileChannel fileChannel = new FileInputStream(file).getChannel();

        // send the file
        long bytesSent = 0, fileLength = file.length();
        while(bytesSent < fileLength) {
            bytesSent += fileChannel.transferTo(
                    bytesSent, // byte offset from which to send
                    fileLength - bytesSent, // number of bytes to send
                    socketChannel // channel to which to send bytes
            );
        }
        fileChannel.close();
    }

    /**
     * Upload a large string
     * @param s string to upload
     * @throws IOException thrown if connection issue
     */
    public void upload(String s) throws IOException {

        // thanks to http://stackoverflow.com/questions/871870/how-to-write-data-to-socket-channel
        // for character encoding/decoding
        socketChannel.write(
                Charset.forName("UTF-8").encode(CharBuffer.wrap(s))
        );
    };

    /**
     * Close the data channel
     * @throws IOException if unable to close
     */
    public void close() throws IOException {

        socketChannel.close();
    }
}
