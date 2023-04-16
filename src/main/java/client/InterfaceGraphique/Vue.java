package client.InterfaceGraphique;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;


/**
 * La classe Vue est responsable de l'affichage graphique de l'interface utilisateur pour l'inscription aux cours.
 */
public class Vue {
    /**
     * Tableau graphique affichant les cours.
     */

    public TableView<Course> table;
    /**
     * Contrôleur responsable de la gestion des événements utilisateur.
     */
    private static Controleur controleur;
    /**
     * Modèle représentant les données des cours.
     */
    private Modele modele;
    /**
     * Indique si le bouton charger a été cliqué.
     */
    private boolean chargerClic = false;
    /**
     * Indique si un cours a été sélectionné.
     */
    private boolean coursSelec = false;
    /**
     * Champ texte pour le prénom de l'étudiant.
     */
    private TextField Prenom = new TextField();
    /**
     * Champ texte pour le nom de l'étudiant.
     */
    private TextField Nom = new TextField();
    /**
     * Champ texte pour l'adresse courriel de l'étudiant.
     */
    private TextField Email = new TextField();
    /**
     * Champ texte pour le matricule de l'étudiant.
     */
    private TextField Matricule = new TextField();

    /**
     * Constructeur de la classe Vue.
     *
     * @param port le port sur lequel le client doit se connecter.
     */

    public Vue(int port){
        creerVue(port);
    }
    /**
     * Crée l'interface utilisateur de l'application.
     *
     * @param port le port sur lequel le client doit se connecter.
     */
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

            entrees.getChildren().addAll(Prenom,Nom,Email,Matricule);
            Inscription.getChildren().add(entrees);

            //Bouton pour envoyer le formulaire d'inscription
            Button Envoyer= new Button("Envoyer");

            try{
                boutonCharger.setOnAction((event) -> {
                    try {

                        controleur.charger(session.getValue());
                        chargerClic = true;
                        coursSelec = false;

                    }catch(NullPointerException e){

                        erreur("Veuillez choisir une session.");
                        table.setBorder(new Border(new BorderStroke(Color.RED,
                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        chargerClic = false;
                        coursSelec = false;

                    }

                });

                table.setOnMouseClicked(event -> {
                    if (event.getClickCount() >= 1) {

                        chargerClic = true;
                        coursSelec = true;

                    }
                });

                Envoyer.setOnAction((event) -> {
                    table.setBorder(null);
                    Prenom.setStyle("");
                    Nom.setStyle("");
                    Email.setStyle("");
                    Matricule.setStyle("");
                    try {
                        if (chargerClic) {
                            try {
                                if(coursSelec) {
                                    Course coursChoisi = table.getSelectionModel().getSelectedItem();
                                    controleur.inscrire(Prenom.getText(), Nom.getText(), Email.getText(),
                                            Matricule.getText(), coursChoisi);
                                    inscriptionReussie(coursChoisi);
                                }else{
                                    table.setBorder(new Border(new BorderStroke(Color.RED,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    throw new IllegalArgumentException("Veuillez choisir un cours.");
                                }

                            } catch (Exception e) {
                                rougeErreur(e.getMessage());
                                throw new IllegalArgumentException(e.getMessage());
                            }

                        } else {
                            table.setBorder(new Border(new BorderStroke(Color.RED,
                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                            coursSelec = false;
                            throw new IllegalArgumentException("Veuillez choisir une session.");

                        }
                    }catch(Exception e){
                        erreur(e.getMessage());
                    }
                });

            }catch(Exception e){


            }


        Formulaire_Inscription.getChildren().addAll(Inscription,Envoyer);




        root.getChildren().add(Formulaire_Inscription);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    /**
     * Cette méthode affiche une fenêtre d'erreur contenant un message d'erreur donné en paramètre
     *
     * @param messageErreur Le message d'erreur à afficher dans la fenêtre
     */
    private void erreur(String messageErreur){
        Stage errorStage = new Stage();
        Label errorMessage = new Label(messageErreur);
        VBox layout = new VBox(errorMessage);
        layout.setAlignment(Pos.CENTER);
        Button boutonErreur = new Button("Ok");
        boutonErreur.setPrefSize(120,30);
        layout.getChildren().add(boutonErreur);

        Scene scene = new Scene(layout, 300, 200);
        errorStage.setScene(scene);
        errorStage.setTitle("Error");
        errorStage.show();
        boutonErreur.setOnAction((action) -> {
            errorStage.close();
        });
    }
    /**
     * Cette méthode applique une ombre rouge sur les champs d'entrée associés aux erreurs spécifiques passées en paramètre,
     * comme le prénom, le nom, l'email ou le matricule
     *
     * @param messageErreur Le message d'erreur contenant des informations sur les champs à surligner en rouge
     */
    private void rougeErreur(String messageErreur){
        int i = -1;
        if (messageErreur.indexOf("prénom")!=i){
            Prenom.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);");
        }
        if (messageErreur.indexOf(" nom")!=i){
            Nom.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);");
        }
        if (messageErreur.indexOf("email")!=i){
            Email.setStyle("-fx-effect: dropshadow(gaussian, #ff0000, 10, 0, 0, 0);");
        }
        if (messageErreur.indexOf("matricule")!=i){
            Matricule.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);");
        }


    }
    /**
     * Cette méthode affiche une fenêtre de succès indiquant qu'une inscription a été réussie pour un cours donné.
     * Elle réinitialise également les champs d'entrée.
     *
     * @param cours Le cours pour lequel l'inscription a été réussie
     */
    private void inscriptionReussie(Course cours){
        Stage succesStage = new Stage();
        Label messageFin = new Label("Félicitations! Inscription réussie de " + Prenom.getText() + " \n" +
                Nom.getText() + " au cours " + cours.getCode() + ".");
        VBox fin = new VBox(messageFin);
        messageFin.setAlignment(Pos.CENTER);

        Button boutonFin = new Button("Ok");
        boutonFin.setPrefSize(120,30);
        boutonFin.setAlignment(Pos.BOTTOM_RIGHT);
        fin.getChildren().add(boutonFin);

        Scene sceneFin = new Scene(fin, 300, 200);
        succesStage.setScene(sceneFin);
        succesStage.setTitle("Message");
        succesStage.show();
        boutonFin.setOnAction((action) -> {
            succesStage.close();
            Prenom.setText("");
            Nom.setText("");
            Email.setText("");
            Matricule.setText("");


        });

    }

}
