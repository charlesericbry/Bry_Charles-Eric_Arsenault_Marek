package server;

/**
 * Lance le serveur.
 */
public class ServerLauncher {
    /**
     * Port utilisé pour le serveur
     */
    public final static int PORT = 1441;
    /**
     * Démarre le serveur, exécute les commandes du client.
     *
     * @param args jamais utilisé
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}