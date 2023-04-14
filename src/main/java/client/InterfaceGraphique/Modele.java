package client.InterfaceGraphique;

import client.Commande;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import server.models.Course;
import server.models.RegistrationForm;


public class Modele {

    private int nb;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Commande charger;
    private Commande inscrire;
    private Commande test;
    private ArrayList<Course> liste_cours;


    /**
     * Initialise le client, tourne en continu pour envoyer les nouvelles commandes
     */

    public Modele(int port) throws IOException {
        this.client = new Socket("127.0.0.1", port);
        objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        objectInputStream = new ObjectInputStream(client.getInputStream());

    }

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
            System.out.println("Erreur lors du chargement des cours.");
            e.printStackTrace();
        }

        return null;
    }
    private ArrayList<Course> coursOfferts() throws IOException, ClassNotFoundException {
        this.liste_cours =(ArrayList<Course>) this.objectInputStream.readObject();
        return this.liste_cours;

    }

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
        int i = -1;
        int indiceErreur = 0;
        String messageErreur = "";

        if (prenom.length()==0){
            messageErreur+="Veuillez entrer un prénom valide.\n";
            indiceErreur+=1;

        }
        if (nom.length()==0){
            messageErreur+="Veuillez entrer un nom valide.\n";
            indiceErreur+=1;
        }
        if (email.length()>=3){
            String substring = email.substring(1, email.length() - 1);
            if ((substring.indexOf("@") == -1)||(substring.indexOf("@") == 0)){
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
