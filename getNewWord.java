import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe encarregue de adicionar novas palavras ao dicionário
 */
class getNewWord implements Runnable {
    Client client;

 /**
     * Construtor da Classe getNewWord
     * 
     * @param client - Cliente atual
     */
    public getNewWord(Client client) throws Exception {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10000);
                // Pedido à API e parse do mesmo pedido
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://random-word-api.herokuapp.com/word?number=1");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null;) {
                        result.append(line);
                    }
                }
                result = result.deleteCharAt(0);
                result = result.deleteCharAt(result.length() - 1);
                
                //Verifica se a palavra proveniente da API já existe no dicionário, se existir não é adicionada
                if (!client.peer.dictonary.contains(result.toString())) {
                    client.peer.dictonary.addLast(result.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
