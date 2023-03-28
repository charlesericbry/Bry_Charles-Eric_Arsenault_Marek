package client;

import server.models.Course;

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

        while (true) {
            try {
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectInputStream = new ObjectInputStream(client.getInputStream());
                commander();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Raté plus");
            }
        }
    }

    public void commander() throws IOException, ClassNotFoundException {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        String num = scanner.nextLine();
        try {
            charger(num);
            coursOfferts();
            System.out.println(">Choix:");
            System.out.println("1. Consulter les cours offerts pour une autre session");
            System.out.println("2. Inscription à un cours");
            System.out.println(">Choix:");
            //objectOutputStream.close();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            commander();

        }
        String num2 = scanner.nextLine();

        try {
            choixProcedure(num2);
        } catch (IllegalArgumentException e) {
            System.out.println(e);

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
                    charger = new Commande("CHARGER", "Ete");
                    break;
                default:
                    throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 3. Merci.");

            }
            System.out.println("Les cours offerts pendant la session d'"+charger.getSession()+" sont:");
            this.objectOutputStream.writeObject(charger);
            this.objectOutputStream.flush();
        }


    public void coursOfferts() throws IOException, ClassNotFoundException {
        System.out.println("hello");
        ArrayList<Course> liste_cours =(ArrayList<Course>) this.objectInputStream.readObject();
        for(int i=0; i<liste_cours.size(); i++){
            System.out.println((i+1)+". "+liste_cours.get(i).getCode()+"\t"+liste_cours.get(i).getName());
        }

        //this.objectInputStream.close();
    }

    public void choixProcedure(String num) throws IOException, ClassNotFoundException {
        if (num.equals("1")){
            commander();
        } else if (num.equals("2")) {
            inscription();
        }else{
            throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 2. Merci.");
        }
    }

    public void inscription(){
        System.out.print("Veuillez saisir votre prénom: ");
        Scanner scanner = new Scanner(System.in);
        String prenom = scanner.nextLine();
        System.out.print(prenom+"\n");
        System.out.print("Veuillez saisir votre nom: ");
        String nom = scanner.nextLine();
        System.out.print(nom+"\n");
        System.out.print("Veuillez saisir votre email: ");
        String email = scanner.nextLine();
        System.out.print(email+"\n");
        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = scanner.nextLine();
        System.out.print(matricule+"\n");
        System.out.print("Veuillez saisir le code du cours: ");
        String code = scanner.nextLine();
        System.out.print(code+"\n");



    }
}

