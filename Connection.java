import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Classe responsável por gerir a conexão entre máquinas
 */
class Connection implements Runnable {
    String clientAddress;
    Socket clientSocket;
    Logger logger;
    Peer peer;

    /**
     * Construtor da Classe Connection
     * 
     * @param clientAddress - IP do cliente
     * @param clientSocket - Socket que está a ser utilizada
     * @param peer - Peer atual
     */
    public Connection(String clientAddress, Socket clientSocket, Peer peer) {
        this.clientAddress = clientAddress;
        this.clientSocket = clientSocket;
        this.logger = peer.logger;
        this.peer = peer;

    }

    @Override
    public void run() {
        /*
         * prepare socket I/O channels
         */
        try {
            String server;
            String port;
            String fullEntry;
            String data;

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String entry = in.readLine();
            String command = parseCommand(entry);
            switch (command) {
                case "register":

                    server = parseIP(entry);
                    port = parsePort(entry);
                    fullEntry = server + ":" + port;

                    peer.nodes.addLast(fullEntry);

                    System.out.println("Registered " + fullEntry);

                    out.print("Register completed");
                    out.flush();

                    break;
                case "push":
                    data = parseData(entry);

                    addToDictonary(data);
                    System.out.println("Data was received");
                    // System.out.println(data);

                    out.print("Data was sent");
                    out.flush();

                    break;
                case "pull":
                    server = parseIP(entry);
                    port = parsePort(entry);
                    fullEntry = server + ":" + port;

                    new Thread(new TokenManager(port, server, this.peer, "push")).start();

                    // out.print("Data Received");
                    out.flush();

                    break;
                case "pushpull":
                    break;
                default:
                    System.out.print("$Error command not defined \n");
                    break;
            }

            /*
             * close connection
             */
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Parses command (ex: Input: "register(localhost:8080)" -> Output: "register")
     * 
     * @param command
     * @return Parsed command
     */
    public String parseCommand(String command) {
        return command.substring(0, command.indexOf("("));
    }

    /**
     * Parses IP (ex: Input: "register(localhost:8080)" -> Output: "localhost")
     * 
     * @param command
     * @return Parsed IP
     */
    public String parseIP(String command) {
        return command.substring(command.indexOf("(") + 1, command.indexOf(":"));
    }

    /**
     * Parses port (ex: Input: "register(localhost:8080)" -> Output: "8080")
     * 
     * @param command
     * @return Parsed port
     */
    public String parsePort(String command) {
        return command.substring(command.indexOf(":") + 1, command.indexOf(")"));
    }

    /**
     * Parses port from command with data (ex: Input: "register(localhost:8080-[dictonary])" -> Output: "8080")
     * 
     * @param command
     * @return Parsed port
     */
    public String parsePortWithData(String command) {
        return command.substring(command.indexOf(":") + 1, command.indexOf("-"));
    }

    /**
     * Parses port from command with data (ex: Input: "register(localhost:8080-[dictonary])" -> Output: "[dictonary]")
     * 
     * @param command
     * @return Parsed data
     */
    public String parseData(String command) {
        return command.substring(command.indexOf("-") + 1, command.indexOf(")"));
    }


    /**
     * Adiciona palavras ao dicionario
     */
    public void addToDictonary(String data) {
        data = data.substring(data.indexOf("[") + 1, data.indexOf("]"));
        String[] splitted = data.split(", ");

        for (int i = 0; i < splitted.length; i++) {
            if (!peer.dictonary.contains(splitted[i])) {
                peer.dictonary.addLast(splitted[i]);
            }
        }
    }
}
