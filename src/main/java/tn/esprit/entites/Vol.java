package tn.esprit.entites;

import java.util.Date;
public class Vol {
    private int  id;
    private Destination  destination;
    private String  compagnie_a;
    private int  num_vol;
    private String  aeroport_depart;
    private String  aeroport_arrivee;
    private Date date_depart;
    private Date  date_arrivee;
    private int  duree_vol;
    private float tarif;
    private String  escale;
    private String  image;
    private Classe  classe;
    public Vol() {
    }
    public Vol(int id, Destination destination, String compagnie_a, int num_vol, String aeroport_depart, String aeroport_arrivee, Date date_depart, Date date_arrivee, int duree_vol, float tarif, String escale, String image, Classe classe) {
        this.id = id;
        this.destination = destination;
        this.compagnie_a = compagnie_a;
        this.num_vol = num_vol;
        this.aeroport_depart = aeroport_depart;
        this.aeroport_arrivee = aeroport_arrivee;
        this.date_depart = date_depart;
        this.date_arrivee = date_arrivee;
        this.duree_vol = duree_vol;
        this.tarif = tarif;
        this.escale = escale;
        this.image = image;
        this.classe = classe;
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

    public String getCompagnie_a() {
        return compagnie_a;
    }

    public void setCompagnie_a(String compagnie_a) {
        this.compagnie_a = compagnie_a;
    }

    public int getNum_vol() {
        return num_vol;
    }

    public void setNum_vol(int num_vol) {
        this.num_vol = num_vol;
    }

    public String getAeroport_depart() {
        return aeroport_depart;
    }

    public void setAeroport_depart(String aeroport_depart) {
        this.aeroport_depart = aeroport_depart;
    }

    public String getAeroport_arrivee() {
        return aeroport_arrivee;
    }

    public void setAeroport_arrivee(String aeroport_arrivee) {
        this.aeroport_arrivee = aeroport_arrivee;
    }

    public Date getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(Date date_depart) {
        this.date_depart = date_depart;
    }

    public Date getDate_arrivee() {
        return date_arrivee;
    }

    public void setDate_arrivee(Date date_arrivee) {
        this.date_arrivee = date_arrivee;
    }

    public int getDuree_vol() {
        return duree_vol;
    }

    public void setDuree_vol(int duree_vol) {
        this.duree_vol = duree_vol;
    }

    public float getTarif() {
        return tarif;
    }

    public void setTarif(float tarif) {
        this.tarif = tarif;
    }

    public String getEscale() {
        return escale;
    }

    public void setEscale(String escale) {
        this.escale = escale;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "destination=" + destination +
                ", compagnie_a='" + compagnie_a + '\'' +
                ", aeroport_depart='" + aeroport_depart + '\'' +
                ", aeroport_arrivee='" + aeroport_arrivee + '\'' +
                '}';
    }
}
