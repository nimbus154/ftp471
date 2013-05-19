package cpsc471.ftp.client.control;

/**
 * Client for the control channel
 */
public interface ControlClient {

    /**
     * List files on server
     */
    public void ls();

    /**
     * Upload a file to the server
     * @param localFile file to upload
     */
    public void put(String localFile);

    /**
     * Retrieve a file from the server
     * @param remoteFile file to retrieve
     */
    public void get(String remoteFile);

    /**
     * Terminate connection with server
     */
    public void quit();
}
