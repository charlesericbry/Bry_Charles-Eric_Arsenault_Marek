package client;

import java.net.Socket;

public class Main {

    public static void main(String[] args){
        try{
            Socket clientSocket = new Socket("127.0.0.1", 1337);
            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours:");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Été");
            while (true){
                

                //System.out.println(">Choix:"+choixSession);
            }




        }catch(Exception e){

        }



    }
}
