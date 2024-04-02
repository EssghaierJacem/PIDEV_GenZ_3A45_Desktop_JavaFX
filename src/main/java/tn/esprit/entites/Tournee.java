package tn.esprit.entites;

import java.util.Date;

public class Tournee {
    private int id;
    private Destination destination;
    private Guide guide;
    private String nom;
    private Date date_debut;
    private String duree;
    private String description;
    private double tarif;
    private String monuments;
    private String tranche_age;
    private String moyen_transport;

    public Tournee() {
    }

    public Tournee(int id, Destination destination, Guide guide, String nom, Date date_debut, String duree, String description, double tarif, String monuments, String tranche_age, String moyen_transport) {
        this.id = id;
        this.destination = destination;
        this.guide = guide;
        this.nom = nom;
        this.date_debut = date_debut;
        this.duree = duree;
        this.description = description;
        this.tarif = tarif;
        this.monuments = monuments;
        this.tranche_age = tranche_age;
        this.moyen_transport = moyen_transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
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

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public String getMonuments() {
        return monuments;
    }

    public void setMonuments(String monuments) {
        this.monuments = monuments;
    }

    public String getTranche_age() {
        return tranche_age;
    }

    public void setTranche_age(String tranche_age) {
        this.tranche_age = tranche_age;
    }

    public String getMoyen_transport() {
        return moyen_transport;
    }

    public void setMoyen_transport(String moyen_transport) {
        this.moyen_transport = moyen_transport;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "id=" + id +
                ", destination=" + destination +
                ", guide=" + guide +
                ", nom='" + nom + '\'' +
                ", date_debut=" + date_debut +
                ", duree='" + duree + '\'' +
                ", description='" + description + '\'' +
                ", tarif=" + tarif +
                ", monuments='" + monuments + '\'' +
                ", tranche_age='" + tranche_age + '\'' +
                ", moyen_transport='" + moyen_transport + '\'' +
                '}';
    }
}
