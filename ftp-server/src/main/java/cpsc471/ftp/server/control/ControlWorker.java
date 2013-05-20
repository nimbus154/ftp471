package cpsc471.ftp.server.control;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Handles client requests
 */
public class ControlWorker implements Runnable {

    private Logger logger = Logger.getLogger(ControlWorker.class);

    private PrintWriter socketWriter;

    private BufferedReader socketReader;

    private Socket socket;

    /**
     * Create a worker to handle a connection
     * @param socket a socket representing the connection for this
     *               worker to handle
     */
    public ControlWorker(Socket socket) throws IOException {

        this.socket = socket;
        socketWriter = new PrintWriter(socket.getOutputStream());
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
        //To change body of implemented methods use File | Settings | File Templates.
        logger.info("Servicing connection");
        ls();
    }

    /**
     * Handle the ls command
     */
    public void ls() {

    }

    public void get(String file, short port) {

    }

    public void put(String file, short port) {

    }

    public void quit() {

    }

    // region Setters and Getters

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


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // endregion
}
