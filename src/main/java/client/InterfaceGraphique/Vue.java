package client.InterfaceGraphique;

import com.sun.javafx.charts.Legend;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;


public class Vue {

    public TableView<Course> table;
    private static Controleur controleur;

    private Modele modele;

    private boolean chargerClic = false;


    public Vue(int port){
        creerVue(port);
    }

    public void creerVue(int port) {
        try{

        Stage primaryStage = new Stage();
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
                TableView<Course> table = new TableView<>();
                controleur = new Controleur(new Modele(port), table);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setMaxWidth(350);
                TableColumn<Course, String> code = new TableColumn<>("Code");
                code.setCellValueFactory(new PropertyValueFactory<>("code"));
                TableColumn<Course, String> cours = new TableColumn<>("Cours");
                cours.setCellValueFactory(new PropertyValueFactory<>("name"));
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
            session.getItems().addAll("Automne", "Hiver", "Ete");
            session.setPrefSize(120, 30);
            Charger.getChildren().add(session);

            //Bouton pour charger la session
            Button boutonCharger = new Button("Charger");
            boutonCharger.setPrefSize(120,30);
            Charger.getChildren().add(boutonCharger);

            boutonCharger.setOnAction((event) -> {
                try {

                    controleur.charger(session.getValue());

                }catch(NullPointerException e){
                    System.out.println("Veuillez choisir une session.");
                    e.printStackTrace();

                }catch(Exception e){

                }

            });
            table.setOnMouseClicked(event -> {
                if (event.getClickCount() >= 1) {
                    String selectedItem = table.getSelectionModel().getSelectedItem().getCode();
                    chargerClic = true;
                    System.out.println(selectedItem);
                    // do something with the selected item
                }
            });

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
            Envoyer.setOnAction((event) -> {
                try {
                    if (chargerClic) {
                        controleur.inscrire(Prenom.getText(), Nom.getText(), Email.getText(),
                                Matricule.getText(),table.getSelectionModel().getSelectedItem());

                    } else {
                        throw new IllegalArgumentException("Veuillez choisir un cours");
                    }
                }catch(IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            });

        Formulaire_Inscription.getChildren().addAll(Inscription,Envoyer);




        root.getChildren().add(Formulaire_Inscription);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

}
