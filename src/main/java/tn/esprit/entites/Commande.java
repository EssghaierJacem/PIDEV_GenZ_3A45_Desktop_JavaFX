package tn.esprit.entites;

import java.util.Date;
import java.util.List;

public class Commande {
    private int id;
    private String num_commande;
    private double prix;
    private String code_promo;
    private String type_paiement;
    private String email;
    private Date date_commande;

    public Commande() {
    }

    public Commande(int id, String num_commande, double prix, String code_promo, String type_paiement, String email, Date date_commande) {
        this.id = id;
        this.num_commande = num_commande;
        this.prix = prix;
        this.code_promo = code_promo;
        this.type_paiement = type_paiement;
        this.email = email;
        this.date_commande = date_commande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_commande() {
        return num_commande;
    }

    public void setNum_commande(String num_commande) {
        this.num_commande = num_commande;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCode_promo() {
        return code_promo;
    }

    public void setCode_promo(String code_promo) {
        this.code_promo = code_promo;
    }

    public String getType_paiement() {
        return type_paiement;
    }

    public void setType_paiement(String type_paiement) {
        this.type_paiement = type_paiement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", num_commande='" + num_commande + '\'' +
                ", prix=" + prix +
                ", code_promo='" + code_promo + '\'' +
                ", type_paiement='" + type_paiement + '\'' +
                ", email='" + email + '\'' +
                ", date_commande=" + date_commande +
                '}';
    }
}
