//package ds.trabalho.parte2;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.LinkedList;


/**
 * Classe responsável por inicializar o cliente e o servidor
 */
public class Peer {
	String host;
	Logger logger;
	String myIp;
	String myPort;
	LinkedList<String> dictonary;
	LinkedList<String> nodes;

	/**
     * Construtor da Classe Peer
     * 
     * @param hostname - ip do da máquina atual
     */
	public Peer(String hostname) {
		host = hostname;
		logger = Logger.getLogger("logfile");
		dictonary = new LinkedList<>();
		nodes = new LinkedList<>();

		try {
			FileHandler handler = new FileHandler("./" + hostname + "_peer.log", true);
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		Peer peer = new Peer(args[0]);
		peer.myIp = args[0];
		peer.myPort = args[1];
		System.out.printf("new peer @ host=%s\n\n", args[0]);
		System.out.println("The following commands are available:");
		System.out
				.println("register(IP:PORT)->  Registers the defined IP:PORT");
		System.out
				.println("push(IP:PORT)->  Sends data from the defined IP:PORT");
		System.out
				.println("pull(IP:PORT)->  Receives data from the defined IP:PORT");

		System.out
				.println("pushpull(IP:PORT)->  Sends and receives data from the defined IP:PORT");
		System.out
				.println("show()->  Display dictionary\n");
		new Thread(new Server(args[0], Integer.parseInt(args[1]), peer)).start();
		new Thread(new Client(args[0], peer)).start();
	}
}
