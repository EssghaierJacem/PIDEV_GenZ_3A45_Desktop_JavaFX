package tn.esprit.Controllers.Commande;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Commande;
import tn.esprit.entites.Destination;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.services.CommandeServices;
import tn.esprit.services.DestinationServices;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController_Front implements Initializable {

    @FXML
    private TableColumn<Commande, String> Code_promo;

    @FXML
    private TableView<Commande> CommandeTableView;

    @FXML
    private TableColumn<Commande, String> Date_commande;

    @FXML
    private TableColumn<Commande, String> Email;

    @FXML
    private JFXButton Logout;

    @FXML
    private TableColumn<Commande, String > Num_commande;

    @FXML
    private TableColumn<Commande, String> Type_paiement;

    @FXML
    private TableColumn<Commande, Integer> commande_id;

    @FXML
    private Label connectedUser_Username;

    @FXML
    void handleLogoutAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentSessionId = SessionManager.getCurrentSessionId();
        User connectedUser = SessionManager.getUserFromSession(currentSessionId);
        if (connectedUser != null) {
            connectedUser_Username.setText(connectedUser.getUsername());
        } else {
            connectedUser_Username.setText("Not logged in");
        }
        addCommandeShowListData();
    }
    private void addCommandeShowListData() {
        CommandeServices commandeServices = new CommandeServices();
        List<Commande> commandeList = commandeServices.getAllCommandes();

        commande_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Num_commande.setCellValueFactory(new PropertyValueFactory<>("Num_commande"));
        Date_commande.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Code_promo.setCellValueFactory(new PropertyValueFactory<>("code_promo"));
        Type_paiement.setCellValueFactory(new PropertyValueFactory<>("type_paiement"));

        CommandeTableView.getItems().addAll(commandeList);
    }
}
