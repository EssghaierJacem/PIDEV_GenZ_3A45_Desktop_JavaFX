package tn.esprit.Controllers.Tournee;

import com.jfoenix.controls.JFXButton;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entites.Destination;
import tn.esprit.entites.Guide;
import tn.esprit.entites.Tournee;
import tn.esprit.services.DestinationServices;
import tn.esprit.services.GuideServices;
import tn.esprit.services.TourneeServices;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTourneeController implements Initializable {

    @FXML
    private JFXButton Add;

    @FXML
    private JFXButton Clear;

    @FXML
    private TextField addAge;

    @FXML
    private DatePicker addDate;

    @FXML
    private TextArea addDescription;

    @FXML
    private ComboBox<Destination> addDestination;

    @FXML
    private TextField addDuree;

    @FXML
    private ComboBox<Guide> addGuide;

    @FXML
    private TextField addMonuments;

    @FXML
    private TextField addNom;

    @FXML
    private TextField addTarif;

    @FXML
    private TextField addTransport;
    private static final String ACCOUNT_SID = "ACde90f2a6ff59a35aa69aa9c5554ce829";
    private static final String AUTH_TOKEN = "6fd2a62e9757cb2ca55fce8bc1088524";

    @FXML
    void handleAddButtonAction(ActionEvent event) {
        Tournee newTournee = new Tournee();
        newTournee.setNom(addNom.getText());
        newTournee.setMonuments(addMonuments.getText());
        newTournee.setDuree(addDuree.getText());
        newTournee.setDate_debut(java.sql.Date.valueOf(addDate.getValue()));
        newTournee.setMoyen_transport(addTransport.getText());
        newTournee.setTarif(Float.parseFloat(addTarif.getText()));
        newTournee.setDescription(addDescription.getText());
        newTournee.setTranche_age(addAge.getText());

        Guide selectedGuide = addGuide.getValue();
        Destination selectedDestination = addDestination.getValue();

        TourneeServices tourneeServices = new TourneeServices();
        tourneeServices.addTournee(newTournee, selectedDestination.getId(), selectedGuide.getId());

        String guidePhoneNumber = "+216" + selectedGuide.getNum_tel();
        String messageBody = " Bonjour Mr " + selectedGuide.getNom() +" Vous etes affecté a la tournée: " + newTournee.getNom();
        sendSMS(guidePhoneNumber, messageBody);
    }

    @FXML
    void handleClearButtonAction(ActionEvent event) {
        clearFields();

    }
    private void clearFields() {
        addNom.clear();
        addMonuments.clear();
        addTarif.clear();
        addTransport.clear();
        addDate.setValue(null);
        addDuree.clear();
        addAge.clear();
        addDescription.clear();
        addGuide.getSelectionModel().clearSelection();
        addDestination.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        loadDestinations();
        loadGuides();
    }

    private void loadDestinations() {
        DestinationServices destinationServices = new DestinationServices();
        addDestination.getItems().addAll(destinationServices.getAllDestinations());
    }

    private void loadGuides() {
        GuideServices guideServices = new GuideServices();
        addGuide.getItems().addAll(guideServices.getAllGuides());
    }

    public static void sendMessageToGuide(Tournee tournee, String messageBody) {
        if (tournee != null && tournee.getGuide() != null) {
            Guide guide = tournee.getGuide();
            String guidePhoneNumber = String.valueOf(guide.getNum_tel());

            sendSMS(guidePhoneNumber, messageBody);
        } else {
            System.out.println("No guide or tour found. Cannot send SMS.");
        }
    }
    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+19513197180";

        try {
            Message message = Message.creator(
                    new PhoneNumber(recipientPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS sent successfully. SID: " + message.getSid());
        } catch (com.twilio.exception.ApiException e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }

}
