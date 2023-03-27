package server.models;

import java.io.Serializable;

/**
 * Classe contenant les informations entrées par le client sur le registration
 * form (formulaire d'enregistrement)
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * Construit le formulaire d'enregistrement.
     *
     * @param prenom       prénom de l'étudiant
     * @param nom             nom de l'étudiant
     * @param email         email de l'étudiant
     * @param matricule matricule de l'étudiant
     * @param course               cours choisi
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }
    /**
     * Retourne prénom de l'étudiant.
     *
     * @return prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }
    /**
     * Modifie le prénom de l'étudiant.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    /**
     * Retourne nom de l'étudiant.
     *
     * @return nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }
    /**
     *Modifie le nom de l'étudiant.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * Retourne le email de l'étudiant.
     *
     * @return email de l'étudiant
     */
    public String getEmail() {
        return email;
    }
    /**
     * Modifie l'email de l'étudiant.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Retourne le matricule de l'étudiant.
     *
     * @return matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }
    /**
     * Modifie le matricule de l'étudiant.
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    /**
     * Retourne le cours choisi.
     *
     * @return cours choisi de la classe Course
     */
    public Course getCourse() {
        return course;
    }
    /**
     *Modifie le cours choisi
     */
    public void setCourse(Course course) {
        this.course = course;
    }
    /**
     * Crée un String avec toutes les informations du registration form
     *
     * @return informations du registration form sous la forme
     * InscriptionForm{prenom=prenom, nom=nom, email=email, matricule=matricule, course=course}
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
