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
     * @return number of bytes received from server
     * @throws IOException
     */
    public long download(long length) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)length);
        socketChannel.read(byteBuffer);

        byteBuffer.rewind(); // rewind buffer to start, otherwise charbuffer will be empty

        CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
        System.out.println(charBuffer.toString());
        return charBuffer.length();
    }

    /**
     * Download a file
     * @param file file in which to store download
     * @param size size of file to download
     * @return number of bytes received from server
     */
    public long download(File file, long size) throws IOException {

        FileChannel fileChannel = new FileOutputStream(file).getChannel();

        fileChannel.transferFrom(
                socketChannel, // channel from which to retrieve bytes
                0,
                size
        );
        long trueFileLength = fileChannel.size();
        fileChannel.close();
        return trueFileLength;
    }

    /**
     * Upload a file
     * @param file file to upload
     * @return the number of bytes sent
     */
    public long upload(File file) throws IOException {

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
        return bytesSent;
    }

    /**
     * Upload a large string
     * @param s string to upload
     * @return the number of bytes sent
     * @throws IOException thrown if connection issue
     */
    public long upload(String s) throws IOException {

        // thanks to http://stackoverflow.com/questions/871870/how-to-write-data-to-socket-channel
        // for character encoding/decoding
        CharBuffer charBuffer = CharBuffer.wrap(s);
        socketChannel.write(
                Charset.forName("UTF-8").encode(charBuffer)
        );
        return charBuffer.length();
    };

    /**
     * Close the data channel
     * @throws IOException if unable to close
     */
    public void close() throws IOException {

        socketChannel.close();
    }
}
