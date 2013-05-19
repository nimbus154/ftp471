package cpsc471.ftp.client.control;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Implementation of the ControlClient interface
 */
public class ControlClientImpl implements ControlClient {

    private String domainName;
    private short port;
    private InetAddress ip;

    /**
     * Create a ControlClient
     * @param domainName domainName name of server to which to connect
     * @param port port on server to which to connect
     * @throws IllegalArgumentException if domainName name isn't found
     */
    public ControlClientImpl(String domainName, short port)
            throws UnknownHostException {

        ip = InetAddress.getByName(domainName);
        this.domainName = domainName;
        this.port = port;
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

    // region Getters and Setters

    public String getDomainName() {
        return domainName;
    }

    public short getPort() {
        return port;
    }

    public InetAddress getIp() {
        return ip;
    }
    // endregion
}
