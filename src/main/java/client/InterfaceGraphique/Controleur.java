package client.InterfaceGraphique;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.EventHandler;
import server.models.Course;

import java.util.ArrayList;

public class Controleur implements EventHandler{

    private Modele modele;

    public ObservableList<Course> charger(String session){
        System.out.println(this.modele.charger(session));
        ArrayList<Course> tableCours = this.modele.charger(session);
        System.out.println(this.modele.charger(session).get(0).getCode());
        //ObservableList<Course> tableCoursString = null;
        ObservableList<Course> tableCoursInfo =  FXCollections.observableArrayList();
        //ObservableList<String[]> data = FXCollections.observableArrayList();
            for (int i = 0;i<tableCours.size();i++){
                //data.add(new String[]{tableCours.get(i).getCode(),tableCours.get(i).getName()});
               tableCoursInfo.add(new Course(this.modele.charger(session).get(i).getCode(),
                       this.modele.charger(session).get(i).getName(),session));
            }
            return tableCoursInfo;

    }
    @Override
    public void handle(String cmd, String arg) {

    }
}
