package tn.esprit.Controllers.Tournee;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.entites.Tournee;
import tn.esprit.services.TourneeServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TourneeFrontController implements Initializable {
    @FXML
    private TableColumn<Tournee, String> T_age;

    @FXML
    private TableColumn<Tournee, String> T_date;

    @FXML
    private TableColumn<Tournee, String> T_description;

    @FXML
    private TableColumn<Tournee, String> T_destination;

    @FXML
    private TableColumn<Tournee, String> T_duree;

    @FXML
    private TableColumn<Tournee, String> T_guide;

    @FXML
    private TableColumn<Tournee, Integer> T_id;

    @FXML
    private TableColumn<Tournee, String> T_monument;

    @FXML
    private TableColumn<Tournee, String> T_nom;

    @FXML
    private TableColumn<Tournee, Float> T_tarif;

    @FXML
    private TableColumn<Tournee, String> T_transport;

    @FXML
    private TableView<Tournee> TourneeTableView;
    private void addTourneeShow() {
        TourneeServices tourneeServices = new TourneeServices();
        List<Tournee> tourneeList = tourneeServices.getAllTournees();

        T_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        T_destination.setCellValueFactory(cellData -> {
            String destinationName = cellData.getValue().getDestination().getPays();
            return new SimpleStringProperty(destinationName);
        });
        T_guide.setCellValueFactory(cellData -> {
            String guideName = cellData.getValue().getGuide().getNom();
            return new SimpleStringProperty(guideName);
        });
        T_nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        T_date.setCellValueFactory(new PropertyValueFactory<>("Date_debut"));
        T_duree.setCellValueFactory(new PropertyValueFactory<>("Duree"));
        T_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        T_tarif.setCellValueFactory(new PropertyValueFactory<>("Tarif"));
        T_monument.setCellValueFactory(new PropertyValueFactory<>("Monuments"));
        T_age.setCellValueFactory(new PropertyValueFactory<>("Tranche_age"));
        T_transport.setCellValueFactory(new PropertyValueFactory<>("Moyen_transport"));


        TourneeTableView.getItems().addAll(tourneeList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTourneeShow();
    }


}
