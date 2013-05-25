package cpsc471.ftp.data;

import java.io.File;
import java.io.IOException;

/**
 * Used to transfer files and large messages.
 * The actual file transfer portion of the FTP connection.
 */
public abstract class DataChannel {

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
