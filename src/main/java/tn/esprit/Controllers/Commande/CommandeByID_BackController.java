package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entites.Commande;
import tn.esprit.services.CommandeServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;

public class CommandeByID_BackController implements Initializable {

    @FXML
    private JFXButton Delete;

    @FXML
    private JFXButton Update;

    @FXML
    private Label commande_Id;

    @FXML
    private Label commande_Num_Commande;

    @FXML
    private Label commande_Code_promo;

    @FXML
    private Label commande_Type;

    @FXML
    private Label commande_email;

    @FXML
    private Label commande_DateC;

    private Commande currentCommande;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCommandeData(Commande commande) {
        this.currentCommande = commande;
        commande_Id.setText(String.valueOf(commande.getId()));
        commande_Num_Commande.setText(commande.getNum_commande());
        commande_Code_promo.setText(commande.getCode_promo());
        commande_Type.setText(commande.getType_paiement());
        commande_email.setText(commande.getEmail());
        commande_DateC.setText(dateFormat.format(commande.getDate_commande()));

    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande/UpdateCommande.fxml"));
        Parent root = loader.load();

        UpdateCommandeController updateCommandeController = loader.getController();
        updateCommandeController.populateFieldsWithData(currentCommande);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                CommandeServices destinationServices = new CommandeServices();
                destinationServices.removeCommande(currentCommande.getId());

                Stage stage = (Stage) Delete.getScene().getWindow();
                stage.close();
            }
        });
    }
}
