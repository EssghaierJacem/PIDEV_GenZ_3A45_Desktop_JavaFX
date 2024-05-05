package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import tn.esprit.entites.Commande;
import tn.esprit.services.CommandeServices;

import java.net.MalformedURLException;
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
    private Label errorLabel;

    @FXML
    void handleAddCommandeButtonAction(ActionEvent event) {
        if (validateInputs()) {
        Commande newCommande = new Commande();
        newCommande.setNum_commande(addNum_commande.getText());
        newCommande.setCode_promo(addCode_promo.getText());
        newCommande.setEmail(addEmail.getText());
        newCommande.setType_paiement(addType.getText());
        newCommande.setDate_commande(java.sql.Date.valueOf(addDateC.getValue()));
        CommandeServices commandeServices = new CommandeServices();
        commandeServices.addCommande(newCommande);
        clearFields();}
        else {
            errorLabel.setText("Veuillez remplir tous les champs.");
        }
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
    private boolean validateInputs() {
        String numCommande = addNum_commande.getText();
        String codePromo = addCode_promo.getText();
        String typePaiement = addType.getText();
        String email = addEmail.getText();
        LocalDate dateCommande = addDateC.getValue();

        if (numCommande.length() < 4 || numCommande.length() > 6) {
            errorLabel.setText("Le numéro de commande doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (codePromo.length() < 4 || codePromo.length() > 6) {
            errorLabel.setText("Le code promo doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (typePaiement.length() < 4 || typePaiement.length() > 6) {
            errorLabel.setText("Le type de paiement doit avoir entre 4 et 6 caractères.");
            return false;
        }

        if (!isValidEmail(email)) {
            errorLabel.setText("L'email n'est pas valide.");
            return false;
        }

        if (dateCommande == null || dateCommande.isAfter(LocalDate.now())) {
            errorLabel.setText("Veuillez sélectionner une date de commande valide (passée ou présente).");
            return false;
        }

        return true;
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for validating email addresses
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
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
