package tn.esprit.entites;

import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private String nom;
    private Date date_debut;
    private Date date_fin;
    private String lieu;
    private String description;
    private String organisateur;
    private String image;
    private double prix;
    private List<Participation> participations;


    public Event() {
    }

    public Event(int id, String nom, Date date_debut, Date date_fin, String lieu, String description, String organisateur, String image, double prix) {
        this.id = id;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.lieu = lieu;
        this.description = description;
        this.organisateur = organisateur;
        this.image = image;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                ", organisateur='" + organisateur + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                '}';
    }
}
