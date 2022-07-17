import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Classe responsável por enviar os vários pedidos
 */
class TokenManager implements Runnable {
    String port;
    String server;
    Peer peer;
    String command;

    /**
     * Construtor da Classe TokenManager
     * 
     * @param port - Porta de ligação da máquina desejada
     * @param server - IP da máquina desejada
     * @param peer - Peer atual
     * @param command - Tipo de pedido efetuado
     */
    public TokenManager(String port, String server, Peer peer, String command) throws Exception {
        this.port = port;
        this.server = server;
        this.peer = peer;
        this.command = command;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getByName(server), Integer.parseInt(port));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            switch (command) {
                case "register":
                    out.println(command + "(" + peer.myIp + ":" + peer.myPort + ")");
                    out.flush();
                    System.out.println(in.readLine());
                    break;
                case "push":
                    out.println(
                            command + "(" + peer.myIp + ":" + peer.myPort + "-" + peer.dictonary
                                    + ")");
                    out.flush();
                    System.out.println(in.readLine());
                    break;
                case "pull":
                    out.println(command + "(" + peer.myIp + ":" + peer.myPort + ")");
                    break;
                default:
                    System.out.print("$Error command not defined \n");
                    break;
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
