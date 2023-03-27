package server.models;

import java.io.Serializable;
/**
 * Classe contenant les informations d'un cours.
 */
public class Course implements Serializable {


    private String name;
    private String code;
    private String session;

    /**
     * Construit un cour.
     * @param name nom du cour en question
     * @param code code du cour
     * @param session session auquel le cour a lieu
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     *
     * @return retourne le nom
     */
    public String getName() {
        return name;
    }

    /**
     * Permet de changer le nom du cours
     * @param name nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return retourne le code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * Permet de changer le code du cours
     * @param code code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return retourne la session du cours
     */
    public String getSession() {
        return session;
    }

    /**
     *
     * @param session Change la session du cours
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     *
     * @return retourne les informations du cours dans une seule phrase.
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
