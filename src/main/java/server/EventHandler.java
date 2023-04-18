package server;
/**
 * Interface qui relie les différentes méthodes de gestion de commandes
 */
@FunctionalInterface
public interface EventHandler {
    void handle(String cmd, String arg);
}

//javaFX/lib/javafx.base.jar javaFX/lib/javafx.controls.jar javaFX/lib/javafx.fxml.jar javaFX/lib/javafx.graphics.jar javaFX/lib/javafx.media.jar javaFX/lib/javafx.properties javaFX/lib/javafx.swing.jar javaFX/lib/javafx.web.jar javaFX/lib/javafx-swt.jar