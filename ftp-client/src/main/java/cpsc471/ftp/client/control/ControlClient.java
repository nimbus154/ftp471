package cpsc471.ftp.client.control;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Client for the control channel
 */
public interface ControlClient {

    /**
     * List files on server
     */
    public long ls() throws IOException;

    /**
     * Upload a file to the server
     * @param localFile file to upload
     * @throws FileNotFoundException if localFile does not refer to a valid file
     */
    public long put(String localFile) throws FileNotFoundException, IOException;

    /**
     * Retrieve a file from the server
     * @param remoteFile file to retrieve
     * @throws FileNotFoundException if remoteFile isn't found on server
     */
    public long get(String remoteFile) throws FileNotFoundException, IOException;

    /**
     * Terminate connection with server
     */
    public void quit();
}
