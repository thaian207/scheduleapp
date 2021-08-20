package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import model.Appointment;
import model.Reports;

/**
 * Contact Controller Class
 * generate schedule based on contact
 * @author  An Nguyen
 */
public class ContactScheduleController implements Initializable{

    @FXML
    private TableView<Appointment> appTable;

    @FXML
    private TableColumn<Appointment, Integer> customerID;
    @FXML
    private TableColumn<Appointment, Integer> appID;
    @FXML
    private TableColumn<Appointment, String> appTitle;
    @FXML
    private TableColumn<Appointment, String> appDescription;
    @FXML
    private TableColumn<Appointment, String> appType;
    @FXML
    private TableColumn<Appointment, String> appStart;
    @FXML
    private TableColumn<Appointment, String> appEnd;

    /**
     * initialize and fill column names
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();

    }

    /**
     * generate schedule for contact 1
     * @param event
     */
    public void onActionFirst(ActionEvent event){
        UpdateTable();
        appTable.setItems(Reports.getAllAppointments(1));
        appTable.getSortOrder().add(appStart);
    }

    /**
     * generate schedule for contact 2
     * @param event
     */
    public void onActionSecond(ActionEvent event){
        UpdateTable();
        appTable.setItems(Reports.getAllAppointments(2));
        appTable.getSortOrder().add(appStart);
    }

    /**
     * generate schedule for contact 3
     * @param event
     */
    public void onActionThird(ActionEvent event){
        UpdateTable();
        appTable.setItems(Reports.getAllAppointments(3));
        appTable.getSortOrder().add(appStart);
    }

    /** tableview column to be filled */
    public void UpdateTable() {
        appID.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }
}
