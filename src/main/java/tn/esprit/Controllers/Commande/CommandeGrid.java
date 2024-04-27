package tn.esprit.Controllers.Commande;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import tn.esprit.entites.Commande;

import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class CommandeGrid implements Initializable {


    @FXML
    private Label GridNum_commande;

    @FXML
    private Label GridCode_promo;

    @FXML
    private Label GridType;
    @FXML
    private Label GridEmail;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setData(Commande commande) {
        GridNum_commande.setText(commande.getNum_commande());
        GridCode_promo.setText(commande.getCode_promo());
        GridType.setText(commande.getType_paiement());
        GridEmail.setText(commande.getType_paiement());

        }
    }

