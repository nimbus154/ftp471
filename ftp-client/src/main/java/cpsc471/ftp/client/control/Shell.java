package cpsc471.ftp.client.control;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Interactive shell for ftp client
 */
public class Shell {

    private static final Logger logger = Logger.getLogger(Shell.class);

    // region Static Methods: main, usage

    public static void usage() {

        System.out.println(
                "Usage:\n" +
                        "cli <domain name> <port>"
        );
    }

    public static void main(String args[]) {

        if(args.length != 2) {
            usage();
        }
        else {
            String domainName = args[0];
            try {
                short port = Short.parseShort(args[1]);
                ControlClient client = new ControlClientImpl(domainName, port);

                Shell shell = new Shell(client);
                shell.run();
            }
            catch (NumberFormatException e) {
                // thrown if port isn't a number or a short
                System.err.println("<port> must be a 16-bit integer");
            }
            catch (UnknownHostException e) {
                System.err.println("Unable to resolve host: " + domainName);
            }
            catch (IOException e) {
                System.err.println("Error with IO: " + e
                        + " " + e.getMessage());
            }
        }
    }

    // endregion

    // region Shell instance methods

    private ControlClient client;

    private static final String HELP_GET =  "get    : get <remote file>\n" +
                                            "           download a file\n";
    private static final String HELP_LS =   "ls     : ls\n" +
                                            "           list files on remote system\n";
    private static final String HELP_PUT =  "put    : put <local file>\n" +
                                            "           upload a file\n";
    private static final String HELP_QUIT = "quit   : quit\n" +
                                            "           exits the program\n";

    /**
     * Create an interactive ftp shell
     * @param client a client with an open connection to a server
     */
    public Shell(ControlClient client) {

        this.client = client;
    }

    /**
     * Runs the interactive ftp loop
     */
    public void run() {

        Scanner inputReader = new Scanner(System.in);
        while(true) {
            System.out.print("ftp> "); // prompt

            // get user input
            String[] input = inputReader.nextLine().split(" ");

            if(invokeCmd(input) == false) {
                break;
            }
        }
    }

    /**
     * Attempt to invoke the command entered by the user
     * @param args args to pass to command, including the command itself
     * @return true if the run loop should continue
     */
    public boolean invokeCmd(String[] args) {

        String cmd = args[0];

        boolean continueExec = true;

        switch(cmd) {
            case "ls":
                client.ls();
                break;
            case "get":
                handleGet(args);
                break;
            case "put":
                handlePut(args);
                break;
            case "quit":
                // close connection to server
                client.quit();
                // exit process
                continueExec = false;
                break;
            default: // unknown command; print help
                help();
                break;
        }
        return continueExec;
    }

    /**
     * Handles the "get" function
     * @param args arguments to pass to get
     */
    public void handleGet(String[] args) {

        if(args.length == 2) {
            client.get(args[1]);
        }
        else {
            System.out.println(HELP_GET);
        }
    }

    /**
     * Handles the put function
     * @param args arguments to pass to put
     */
    public void handlePut(String[] args) {

        if(args.length == 2) {
            try {
                client.put(args[1]);
            }
            catch (FileNotFoundException e) {
                System.err.println(
                        "Unable to upload \"" + args[1] + "\"; " +
                        "unable to find file."
                );
            }
        }
        else {
            System.out.println(HELP_PUT);
        }
    }

    /**
     * Print a list of commands and options
     */
    public void help() {

        System.out.printf(
                "Available commands:\n" +
                        "\t" + HELP_GET +
                        "\t" + HELP_LS +
                        "\t" + HELP_PUT +
                        "\t" + HELP_QUIT
        );
    }

    // endregion
}
