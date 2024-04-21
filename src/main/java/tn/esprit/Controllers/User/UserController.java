package tn.esprit.Controllers.User;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Role;
import tn.esprit.entites.User;
import tn.esprit.services.UserServices;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private JFXButton DeleteUser;

    @FXML
    private JFXButton UpdateUser;

    @FXML
    private TableView<User> UserTableView;

    @FXML
    private TextField updateCin;

    @FXML
    private TextField updateEmail;

    @FXML
    private TextField updateImage;

    @FXML
    private TextField updateNom;

    @FXML
    private TextField updatePassword;

    @FXML
    private TextField updatePrenom;

    @FXML
    private ComboBox<String> updateRole;

    @FXML
    private TextField updateUsername;

    @FXML
    private TableColumn<User, Integer> user_cell_cin;

    @FXML
    private TableColumn<User, String> user_cell_email;

    @FXML
    private TableColumn<User, Integer> user_cell_id;

    @FXML
    private TableColumn<User, String> user_cell_nom;

    @FXML
    private TableColumn<User, String> user_cell_password;

    @FXML
    private TableColumn<User, String> user_cell_prenom;

    @FXML
    private TableColumn<User, Role> user_cell_role;

    @FXML
    private TableColumn<User, String> user_cell_username;
    private UserServices userServices;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userServices = new UserServices();
        fillUserTableView();
        loadClasses();

        UserTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
    }
    private void loadClasses() {
        updateRole.getItems().addAll("ADMIN", "USER");
    }
    private void fillUserTableView(){
        List<User> userList = userServices.getAllUsers();

        user_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_cell_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        user_cell_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        user_cell_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        user_cell_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        user_cell_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_cell_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_cell_role.setCellValueFactory(new PropertyValueFactory<>("role"));

        UserTableView.getItems().addAll(userList);
    }
    void  populateFieldsWithData(User user){
        updateCin.setText(String.valueOf(user.getCin()));
        updateNom.setText(user.getNom());
        updatePassword.setText(user.getPassword());
        updatePrenom.setText(user.getPrenom());
        updateImage.setText(user.getPhoto());
        updateRole.setValue(user.getRole().toString());
        updateUsername.setText(user.getUsername());
        updateEmail.setText(user.getEmail());
    }

    @FXML
    private void handleUpdateUserButtonAction(ActionEvent event) {
        User selectedUser = UserTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez-vous mettre à jour cet utilisateur?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                selectedUser.setCin(Integer.parseInt(updateCin.getText()));
                selectedUser.setNom(updateCin.getText());
                selectedUser.setPrenom(updateCin.getText());
                selectedUser.setEmail(updateCin.getText());
                selectedUser.setUsername(updateCin.getText());
                selectedUser.setPassword(updateCin.getText());
                selectedUser.setPhoto(updateCin.getText());
                Role selectedRole = Role.valueOf(updateRole.getValue());
                selectedUser.setRole(selectedRole);

                userServices.updateUser(selectedUser);
                UserTableView.refresh();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("utilisateur mis à jour avec succès");
                successAlert.showAndWait();
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Aucun utilisateur sélectionné");
            errorAlert.setContentText("Veuillez sélectionner utilisateur à mettre à jour.");
            errorAlert.showAndWait();
        }

    }
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        User selectedUser = UserTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Etes-vous sûr que vous voulez supprimer?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    userServices = new UserServices();
                    userServices.removeUser(selectedUser.getId());

                    UserTableView.getItems().remove(selectedUser);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun utilisateur sélectionné");
            alert.setContentText("Veuillez choisir un vol à supprimer.");
            alert.showAndWait();
        }
    }
}



