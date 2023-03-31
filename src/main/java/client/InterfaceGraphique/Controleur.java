package client.InterfaceGraphique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import server.models.Course;

import java.util.ArrayList;

public class Controleur{
    private Modele modele;
    private Vue vue;
    public TableView<Course> table;

    public Controleur(Modele m,TableView<Course> table){
            this.table = table;
            this.modele = m;
    }









    public void charger(String session) throws NullPointerException{
        System.out.println(modele.charger(session));
        ArrayList<Course> tableCours = modele.charger(session);
        System.out.println(modele.charger(session).get(0).getCode());
        //ObservableList<Course> tableCoursString = null;
        ObservableList<Course> tableCoursInfo =  FXCollections.observableArrayList();
        //ObservableList<String[]> data = FXCollections.observableArrayList();
            for (int i = 0;i<tableCours.size();i++){
                //data.add(new String[]{tableCours.get(i).getCode(),tableCours.get(i).getName()});
               tableCoursInfo.add(new Course(modele.charger(session).get(i).getCode(),
                       modele.charger(session).get(i).getName(),session));
            }
        this.vue.table.setItems(tableCoursInfo);

    }

    public void inscrire(String prenom, String nom,String email,String matricule){

    }

}
