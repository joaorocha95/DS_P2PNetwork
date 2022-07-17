import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Classe responsavel por inicializar connections
 */
class Server implements Runnable {
    String host;
    int port;
    ServerSocket server;
    Logger logger;
    Peer peer;

    /**
     * Construtor da Classe Server
     * 
     * @param host - IP do da maquina atual
     * @param port - Porta de ligação da máquina atual
     * @param peer - Peer atual
     */
    public Server(String host, int port, Peer peer) throws Exception {
        this.host = host;
        this.port = port;
        this.logger = peer.logger;
        this.peer = peer;
        server = new ServerSocket(port, 1, InetAddress.getByName(host));
    }

    @Override
    public void run() {
        try {
            while (true) {

                try {
                    Socket client = server.accept();
                    String clientAddress = client.getInetAddress().getHostAddress();
                    new Thread(new Connection(clientAddress, client, peer)).start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
