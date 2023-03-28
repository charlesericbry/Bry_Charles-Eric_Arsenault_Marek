package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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

        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        System.out.println(num);
        charger(num);
        coursOfferts(num);
        int num2 = scanner.nextInt();

    }

    public void charger(int num) {
        try {
            Commande charger;
            switch (num) {
                case 1:
                    charger = new Commande("CHARGER","Automne");
                    break;
                case 2:
                    charger = new Commande("CHARGER","Hiver");
                    break;
                case 3:
                    charger = new Commande("CHARGER","Été");
                    break;
                default:
                    System.out.println("Commande inconnue. Veuillez entrer un nombre entre 1 et 3. Merci!");
                    charger = null;
            }
            System.out.println(charger.toString());
            this.objectOutputStream.writeObject(charger);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Commande inconnue. Veuillez entrer un nombre entre 1 et 3. Merci!");
            System.out.print(">Choix:");
        }
    }

    public void coursOfferts(int num) throws IOException, ClassNotFoundException {
        System.out.println("Les cours offerts pendant la session d'automne sont:");
        objectInputStream = new ObjectInputStream(client.getInputStream());
        String cours = (String) objectInputStream.readObject().toString();
        System.out.println(cours);
        //String cours = (String) objectOutputStream.split("\t")readObject().toString();
        objectInputStream.close();
    }

}
