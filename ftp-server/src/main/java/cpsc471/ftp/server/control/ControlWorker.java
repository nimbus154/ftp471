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

    private static final String CONNECTING_MESSAGE = "connecting";

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
                default:
                    break;
            }
        }
        catch (IOException e) {
            logger.warn("Nothing in input buffer");
        }
    }

    /**
     * Handle the ls command
     */
    public void ls() {

        logger.info("handling \"ls\"");

        // respond to request with "connecting"
        socketWriter.println(CONNECTING_MESSAGE);
        socketWriter.flush();

        // open a new data connection with which to send data
        // todo open data connection
    }

    public void get(String file, short port) {

    }

    /**
     * Handle a request to upload a file
     */
    public void put() {

        String fileName;
        try {
            fileName = socketReader.readLine();
        }
        catch (IOException e) {
            logger.warn("Insufficient arguments supplied to put command");
            return;
        }

        // fileName will always be the second line
        logger.info("handling \"put " + fileName + "\"");

        socketWriter.println(CONNECTING_MESSAGE);
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
