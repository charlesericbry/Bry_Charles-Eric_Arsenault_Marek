package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client {

    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private static String message="Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:\n 1. Automne\n 2. Hiver\n 3. Été\n>Choix:";
    private Commande charger;
    private Commande inscrire;
    private ArrayList<Course> liste_cours;


    /**
     * Initialise le client, tourne en continu pour envoyer les nouvelles commandes
     */
    public Client(int port) throws IOException, ConnectException {
        this.client = new Socket("127.0.0.1", port);
    }

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
                System.out.println("Erreur lors de la procédure.");
                e.printStackTrace();
                break;
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
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            commander();

        }
        String num2 = scanner.nextLine();

        try {
            choixProcedure(num2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            num2 = scanner.nextLine();
            choixProcedure(num2);
        }
    }

    public void charger(String num) throws IllegalArgumentException {
        try{
            switch (num) {
                case "1":
                    this.charger = new Commande("CHARGER", "Automne");

                    break;
                case "2":
                    this.charger = new Commande("CHARGER", "Hiver");
                    break;
                case "3":
                    this.charger = new Commande("CHARGER", "Ete");
                    break;
                default:
                    throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 3. Merci.");

            }

            this.objectOutputStream.writeObject(charger);
        System.out.println("Les cours offerts pendant la session d'"+charger.getSession()+" sont:");
            this.objectOutputStream.flush();
        }catch(IOException e){
            System.out.println("Erreur lors du chargement des cours.");
        }
    }

    private void coursOfferts() throws IOException, ClassNotFoundException, EOFException {
        this.liste_cours =(ArrayList<Course>) this.objectInputStream.readObject();
        for(int i=0; i<liste_cours.size(); i++){
            System.out.println((i+1)+". "+liste_cours.get(i).getCode()+"\t"+liste_cours.get(i).getName());
        }

    }

    private void choixProcedure(String num) throws IOException, ClassNotFoundException {
        if (num.equals("1")){
            commander();
        } else if (num.equals("2")) {
            inscription();
        }else{
            throw new IllegalArgumentException("Veuillez entrer un nombre entre 1 et 2. Merci.");
        }
    }

    public void inscription() {
        try {
            System.out.print("Veuillez saisir votre prénom: ");
            Scanner scanner = new Scanner(System.in);
            String prenom = scanner.nextLine();
            while (prenom.length()==0) {
                System.out.println("Veuillez entrer un prénom valide.\n");
                scanner = new Scanner(System.in);
                prenom = scanner.nextLine();
            }

            System.out.print("Veuillez saisir votre nom: ");
            String nom = scanner.nextLine();
            while (nom.length()==0) {
                System.out.println("Veuillez entrer un prénom valide.\n");
                scanner = new Scanner(System.in);
                nom = scanner.nextLine();
            }
            String email = emailValide();
            String matricule = choixMatricule();
            Course mon_cours = cours();
            inscrire = new Commande("INSCRIRE", "");
            this.objectOutputStream.writeObject(inscrire);
            this.objectOutputStream.flush();
            RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, mon_cours);
            System.out.println("\nFélicitation! Inscription réussie de " + prenom + " " + nom + " au cours " + mon_cours.getCode() + ".");
            this.objectOutputStream.writeObject(form);
            this.objectOutputStream.flush();
        }catch(Exception e){
            System.out.println("Erreur dans l'inscription. Veuillez recommencer.");
            inscription();
        }

    }
    private Course cours() {

        System.out.print("Veuillez saisir le code du cours: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        for (int i = 0; i <= this.liste_cours.size(); i++) {
            if (this.liste_cours.get(i).getCode().equals(code)) {
                return liste_cours.get(i);
            }
            if (i == this.liste_cours.size()-1) {
                System.out.println("Ce cours n'existe pas, ou bien n'est pas offert durant cette session.");
                return cours();
            }
        }
        return null;
    }

    private String emailValide(){
        System.out.print("Veuillez saisir votre email: ");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        String regex = "^[\\w-_.+]*[\\w-_.]@[\\w]+([\\w-.]+[\\w-])?\\.\\w{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return email;
        }else{
            System.out.println("Veuillez entrer un email valide.");
            return emailValide();
        }
    }

    private String choixMatricule(){
        System.out.print("Veuillez saisir votre matricule: ");
        Scanner scanner = new Scanner(System.in);
        String matricule = scanner.nextLine();
        boolean estNombre = true;
        for (int j = 0; j < matricule.length(); j++) {
            if (!Character.isDigit(matricule.charAt(j))) {
                estNombre = false;
                break;
            }
        }
        if (matricule.length()!=8||!estNombre){
            System.out.println("Veuillez entrer un matricule à 8 chiffres.");
            return choixMatricule();
        }else{
            return matricule;
        }
    }
}

