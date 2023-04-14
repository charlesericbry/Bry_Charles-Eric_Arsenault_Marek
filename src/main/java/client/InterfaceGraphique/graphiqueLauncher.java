package client.InterfaceGraphique;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classede lancement de l'interface graphique.
 */
public class graphiqueLauncher extends Application {
    /**
     * Port de l'ordinateur
     */
    public final static int PORT = 1337;

    /**
     * Méthode main qui lance l'interface graphique
     * @param args
     */
    public static void main(String[] args) {
        try {


            launch(args);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param primaryStage Stage principal de l'interface
     * @throws Exception Toute exception inattendue
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Vue(PORT);
    }
}
