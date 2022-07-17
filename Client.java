import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Classe que efetua as coneções e lê os comandos do cliente.
 */
class Client implements Runnable {
    String host;
    Logger logger;
    Scanner scanner;
    Peer peer;

    /**
     * Construtor da Class Client
     * 
     * @param host - ip da máquina à qual o cliente se vai ligar
     * @param peer - Peer atual
     */
    public Client(String host, Peer peer) throws Exception {
        this.host = host;
        this.logger = peer.logger;
        this.scanner = new Scanner(System.in);
        this.peer = peer;
    }

    @Override
    public void run() {
        try {
            new Thread(new getNewWord(this)).start();
            System.out.println("Please type your command in the console");

            while (true) {
                String server = "";
                String port = "";
                String fullEntry = "";

                try {
                    System.out.print("$ ");
                    String entry = scanner.next();
                    String command = parseCommand(entry);

                    if (!(command.equals("show"))) {
                        server = parseIP(entry);
                        port = parsePort(entry);
                        fullEntry = server + ":" + port;
                    }

                    switch (command) {
                        case "register":
                            if (!peer.nodes.contains(fullEntry) && !fullEntry.equals(peer.myIp + ":" + peer.myPort)) {
                                peer.nodes.addLast(fullEntry);

                                new Thread(new TokenManager(port, server, this.peer, command)).start();

                            } else {
                                System.out.println("Ip already Registered");
                            }

                            break;
                        case "push":
                            if (verification(this.peer, fullEntry)) {
                                new Thread(new TokenManager(port, server, this.peer, command)).start();
                            } else {
                                System.out.println("Ip Not Registered");
                            }
                            break;
                        case "pull":
                            if (verification(this.peer, fullEntry)) {
                                new Thread(new TokenManager(port, server, this.peer, command)).start();
                            } else {
                                System.out.println("Ip Not Registered");
                            }
                            break;
                        case "pushpull":
                            if (verification(this.peer, fullEntry)) {
                                new Thread(new TokenManager(port, server, this.peer, "push")).start();
                                new Thread(new TokenManager(port, server, this.peer, "pull")).start();
                            } else {
                                System.out.println("Ip Not Registered");
                            }
                            break;
                        case "show":
                            System.out.println("Dictonary:" + peer.dictonary);
                            break;
                        default:
                            System.out.print("$Error command not defined \n");
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (

        Exception e) {
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
     * Verifies if IP and port are already inserted in the node Linked List
     * 
     * @param peer
     * @param fullEntry
     * @return Boolean
     */
    public Boolean verification(Peer peer, String fullEntry) {
        if (peer.nodes.contains(fullEntry) || fullEntry.equals(peer.myIp + ":" + peer.myPort)) {
            return true;
        } else
            return false;
    }
}
