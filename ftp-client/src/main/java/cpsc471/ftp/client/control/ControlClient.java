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
     */
    public void put();

    /**
     * Retrieve a file from the server
     */
    public void get();

    /**
     * Terminate connection with server
     */
    public void quit();
}
