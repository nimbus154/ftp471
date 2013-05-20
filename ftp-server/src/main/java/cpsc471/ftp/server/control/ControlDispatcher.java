package cpsc471.ftp.server.control;

/**
 * Dispatches requests to worker clients
 * Entry point for server
 */
public class ControlDispatcher {

    // region Static methods: Main and Usage

    public static void main(String args[]) {

        if(args.length == 1) {
            try {
                short port = Short.parseShort(args[0]);
                ControlDispatcher dispatcher = new ControlDispatcher();
                dispatcher.listen(port);
            }
            catch(NumberFormatException e) {
                System.out.println("<port> must be a 16-bit integer");
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
                        "\tserver <port>"
        );
    }

    // endregion


    public ControlDispatcher() { }

    /**
     * Run the server. Dispatch requests to request handles.
     * @param port port on which to listen
     */
    public void listen(short port) {

        System.out.println("Listening w00t!");
    }
}
