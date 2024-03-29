package tn.esprit.entites;

import java.util.HashSet;
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
    private Set<Destination> destinations = new HashSet<>();

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

    @Override
    public String toString() {
        return "User{" +
                "Username=" + username +
                ", role='" + role + '\'' +
                '}';
    }
}
