package client;

import java.net.Socket;
import java.util.Scanner;


public class Main {
    public final static int PORT = 1337;
    public static void main(String[] args) {
        try {
            Client client = new Client(PORT);
            client.run();
        }catch (Exception e) {
                e.printStackTrace();
            }
    }



}
