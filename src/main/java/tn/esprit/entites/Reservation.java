package tn.esprit.entites;

import java.util.Date;

public class Reservation {
    private int id;
    private User user;
    private Commande commande;
    private String nom_client;
    private String prenom_client;
    private int num_tel;
    private int quantite;
    private Date date_reservation;
    private String qr_code;

    public Reservation(String number, String mahmoud, String touhemi, String id, String s, String date, String qrcode) {
    }

    public Reservation(int id, User user, Commande commande, String nom_client, String prenom_client, int num_tel, int quantite, Date date_reservation, String qr_code) {
        this.id = id;
        this.user = user;
        this.commande = commande;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.num_tel = num_tel;
        this.quantite = quantite;
        this.date_reservation = date_reservation;
        this.qr_code = qr_code;
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

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public java.sql.Date getDate_reservation() {
        return (java.sql.Date) date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", commande=" + commande +
                ", nom_client='" + nom_client + '\'' +
                ", prenom_client='" + prenom_client + '\'' +
                ", num_tel=" + num_tel +
                ", quantite=" + quantite +
                ", date_reservation=" + date_reservation +
                ", qr_code='" + qr_code + '\'' +
                '}';
    }
}
