package client.InterfaceGraphique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import server.models.Course;

import java.util.ArrayList;

/**
 * Classe contrôleur. Gère les transitions entre la classe vue et la classe modèle
 */
public class Controleur{
    private Modele modele;
    private Vue vue;
    /**
     * Table pour charger les cours
     */
    public TableView<Course> table;

    /**
     * Constructeur de la classe contrôleur
     *
     * @param m instance de la classe modèle
     * @param table instance de la table pour charger les cours
     */
    public Controleur(Modele m,TableView<Course> table){
            this.table = table;
            this.modele = m;
    }

    /**
     * Charge les cours et les inscrit dans la table.
     *
     * @param session Session choisie par l'interface graphique
     * @throws NullPointerException Tient en compte de l'exception où une erreur n'engendre aucune valeur, par
     * exemple lorsque le serveur ne fournit pas l'information nécessaire.
     */
    public void charger(String session) throws NullPointerException{
        ArrayList<Course> tableCours = modele.charger(session);
        ObservableList<Course> tableCoursInfo =  FXCollections.observableArrayList();
            for (int i = 0;i<tableCours.size();i++){
               tableCoursInfo.add(new Course(modele.charger(session).get(i).getName(),
                       modele.charger(session).get(i).getCode(), session));
            }
        this.table.setItems(tableCoursInfo);
    }

    /**
     * Transmet l'information entrée dans l'interface graphique au modèle, puis renvoie cette
     * information à la vue pour afficher les résultats de la gestion par le modèle.
     *
     * @param prenom
     * @param nom
     * @param email
     * @param matricule
     * @param coursSelectionne
     * @throws Exception
     */
    public void inscrire(String prenom, String nom,String email,String matricule,Course coursSelectionne) throws Exception {
        modele.inscription(prenom,nom,email,matricule,coursSelectionne);
        modele.setMessage();
    }


}
