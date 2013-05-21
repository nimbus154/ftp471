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

    /**
     * Handle a request to download a file
     */
    public void get() {

        String fileName;
        try {
            fileName = socketReader.readLine();
            if(fileName == null || fileName.length() == 0) {
                throw new IOException("fileName blank");
            }

            File f = new File(fileName);
            if(!f.exists()) {
                throw new FileNotFoundException();
            }
        }
        catch (FileNotFoundException e) {
            logger.warn("Requested file not found");
            socketWriter.println(FILE_NOT_FOUND_MESSAGE);
            socketWriter.flush();
            return;
        }
        catch (IOException e) {
            logger.warn("Insufficient arguments supplied to get command");
            socketWriter.println(INSUFFICIENT_ARGS_MESSAGE);
            socketWriter.flush();
            return;
        }

        logger.info("handling \"get\"");

        // respond to request with "connecting"
        socketWriter.println(CONNECTING_MESSAGE);
        socketWriter.flush();
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
