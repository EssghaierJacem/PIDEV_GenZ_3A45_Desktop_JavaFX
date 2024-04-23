package tn.esprit.Controllers.Guide;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entites.Guide;
import tn.esprit.services.GuideServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGuideController implements Initializable {

    @FXML
    private JFXButton AddGuide;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addExperience;

    @FXML
    private TextField addLangues;

    @FXML
    private TextField addNationalite;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addNumTel;

    @FXML
    private TextField addPrenom;

    @FXML
    private TextField addTarif;

    @FXML
    void handleAddGuideButtonAction(ActionEvent event) throws IOException {
        Guide newGuide = new Guide();
        newGuide.setNom(addNom.getText());
        newGuide.setPrenom(addPrenom.getText());
        newGuide.setNationalite(addNationalite.getText());
        newGuide.setLangues_parlees(addLangues.getText());
        newGuide.setExperiences(addExperience.getText());
        newGuide.setNum_tel(Integer.parseInt(addNumTel.getText()));
        newGuide.setTarif_horaire(Integer.parseInt(addTarif.getText()));


        GuideServices guideServices = new GuideServices();
        guideServices.addGuide(newGuide);

        clearFields();
        Stage currentStage = (Stage) addNom.getScene().getWindow();
        currentStage.close();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Guide/Guide_Back.fxml"));
        Parent root = loader.load();
        Guide_BackController Guide_BackController = loader.getController();


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();



    }
    private void clearFields() {
        addNom.clear();
        addPrenom.clear();
        addNationalite.clear();
        addLangues.clear();
        addExperience.clear();
        addNumTel.clear();
        addTarif.clear();

    }
    @FXML
    void handleClear(ActionEvent event) {
        clearFields();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
