package tn.esprit.Controllers.User;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entites.Role;
import tn.esprit.entites.SessionManager;
import tn.esprit.entites.User;
import tn.esprit.services.UserServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private JFXButton DeleteUser;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton UpdateUser;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton Logout;

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
        loadRoles();

        UserTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });
        errorLabel.setVisible(false);
    }

    private void loadRoles() {
        updateRole.getItems().addAll("ADMIN", "USER");
    }

    private void fillUserTableView() {
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

    void populateFieldsWithData(User user) {
        updateCin.setText(String.valueOf(user.getCin()));
        updateNom.setText(user.getNom());
        updatePrenom.setText(user.getPrenom());
        updateEmail.setText(user.getEmail());
        updateUsername.setText(user.getUsername());
        updatePassword.setText(user.getPassword());
        updateImage.setText(user.getPhoto());
        updateRole.setValue(user.getRole().toString());
    }

    @FXML
    private void handleUpdateUserButtonAction(ActionEvent event) {
        User selectedUser = UserTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun utilisateur sélectionné");
            alert.setContentText("Veuillez sélectionner un utilisateur à mettre à jour.");
            alert.showAndWait();
            return;
        }

        // Validate inputs before updating
        if (!validateInputs()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous mettre à jour cet utilisateur?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedUser.setCin(Integer.parseInt(updateCin.getText()));
            selectedUser.setNom(updateNom.getText());
            selectedUser.setPrenom(updatePrenom.getText());
            selectedUser.setEmail(updateEmail.getText());
            selectedUser.setUsername(updateUsername.getText());
            selectedUser.setPassword(updatePassword.getText());
            selectedUser.setPhoto(updateImage.getText());
            selectedUser.setRole(Role.valueOf(updateRole.getValue()));

            userServices.updateUser(selectedUser);
            UserTableView.refresh();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Utilisateur mis à jour avec succès.");
            successAlert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        User selectedUser = UserTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun utilisateur sélectionné");
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet utilisateur?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userServices.removeUser(selectedUser.getId());
            UserTableView.getItems().remove(selectedUser);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Utilisateur supprimé avec succès.");
            successAlert.showAndWait();
        }
    }

    @FXML
    private void goToDestination(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Destination/ListDestination_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destinationButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("List of Destinations");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean validateInputs() {
        errorLabel.setVisible(false);
        errorLabel.setText("");
        StringBuilder errors = new StringBuilder();

        try {
            int cin = Integer.parseInt(updateCin.getText().trim());
            if (cin < 10000000 || cin > 99999999) {
                errors.append("'CIN' doit comporter 8 chiffres.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("'CIN' doit être un nombre valide de 8 chiffres.\n");
        }

        if (!validateName(updateNom.getText())) {
            errors.append("'Nom' doit comporter entre 3 et 20 lettres.\n");
        }

        if (!validateName(updatePrenom.getText())) {
            errors.append("'Prenom' doit comporter entre 3 et 20 lettres.\n");
        }

        if (!validateEmail(updateEmail.getText())) {
            errors.append("'Email' doit être au format 'y@x.sth'.\n");
        }

        if (!validatePassword(updatePassword.getText())) {
            errors.append("'Mot de passe' doit comporter entre 6 et 20 caractères.\n");
        }

        if (!validatePhotoURL(updateImage.getText())) {
            errors.append("'Photo' doit être une URL valide.\n");
        }

        if (updateRole.getValue() == null) {
            errors.append("'Role' doit être sélectionné.\n");
        }

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validateName(String name) {
        return name != null && name.length() >= 3 && name.length() <= 20;
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() >= 6 && password.length() <= 20;
    }

    private boolean validatePhotoURL(String photoURL) {
        String urlRegex = "^(http|https)://[a-zA-Z0-9./_%+-]+\\.[a-zA-Z]{2,}$";
        return photoURL.matches(urlRegex);
    }
}
