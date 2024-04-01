package tn.esprit.entites;

import java.util.List;

public class Categorie_h {
    private int id;
    private String type;
    private String description;
    private List<Hebergement> hebergements;
    public Categorie_h() {
    }

    public Categorie_h(int id, String type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Hebergement> getHebergements() {
        return hebergements;
    }
    public void setHebergements(List<Hebergement> hebergements) {
        this.hebergements = hebergements;
    }
    public void addHebergement(Hebergement hebergement) {
        this.hebergements.add(hebergement);
    }

    @Override
    public String toString() {
        return "Categorie_h{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
