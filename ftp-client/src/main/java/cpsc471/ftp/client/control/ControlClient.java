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
    public void ls() throws IOException;

    /**
     * Upload a file to the server
     * @param localFile file to upload
     * @throws FileNotFoundException if localFile does not refer to a valid file
     */
    public void put(String localFile) throws FileNotFoundException;

    /**
     * Retrieve a file from the server
     * @param remoteFile file to retrieve
     * @throws FileNotFoundException if remoteFile isn't found on server
     */
    public void get(String remoteFile) throws FileNotFoundException;

    /**
     * Terminate connection with server
     */
    public void quit();
}
