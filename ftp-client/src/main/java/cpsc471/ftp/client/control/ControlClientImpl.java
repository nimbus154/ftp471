package cpsc471.ftp.client.control;

import cpsc471.ftp.data.DataChannelServer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementation of the ControlClient interface
 */
public class ControlClientImpl implements ControlClient {

    private Logger logger = Logger.getLogger(this.getClass());

    private String domainName;

    private short port;

    private Socket socket;

    private PrintWriter socketWriter;

    private BufferedReader socketReader;

    private static final String CONNECTING_MESSAGE = "connecting";

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

        setSocket(new Socket(domainName, port));

        BasicConfigurator.configure(); // configure log4j
    }

    /**
     * Basic constructor, for testing
     */
    public ControlClientImpl() {

        BasicConfigurator.configure(); // configure log4j
    }

    @Override
    public void ls() throws IOException {

        socketWriter.println("ls");
        DataChannelServer dataChannel = new DataChannelServer();
        socketWriter.println(dataChannel.getPort());
        socketWriter.flush();

        long bytesToReceive = extractBytesToReceive();

        if(serverWantsToConnect()) {
            dataChannel.accept(); // accept connection, returns when connection established
            dataChannel.download(bytesToReceive); // will print to stdout
        }
    }

    @Override
    public void put(String localFile)
        throws FileNotFoundException, IOException {

        put(new File(localFile));
    }

    /**
     * Upload a file to the server
     * @param localFile the file to upload
     * @throws FileNotFoundException file to upload
     */
    public void put(File localFile)
            throws FileNotFoundException, IOException {

        if(localFile.exists()) {
            socketWriter.println("put");
            socketWriter.println(localFile.getName());
            socketWriter.println(localFile.length());

            DataChannelServer dataChannel = new DataChannelServer();
            socketWriter.println(dataChannel.getPort());
            socketWriter.flush();

            if(serverWantsToConnect()) {
                dataChannel.accept(); // accept data channel connection
                dataChannel.upload(localFile); // once accepted, upload
                dataChannel.close();
            }
        }
        else {
            throw new FileNotFoundException(
                    "File \"" + localFile.getName() + "\" does not exist"
            );
        }
    }

    @Override
    public void get(String remoteFile)
        throws FileNotFoundException, IOException {

        // send get request to server
        socketWriter.println("get");
        socketWriter.println(remoteFile);

        DataChannelServer dataChannel = new DataChannelServer();
        socketWriter.println(dataChannel.getPort());
        socketWriter.flush();

        long fileSize = extractBytesToReceive();

        if(serverWantsToConnect()) {
            dataChannel.accept(); // accept data channel connection
            dataChannel.download(new File(remoteFile), fileSize); // once accepted, upload
            dataChannel.close();
        }
    }

    @Override
    public void quit() {

        try {
            // send quit signal to server
            socketWriter.println("quit");
            socketWriter.flush();
            socket.close();
        }
        catch (IOException e) {
            logger.warn("Unable to close socket:" + e.getMessage(), e);
        }
    }

    /**
     * Extract the file size from the server
     * @return size of file to download
     * @throws FileNotFoundException if server couldn't find file
     * @throws IOException if another error occurred
     */
    private long extractBytesToReceive()
        throws FileNotFoundException, IOException {

        String arg = null;
        long number = 0;
        try {
            arg = socketReader.readLine(); // file size
            number = Long.parseLong(arg);
        }
        catch (IOException e) {
            // if nothing in socket
            logger.warn("Unable to read from socket: " + e.getMessage(), e);
            throw new IOException(e.getMessage());
        }
        catch(NumberFormatException e) {
            // if a value other than a file size is returned
            logger.warn("Error parsing a long from arg: " + arg);
            throw new FileNotFoundException("Server could not find file");
        }
        return number;
    }

    /**
     * Checks if server is trying to connect to client for data transfer
     * @return true if trying to connect
     * @throws IOException if unable to read from socket
     */
    private boolean serverWantsToConnect() throws IOException {

        String response;
        try {
            response = socketReader.readLine(); // should say "connecting"
            if(response == null) {
                throw new IOException("Server did not respond according to protocol: "
                    + "it did not provide a response to client request.");
            }
            if(!response.equals(CONNECTING_MESSAGE )) {
                throw new IOException("Error: " + response);
            }
            return true;
        }
        catch (IOException e) {
            // if nothing in socket
            logger.warn("Unable to read from socket: " + e.getMessage(), e);
            throw new IOException(e.getMessage());
        }
    }

    // region Getters and Setters

    public String getDomainName() {
        return domainName;
    }

    public short getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Sets socket and streams that read from socket
     * @param socket socket to set
     * @throws IOException if unable to open a stream into the socket
     */
    public void setSocket(Socket socket) throws IOException {

        this.socket = socket;
        socketWriter = new PrintWriter(socket.getOutputStream());
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    // endregion
}
