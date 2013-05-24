package cpsc471.ftp.server.control;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * Handles client requests
 */
public class ControlWorker implements Runnable {

    private Logger logger = Logger.getLogger(ControlWorker.class);

    private PrintWriter socketWriter;

    private BufferedReader socketReader;

    private Socket socket;

    public static final String CONNECTING_MESSAGE = "connecting";
    public static final String INSUFFICIENT_ARGS_MESSAGE = "insufficient arguments";
    public static final String FILE_NOT_FOUND_MESSAGE = "not found";

    /**
     * Create a worker to handle a connection
     * @param socket a socket representing the connection for this
     *               worker to handle
     */
    public ControlWorker(Socket socket) throws IOException {

        setSocket(socket);
    }

    /**
     * For testing purposes
     */
    public ControlWorker() {

        BasicConfigurator.configure(); // configure log4j
    }

    @Override
    public void run() {
        logger.info("Servicing connection");

        while(!socket.isClosed()) {
            handleCmd();
        }
    }

    public void handleCmd() {
        try {
            String cmd = socketReader.readLine();
            logger.info("Read " + cmd);

            switch(cmd) {
                case "ls":
                    ls();
                    break;
                case "put":
                    put();
                    break;
                case "get":
                    get();
                    break;
                case "quit":
                    quit();
                    break;
                default:
                    break;
            }
        }
        catch (IOException e) {
            logger.warn("Nothing in input buffer");
        }
    }

    /**
     * Handle quit command
     */
    public void quit() {

        logger.info("Closing connection");
        try {
            socket.close();
        }
        catch (IOException e) {
            logger.warn("Unable to close socket: " + e.getMessage(), e);
        }
    }

    /**
     * Handle the ls command
     */
    public void ls() {

        logger.info("handling \"ls\"");

        // extract port
        // respond to request with "connecting"
        connect();

        // open a new data connection with which to send data
        // execute the ls command locally, get the values
        // upload that value to the client
    }

    /**
     * Handle a request to download a file
     */
    public void get() {

        String fileName = nextArg();
        // get port
        if(fileName == null /* || port == null */) {
            logger.warn("Insufficient arguments supplied to get command");
            insufficientArgs();
            return;
        }
        else if(!isValidFile(fileName)) {
            logger.warn("Requested file not found");
            notFound();
            return;
        }

        logger.info("handling \"get\"");
        // respond to request with "connecting"
        connect();

        // open socket connection to client
        // "upload" file from client
    }

    /**
     * Handle a request to upload a file
     */
    public void put() {

        String fileName = nextArg();
        // get port
        if(fileName == null /* || port == null */) {
            logger.warn("Insufficient arguments supplied to put command");
            insufficientArgs();
            return;
        }

        // fileName will always be the second line
        logger.info("handling \"put " + fileName + "\"");
        connect();
        // open socket connection to client
        // download file from client
    }

    /**
     * Extract fileName from stream
     * @return string representing the next argument passed to the server
     * null if none
     */
    private String nextArg() {

        String fileName;

        try {
            fileName = socketReader.readLine();

            if(fileName == null || fileName.length() == 0) {
                throw new IOException("fileName blank");
            }
        }
        catch (IOException e) {
            return null;
        }
        return fileName;
    }

    /**
     * Checks if a file exists
     * @param fileName name of file
     * @return true if file exists
     */
    private boolean isValidFile(String fileName) {

        return new File(fileName).exists();
    }

    /**
     * Send a "connecting" response to client
     */
    private void connect() {

        socketWriter.println(CONNECTING_MESSAGE);
        socketWriter.flush();
    }

    /**
     * Send a "not found" message to client
     */
    private void notFound() {

        socketWriter.println(FILE_NOT_FOUND_MESSAGE);
        socketWriter.flush();
    }

    /**
     * Send an insufficient args response to client
     */
    private void insufficientArgs() {

        socketWriter.println(INSUFFICIENT_ARGS_MESSAGE);
        socketWriter.flush();
    }

    // region Setters and Getters

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
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
    }

    // endregion
}
