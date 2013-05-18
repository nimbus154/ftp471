package cpsc471.ftp.client.control;

import org.apache.log4j.Logger;

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
            try {
                String domainName = args[0];
                short port = Short.parseShort(args[1]);
                ControlClient client = new ControlClientImpl(domainName, port);

                Shell shell = new Shell(client);
                shell.run();
            }
            catch (NumberFormatException e) {
                // thrown if port isn't a number or a short
                System.err.println("<port> must be a 16-bit integer");
            }
            catch (Exception e) {
                System.err.println("An unexpected error occurred: "
                        + e.getMessage());
                logger.error(e.getClass().getName() + " " + e.getMessage());
            }
        }
    }

    // endregion

    // region Shell instance methods

    private ControlClient client;

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
     * Print a list of commands and options
     */
    public void help() {

    }

    // endregion
}
