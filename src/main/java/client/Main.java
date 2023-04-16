package client;

import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    public final static int PORT = 1337;
    public static void main(String[] args) {
        try {
            Client client = new Client(PORT);
            client.run();
        }catch (ConnectException e) {
            System.out.println("Erreur lors de la connexion");

        }catch (Exception e) {
                e.printStackTrace();
            }
    }



}
