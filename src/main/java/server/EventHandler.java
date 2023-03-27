package server;
/**
 * Interface qui relie les différentes méthodes de gestion de commandes
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}
