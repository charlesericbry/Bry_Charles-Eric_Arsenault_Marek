package client;

import java.net.Socket;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
        try {
            Socket client = new Socket("127.0.0.1", 1337);
            while (true) {
                System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:");
                System.out.println("1. Automne");
                System.out.println("2. Hiver");
                System.out.println("3. Été");
                System.out.print(">Choix:");
                Scanner scanner = new Scanner(System.in);
                int num = scanner.nextInt();
                System.out.println(num);
                charger(num);

            }

        } catch (Exception e) {
            System.out.println("Raté plus");
        }
    }

    public static void charger(int num) {
            try {
                switch (num) {

                    case 1:
                        Charger charger=new Charger("Automne");
                        break;
                    case 2:
                        Charger charger=new Charger("Hiver");
                        break;
                    case 3:
                        Charger charger=new Charger("Été");
                        break;
                    default:
                        System.out.println("Commande inconnue. Veuillez entrer un nombre entre 1 et 3. Merci!");
                }
            } catch (Exception e) {
                System.out.println("Commande inconnue. Veuillez entrer un nombre entre 1 et 3. Merci!");
                System.out.print(">Choix:");
            }
    }

}
