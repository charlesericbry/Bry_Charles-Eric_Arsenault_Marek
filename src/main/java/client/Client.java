package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Commande commande;

    public Client() throws IOException {
        Socket client = new Socket("127.0.0.1", 1337);
    }
    public void run(){

    }

}
