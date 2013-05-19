package cpsc471.ftp.client.control;

import java.io.FileNotFoundException;

/**
 * Implementation of the ControlClient interface
 */
public class ControlClientImpl implements ControlClient {

    /**
     * Create a ControlClient
     * @param domainName domain name of server to which to connect
     * @param port port on server to which to connect
     * @throws Exception if domain name isn't found
     */
    public ControlClientImpl(String domainName, short port)
            throws Exception {

    }

    @Override
    public void ls() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void put(String localFile)
        throws FileNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void get(String remoteFile) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void quit() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
