package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Commande commande;
    private static String message="Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:\n 1. Automne\n 2. Hiver\n 3. Été\n>Choix:";

    public Client(int port) throws IOException {
        this.client = new Socket("127.0.0.1", port);
    }

    /**
     * Initialise le client, tourne en continu pour envoyer les nouvelles commandes
     */

    public void run(){
        System.out.println("Connecté au serveur!");
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
        System.out.println(message);
        while (true) {
            try {


                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                commander();
            } catch (Exception e) {
                System.out.println("Raté plus");
            }
        }
    }

    public void commander() throws IOException, ClassNotFoundException{
        try {
            Scanner scanner = new Scanner(System.in);
            String num = scanner.nextLine();
            charger(num);
            //coursOfferts();
            //int num2 = scanner.nextInt();
        }catch(IllegalArgumentException e) {
            System.out.println(e);
            commander();
         }
    }

    public void charger(String num) throws IOException,IllegalArgumentException {

            Commande charger;
            switch (num) {
                case "1":
                    charger = new Commande("CHARGER", "Automne");

                    break;
                case "2":
                    charger = new Commande("CHARGER", "Hiver");
                    break;
                case "3":
                    charger = new Commande("CHARGER", "Été");
                    break;
                default:
                    throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 3. Merci.");
                    //charger = null;
            }
            System.out.println(charger);
            this.objectOutputStream.writeObject(charger);
            this.objectOutputStream.flush();
        }


    public void coursOfferts() throws IOException, ClassNotFoundException {
        System.out.println("Les cours offerts pendant la session d'automne sont:");
        objectInputStream = new ObjectInputStream(client.getInputStream());
        //lireArrayList(objectInputStream);
        //for (int i = 0; i <= list.size(); i++ )
        System.out.println("test");
        String cours = (String) objectInputStream.readObject();
        System.out.println(cours);

        objectInputStream.close();
    }


    public void lireArrayList(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ArrayList<Object> list = new ArrayList<Object>();

        try {
            while (true) {
                Object obj = in.readObject();
                list.add(obj);
            }
        } catch (EOFException e) {
            // La fin du flux a été atteinte, il n'y a plus d'objets à lire
        }
    }
}

