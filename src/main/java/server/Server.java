package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * La classe server gère le serveur après son démarrage.
 */
public class Server {
    /**
     * Le nom de la commande pour inscrire un client à un cours.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * Le nom de la commande pour charger.
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Initialise le serveur en fonction du port d'entrée.
     *
     * @param port port de connection du serveur,
     * @throws IOException IOException si une erreur survient lors de la création du socket serveur
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * addEventHandler ajoute un gestionnaire d'événement.
     *
     * @param h gestionnaire d'événement que l'on veut rajouter à la classe.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Accepte et imprime 'connecté au client' lorsqu'il y a connection. Reçoit le flux
     * d'entrée, écoute les commandes du client et alerte le gestionnaire d'évènement.
     * Ferme le flux et la connection lorsque le client se déconnecte.
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Traite le fil de commandes du client et alerte les gestionnaires d'événements correspondants.
     *
     * @throws IOException            si une erreur survient lors de la lecture de l'entrée du client
     * @throws ClassNotFoundException si la classe de l'objet envoyé par le client ne peut pas être trouvée
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Traite la ligne de commande qui lui est fournie en retournant un pairage entre une commande
     * et son argument.
     *
     * @param line La ligne qui contient la commande et ses arguments
     * @return Une liste pairée entre commande (clé) et son argument (valeur)
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Ferme tous les streams, termine la communication entre le serveur et le client.
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Gère les commandes d'enregistrement et de chargement
     *
     * @param cmd Type de commande reçu 'Inscrire' ou 'Commande'
     * @param arg Argument de la commande, information supplémentaire sur la personne qui s'inscrit, ou bien
     *            sur les cours etc...
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les transorfmer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     *
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try {
            FileReader courseReader = new FileReader("../../../src/main/java/server/data/cours.txt");
            
            BufferedReader reader = new BufferedReader(courseReader);
            String line;
            ArrayList<Course> liste_cours = new ArrayList<Course>();
            while ((line = reader.readLine()) != null) {
                String regex = "^IFT\\d{4}\t.+\\t(Hiver|Ete|Automne)$\\n?";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                if(!matcher.matches()){
                    throw new IllegalArgumentException("Mauvais format du fichier");
                }

                String[] parts = line.split("\t");
                String code_du_cours = parts[0];
                String nom_du_cours = parts[1];
                String session = parts[2];
                if (session.equals(arg)) {
                    Course cours = new Course(nom_du_cours, code_du_cours, session);
                    liste_cours.add(cours);
                }
            }
            reader.close();
            this.objectOutputStream.writeObject(liste_cours);
            this.objectOutputStream.flush();
            listen();
        }catch (IllegalArgumentException e) {

                System.err.println(e.getMessage());

            }
          catch (ClosedByInterruptException e) {
            System.out.println("Interruption lors de l'écriture ou la lecture");
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'a pas été trouvé : " + e.getMessage());

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());

        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());

        }
    }

    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            ObjectInputStream is = this.objectInputStream;
            RegistrationForm r = (RegistrationForm) is.readObject();

            FileWriter fw = new FileWriter("../../../src/main/java/server/data/inscription.txt", true);

            BufferedWriter writer = new BufferedWriter(fw);
            String informations = (r.getCourse().getSession() + "\t" +
                    r.getCourse().getCode() + "\t" + r.getMatricule() + "\t" +
                    r.getPrenom() + "\t" + r.getNom() + "\t" + r.getEmail() + "\n");
            writer.append(informations);
            writer.flush();


        } catch (ClosedByInterruptException e) {
            System.out.println("Interruption lors de l'écriture ou la lecture");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}


