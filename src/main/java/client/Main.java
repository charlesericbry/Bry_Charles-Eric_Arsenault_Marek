package client;

import java.net.Socket;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 1337);
            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Été");
            System.out.print(">Choix:");
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);

                    int num = scanner.nextInt();
                    switch (num) {

                        case 1:
                            System.out.println("Automne");
                            break;
                        case 2:
                            System.out.println("Hiver");
                            break;
                        case 3:
                            System.out.println("Été");
                            break;
                        default:
                            System.out.println("Commande inconnue, veuillez recommencez");
                    }

                    
                } catch (Exception e) {
                    System.out.println("Commande inconnue. Veuillez entrer un nombre entre 1 et 3. Merci!");
                    System.out.print(">Choix:");
                }


            }


        } catch (Exception e) {
            System.out.println("Raté plus");
        }
    }
}
