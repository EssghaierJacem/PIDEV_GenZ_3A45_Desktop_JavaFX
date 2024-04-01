package tn.esprit.entites;

public class Hebergement {
    private int id;
    private User user;
    private Categorie_h categorie_h;
    private String nom_h;
    private String adresse;
    private int nbr_etoile;
    private int capacite;
    private double tarif_par_nuit;

    public Hebergement() {
    }

    public Hebergement(int id, User user, Categorie_h categorie_h, String nom_h, String adresse, int nbr_etoile, int capacite, double tarif_par_nuit) {
        this.id = id;
        this.user = user;
        this.categorie_h = categorie_h;
        this.nom_h = nom_h;
        this.adresse = adresse;
        this.nbr_etoile = nbr_etoile;
        this.capacite = capacite;
        this.tarif_par_nuit = tarif_par_nuit;
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

    public Categorie_h getCategorie_h() {
        return categorie_h;
    }

    public void setCategorie_h(Categorie_h categorie_h) {
        this.categorie_h = categorie_h;
    }

    public String getNom_h() {
        return nom_h;
    }

    public void setNom_h(String nom_h) {
        this.nom_h = nom_h;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbr_etoile() {
        return nbr_etoile;
    }

    public void setNbr_etoile(int nbr_etoile) {
        this.nbr_etoile = nbr_etoile;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public double getTarif_par_nuit() {
        return tarif_par_nuit;
    }

    public void setTarif_par_nuit(double tarif_par_nuit) {
        this.tarif_par_nuit = tarif_par_nuit;
    }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", user=" + user +
                ", categorie_h=" + categorie_h +
                ", nom_h='" + nom_h + '\'' +
                ", adresse='" + adresse + '\'' +
                ", nbr_etoile=" + nbr_etoile +
                ", capacite=" + capacite +
                ", tarif_par_nuit=" + tarif_par_nuit +
                '}';
    }
}
