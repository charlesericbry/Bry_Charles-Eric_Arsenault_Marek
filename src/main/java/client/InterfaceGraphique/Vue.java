package client.InterfaceGraphique;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue extends Application {
    public static void main(String[] args) {
        Vue.launch(args);
    }

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        //Boite principale
        HBox root = new HBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));

            //Boite de liste de cours
            VBox Liste_de_cours= new VBox();
            Liste_de_cours.setSpacing(10);
            root.getChildren().add(Liste_de_cours);
            Liste_de_cours.setPrefWidth(399);
            Liste_de_cours.setPrefHeight(600);


                //table pour choisir le cours
                VBox tableCours= new VBox();
                tableCours.setAlignment(Pos.CENTER);
                Text titre1 = new Text("Liste de cours");
                titre1.setFont(Font.font("Calibri", 25));
                tableCours.getChildren().add(titre1);
                tableCours.setStyle("-fx-background-color: rgb(245, 245, 220);");
                tableCours.setPrefWidth(399);
                tableCours.setPrefHeight(499);

                //table qui contient les cours.
                TableView<String> table = new TableView<>();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMaxWidth(350);
                TableColumn<String, String> code = new TableColumn<>("Code");
                TableColumn<String, String> cours = new TableColumn<>("Cours");
                table.getColumns().addAll(code, cours);
                tableCours.getChildren().add(table);

                Liste_de_cours.getChildren().add(tableCours);

        //Boite pour charger la session
        HBox Charger = new HBox();
        Charger.setSpacing(30);
        Charger.setAlignment(Pos.CENTER);
        Charger.setStyle("-fx-background-color: rgb(245, 245, 220);");
        Charger.setPrefWidth(399);
        Charger.setPrefHeight(120);
            //Choix de session
            ComboBox<String> session = new ComboBox<>();
            session.getItems().addAll("Automne", "Hiver", "Été");
            session.setPrefSize(120, 30);
            Charger.getChildren().add(session);
            //Bouton pour charger la session
            Button boutonCharger = new Button("Charger");
            boutonCharger.setPrefSize(120,30);
            Charger.getChildren().add(boutonCharger);

        Liste_de_cours.getChildren().add(Charger);


        //Boite pour les formulaires d'inscription
        VBox Formulaire_Inscription= new VBox();
        Formulaire_Inscription.setPadding(new Insets(30));
        Formulaire_Inscription.setStyle("-fx-background-color: rgb(245, 245, 220);");
        Formulaire_Inscription.setSpacing(30);
        Formulaire_Inscription.setPrefWidth(399);
        Formulaire_Inscription.setPrefHeight(600);
        Formulaire_Inscription.setAlignment(Pos.TOP_CENTER);
        Text titre2 = new Text("Formulaire d'inscription");
        titre2.setFont(Font.font("Calibri", 25));
        Formulaire_Inscription.getChildren().add(titre2);


            //Boîte contenant les entrées pour l'inscription, ainsi que la dénotation.
            HBox Inscription= new HBox();
            Inscription.setSpacing(20);
            Inscription.setAlignment(Pos.CENTER);

            //Contient les dénotations des entrées(ex;Prénom,Nom..)
            VBox titre = new VBox();
            titre.setSpacing(20);
            titre.getChildren().addAll(new Text("Prénom"),new Text("Nom"),new Text("Email"), new Text("Matricule"));
            Inscription.getChildren().add(titre);

        //Contient des TextField() pour y entrer des textes
        VBox entrees = new VBox();
        entrees.setSpacing(10);
        TextField Prenom = new TextField();
        TextField Nom = new TextField();
        TextField Email = new TextField();
        TextField Matricule = new TextField();
        entrees.getChildren().addAll(Prenom,Nom,Email,Matricule);
        Inscription.getChildren().add(entrees);

        //Bouton pour envoyer le formulaire d'inscription
        Button Envoyer= new Button("Envoyer");

        Formulaire_Inscription.getChildren().addAll(Inscription,Envoyer);




        root.getChildren().add(Formulaire_Inscription);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
