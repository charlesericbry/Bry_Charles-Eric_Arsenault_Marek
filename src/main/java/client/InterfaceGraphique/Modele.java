package client.InterfaceGraphique;

import client.Commande;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


import server.models.Course;
import server.models.RegistrationForm;

/**
 * Classe modèle, qui traite les données reçues du contrôleur.
 */
public class Modele {

    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Commande charger;
    private Commande inscrire;
    private ArrayList<Course> liste_cours;
    private String messageErreur = "";


    /**
     * Initialise le client, tourne en continu pour envoyer les nouvelles commandes
     */

    public Modele(int port) throws IOException {
        this.client = new Socket("127.0.0.1", port);
        objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        objectInputStream = new ObjectInputStream(client.getInputStream());

    }

    /**
     * Communique avec le serveur pour obtenir une liste de cours selon une session choisie
     *
     * @param session Session choisie
     * @return retourne une liste avec les cours pour une session donnée
     */
    public ArrayList<Course> charger(String session) {
        try{
            switch (session) {
                case "Automne":
                    this.charger = new Commande("CHARGER", "Automne");
                    break;
                case "Hiver":
                    this.charger = new Commande("CHARGER", "Hiver");
                    break;
                case "Ete":
                    this.charger = new Commande("CHARGER", "Ete");
                    break;
            }

            this.objectOutputStream.writeObject(charger);

            this.objectOutputStream.flush();
            return coursOfferts();

        }catch(Exception e){
            messageErreur+="Erreur lors du chargement des cours";
        }

        return null;
    }
    private ArrayList<Course> coursOfferts() throws IOException, ClassNotFoundException {
        this.liste_cours =(ArrayList<Course>) this.objectInputStream.readObject();
        return this.liste_cours;

    }

    /**
     * Gère les informations d'inscription reçues et décide de leur validité.
     *
     * @param prenom
     * @param nom
     * @param email
     * @param matricule
     * @param coursSelectionne
     * @throws Exception
     */
    public void inscription(String prenom, String nom,String email,
                            String matricule,Course coursSelectionne) throws Exception {
        choixInfo(prenom,nom,email,matricule);
        Course mon_cours = coursSelectionne;
        inscrire = new Commande("INSCRIRE", "");
        this.objectOutputStream.writeObject(inscrire);
        this.objectOutputStream.flush();
        RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, mon_cours);

        this.objectOutputStream.writeObject(form);
        this.objectOutputStream.flush();
    }

    private void choixInfo(String prenom, String nom,String email,String matricule){

        int indiceErreur = 0;


        if (prenom.length()==0){
            messageErreur+="Veuillez entrer un prénom valide.\n";
            indiceErreur+=1;

        }
        if (nom.length()==0){
            messageErreur+="Veuillez entrer un nom valide.\n";
            indiceErreur+=1;
        }
        if (email.length()>=5){
            String substring = email.substring(0, email.length() - 1);
            if (( substring.indexOf(".") == -1||substring.indexOf("@") == -1)||
                    substring.indexOf(".")<substring.indexOf("@")+1 ||(substring.indexOf("@") ==0)){
                messageErreur+="Veuillez entrer un email valide.\n";
                indiceErreur+=1;
            }
        }else{
            messageErreur+="Veuillez entrer un email valide.\n";
            indiceErreur+=1;
        }
        boolean estNombre = true;
        for (int j = 0; j < matricule.length(); j++) {
            if (!Character.isDigit(matricule.charAt(j))) {
                estNombre = false;
                break;
            }
        }
        if (matricule.length()!=8 || !estNombre){
            messageErreur+="Veuillez entrer un matricule à 8 chiffres.\n";
            indiceErreur+=1;

        }
        if (indiceErreur > 0){
            throw new IllegalArgumentException(messageErreur);
        }
    }
}
