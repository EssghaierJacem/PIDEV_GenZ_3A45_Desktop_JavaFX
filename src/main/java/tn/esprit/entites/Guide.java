package tn.esprit.entites;

import java.util.List;

public class Guide {
    private int id;
    private String nom;
    private String prenom;
    private String nationalite;
    private String langues_parlees;
    private String experiences;
    private double tarif_horaire;
    private int num_tel;
    private List<Tournee> tournees;
    public Guide() {
    }

    public Guide(int id, String nom, String prenom, String nationalite, String langues_parlees, String experiences, double tarif_horaire, int num_tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.langues_parlees = langues_parlees;
        this.experiences = experiences;
        this.tarif_horaire = tarif_horaire;
        this.num_tel = num_tel;
    }

    public Guide(String nom, String prenom, String nationalite, String langues_parlees, String experiences, double tarif_horaire, int num_tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.langues_parlees = langues_parlees;
        this.experiences = experiences;
        this.tarif_horaire = tarif_horaire;
        this.num_tel = num_tel;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getLangues_parlees() {
        return langues_parlees;
    }

    public void setLangues_parlees(String langues_parlees) {
        this.langues_parlees = langues_parlees;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public double getTarif_horaire() {
        return tarif_horaire;
    }

    public void setTarif_horaire(double tarif_horaire) {
        this.tarif_horaire = tarif_horaire;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public List<Tournee> getTournees() {
        return tournees;
    }

    public void setTournees(List<Tournee> tournees) {
        this.tournees = tournees;
    }

    @Override
    public String toString() {
        return ""+ nom +"," + prenom +".";
    }
}
