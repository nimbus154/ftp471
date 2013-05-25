package cpsc471.ftp.data;

import java.io.*;
import java.net.Socket;

/**
 * Used to transfer files and large messages.
 * The actual file transfer portion of the FTP connection.
 */
public abstract class DataChannel {

    protected PrintWriter socketWriter;

    protected BufferedReader socketReader;

    protected Socket socket;

    /**
     * Set the socket to use for communications
     * @param socket socket to set
     * @throws IOException if unable to read from socket
     */
    public void setSocket(Socket socket) throws IOException {

        this.socket = socket;
        socketWriter = new PrintWriter(socket.getOutputStream());
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Download a file
     * @param file file to download
     */
    public abstract void download(File file) throws IOException;

    /**
     * Upload a file
     * @param file file to upload
     */
    public abstract void upload(File file) throws IOException;

    /**
     * Upload a large string
     * @param s string to upload
     * @throws IOException thrown if connection issue
     */
    public abstract void upload(String s) throws IOException;

    /**
     * Close the data channel
     * @throws IOException if unable to close
     */
    public abstract void close() throws IOException;
}
