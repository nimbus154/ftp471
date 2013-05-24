package cpsc471.ftp.server.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Dispatches requests to worker clients
 * Entry point for server
 */
public class ControlDispatcher {

    // region Static methods: Main and Usage

    public static void main(String args[]) {

        BasicConfigurator.configure(); // configure log4j

        if(args.length == 1) {
            short port = 0;
            try {
                port = Short.parseShort(args[0]);
                ControlDispatcher dispatcher = new ControlDispatcher();
                dispatcher.listen(port);
            }
            catch(NumberFormatException e) {
                System.out.println("<port> must be a 16-bit integer");
            }
            catch(IOException e) {
                System.out.println("Unable to listen on port " + port);
            }
        }
        else {
            usage();
        }
    }

    /**
     * Print how to use the server
     */
    public static void usage() {

        System.out.println(
                "Usage:\n" +
                        "serv <port>"
        );
    }

    // endregion

    private static final Logger logger = Logger.getLogger(ControlDispatcher.class);
    // todo handle out of threads
    private static final int THREADS = 10;

    private ExecutorService threadPool;

    public ControlDispatcher() {

        threadPool = Executors.newFixedThreadPool(THREADS);
        logger.info("Thread pool using " + THREADS + " threads.");
    }

    /**
     * Run the server. Dispatch requests to request handles.
     * @param port port on which to listen
     * @throws IOException if unable to listen on port
     */
    public void listen(short port) throws IOException {

        listen(new ServerSocket(port));
    }

    /**
     * Run the server. Dispatch requests to request handles.
     * @param serverSocket socket to use for server listening
     */
    public void listen(ServerSocket serverSocket) {

        logger.info("Listening on port " + serverSocket.getLocalPort());
        while(true) { // connection loop
            try {

                Socket clientSocket = serverSocket.accept();

                // assign socket to a request handler
                threadPool.submit(new ControlWorker(clientSocket));
            }
            catch(IOException e) {
                logger.error(
                        "Unable to accept connection: " + e.getMessage(),
                        e
                );
            }
        }
    }
}
