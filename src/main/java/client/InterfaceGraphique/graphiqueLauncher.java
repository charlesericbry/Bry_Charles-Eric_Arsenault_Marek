package client.InterfaceGraphique;


import javafx.application.Application;
import javafx.stage.Stage;

public class graphiqueLauncher extends Application {
    public final static int PORT = 1337;
    public static void main(String[] args) {
        try {
            Modele modele = new Modele(PORT);
            launch(args);
            //modele.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Vue();
    }
}
