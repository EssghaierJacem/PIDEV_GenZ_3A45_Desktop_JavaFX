package tn.esprit.Controllers.Destination;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.entites.Destination;
import tn.esprit.services.DestinationServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DestinationController_Back implements Initializable {
    private DestinationServices destinationService = new DestinationServices();

    @FXML
    private ListView<Destination> BackDestinationListView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Destination> destinations = destinationService.getAllDestinations();

        BackDestinationListView.getItems().addAll(destinations);
    }
}
