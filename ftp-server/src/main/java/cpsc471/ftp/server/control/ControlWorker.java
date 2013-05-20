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

    private OutputStream outputStream;

    private BufferedReader socketReader;

    private Socket socket;

    private static final String CONNECTING_MESSAGE = "connecting";

    /**
     * Create a worker to handle a connection
     * @param socket a socket representing the connection for this
     *               worker to handle
     */
    public ControlWorker(Socket socket) throws IOException {

        this.socket = socket;
        outputStream = socket.getOutputStream();
        socketReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
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
    public void ls() throws IOException {

        logger.info("Handling \"ls\" command");

        // respond to request with "connecting"
        socket.getOutputStream().write(CONNECTING_MESSAGE.getBytes());

        // open a new data connection with which to send data
        // todo open data connection
    }

    public void get(String file, short port) {

    }

    public void put(String file, short port) {

    }

    public void quit() {

    }

    // region Setters and Getters

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }

    public void setSocketReader(BufferedReader socketReader) {
        this.socketReader = socketReader;
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // endregion
}
