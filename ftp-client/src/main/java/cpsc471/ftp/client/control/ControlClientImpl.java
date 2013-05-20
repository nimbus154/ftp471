package cpsc471.ftp.client.control;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementation of the ControlClient interface
 */
public class ControlClientImpl implements ControlClient {

    private String domainName;

    private short port;

    private Socket socket;

    private PrintWriter socketWriter;

    private BufferedReader socketReader;

    /**
     * Create a ControlClient
     * @param domainName domainName name of server to which to connect
     * @param port port on server to which to connect
     * @throws IllegalArgumentException if domainName name isn't found
     */
    public ControlClientImpl(String domainName, short port)
            throws UnknownHostException, IOException {

        this.domainName = domainName;
        this.port = port;

        socket = new Socket(domainName, port);

        socketWriter = new PrintWriter(socket.getOutputStream());
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
    }

    /**
     * Basic constructor, for testing
     */
    public ControlClientImpl() { }

    @Override
    public void ls() {

        //socketWriter.println("ls");
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

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setPort(short port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    public PrintWriter getSocketWriter() {
        return socketWriter;
    }

    public void setSocketWriter(PrintWriter socketWriter) {
        this.socketWriter = socketWriter;
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }

    public void setSocketReader(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }
    // endregion
}
