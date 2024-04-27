package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;

import tn.esprit.entites.Commande;
import tn.esprit.services.CommandeServices;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddCommandeController implements Initializable {

    @FXML
    private JFXButton AddCommande;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addNum_commande;


    @FXML
    private TextField addCode_promo;

    @FXML
    private TextField addType;

    @FXML
    private TextField addEmail;

    @FXML
    private DatePicker addDateC;

    @FXML
    void handleAddCommandeButtonAction(ActionEvent event) {
        Commande newCommande = new Commande();
        newCommande.setNum_commande(addNum_commande.getText());
        newCommande.setCode_promo(addCode_promo.getText());
        newCommande.setEmail(addEmail.getText());
        newCommande.setType_paiement(addType.getText());
        newCommande.setDate_commande(java.sql.Date.valueOf(addDateC.getValue()));
        CommandeServices commandeServices = new CommandeServices();
        commandeServices.addCommande(newCommande);



        clearFields();
    }

    private void clearFields() {
        addNum_commande.clear();
        addCode_promo.clear();
        addType.clear();
        addEmail.clear();
        addDateC.setValue(null);
    }
    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            addNum_commande.setPromptText("");
            addCode_promo.setPromptText("");
            addType.setPromptText("");
            addEmail.setPromptText("");

            // Example of setting a default value for the date picker
            addDateC.setValue(LocalDate.now());
        }
}
