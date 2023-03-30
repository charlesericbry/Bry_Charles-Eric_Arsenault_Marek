package client.InterfaceGraphique;

import client.Commande;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import server.models.Course;
import server.models.RegistrationForm;


public class Modele {

    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Commande charger;
    private Commande inscrire;
    private ArrayList<Course> liste_cours;

    /**
     * Initialise le client, tourne en continu pour envoyer les nouvelles commandes
     */
    public Modele(int port) throws IOException {
        this.client = new Socket("127.0.0.1", port);
    }

    public void run(){
        while(true) {
            try {
                String session = null;
                ArrayList<Course> coursOfferts = charger(session);
                inscrire();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                default:
                    throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 3. Merci.");

            }

            this.objectOutputStream.writeObject(charger);
            System.out.println("Les cours offerts pendant la session d'"+charger.getSession()+" sont:");
            this.objectOutputStream.flush();
            return coursOfferts();

        }catch(Exception e){
            System.out.println("Erreur lors du chargement des cours.");
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Course> coursOfferts() throws IOException, ClassNotFoundException {
        this.liste_cours =(ArrayList<Course>) this.objectInputStream.readObject();
        return this.liste_cours;

    }

    public void inscrire(){

    }
}
