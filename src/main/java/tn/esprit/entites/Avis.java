package tn.esprit.entites;

import java.util.Date;

public class Avis {
    private int id;
    private User user;
    private String commentaire;
    private Date date_publication;

    public Avis() {
    }

    public Avis(int id, User user, String commentaire, Date date_publication) {
        this.id = id;
        this.user = user;
        this.commentaire = commentaire;
        this.date_publication = date_publication;
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", user=" + user +
                ", commentaire='" + commentaire + '\'' +
                ", date_publication=" + date_publication +
                '}';
    }
}
