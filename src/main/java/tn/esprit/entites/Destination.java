package tn.esprit.entites;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Destination {
    private int id;
    private String pays;
    private String ville;
    private String description;
    private String attractions;

    private String accomodation;
    private String devise;
    private String multimedia;
    private String cuisine_locale;
    private Boolean accessibilite;
    private String abbrev;
    private Set<User> users = new HashSet<>();
    private List<Tournee> tournees;
    public Set<User> getUsers() {
        return users;
    }


    public void setUsers(Set<User> users) {
        this.users = users;
    }
    public Destination() {

    }

    public Destination( String pays, String ville, String description, String attractions, String accomodation, String devise, String multimedia, String cuisine_locale, Boolean accessibilite, String abbrev) {
        this.pays = pays;
        this.ville = ville;
        this.description = description;
        this.attractions = attractions;
        this.accomodation = accomodation;
        this.devise = devise;
        this.multimedia = multimedia;
        this.cuisine_locale = cuisine_locale;
        this.accessibilite = accessibilite;
        this.abbrev = abbrev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }

    public String getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(String accomodation) {
        this.accomodation = accomodation;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public String getCuisine_locale() {
        return cuisine_locale;
    }

    public void setCuisine_locale(String cuisine_locale) {
        this.cuisine_locale = cuisine_locale;
    }

    public Boolean getAccessibilite() {
        return accessibilite;
    }

    public void setAccessibilite(Boolean accessibilite) {
        this.accessibilite = accessibilite;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public List<Tournee> getTournees() {
        return tournees;
    }

    public void setTournees(List<Tournee> tournees) {
        this.tournees = tournees;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "pays='" + pays + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
