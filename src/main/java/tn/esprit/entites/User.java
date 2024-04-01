package tn.esprit.entites;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private int id;
    private String nom;
    private String prenom;

    private String email;
    private String password;
    private int cin;
    private String photo;
    private String username;
    private Role role;

    //Relations :
    private List<Avis> avisList;
    private List<Hebergement> hebergements;
    private List<Participation> participations;
    private List<Reservation> reservations;

    private Set<Destination> destinations = new HashSet<>();

    public void setAvisList(List<Avis> avisList) {
        this.avisList = avisList;
    }
    public List<Avis> getAvisList() {
        return avisList;
    }
    public List<Hebergement> getHebergements() {
        return hebergements;
    }

    public void setHebergements(List<Hebergement> hebergements) {
        this.hebergements = hebergements;
    }
    public void addAvis(Avis avis) {
        this.avisList.add(avis);
    }
    public void addHebergement(Hebergement hebergement) {
        this.hebergements.add(hebergement);
    }
    public void addParticipation(Participation participation) {
        this.participations.add(participation);
    }
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    public User(String nom, String prenom, String email, String password, int cin, String photo, String username, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.cin = cin;
        this.photo = photo;
        this.username = username;
        this.role = role;
    }
    public  User(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getPhoto() {
        return photo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username=" + username +
                ", role='" + role + '\'' +
                '}';
    }
}
