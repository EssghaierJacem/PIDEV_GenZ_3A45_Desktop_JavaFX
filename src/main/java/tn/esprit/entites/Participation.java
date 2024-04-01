package tn.esprit.entites;

public class Participation {
    private int id;
    private User user;
    private Event event;
    private String nom;
    private String prenom;
    private int tel;
    private String email;

    public Participation() {
    }

    public Participation(int id, User user, Event event, String nom, String prenom, int tel, String email) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", user=" + user +
                ", event=" + event +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                '}';
    }
}
