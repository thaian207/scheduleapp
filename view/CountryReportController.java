package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import model.*;
import model.Customer;


/** Controller class for country
 * Display customers base on country
 * @author An Nguyen
 */
public class CountryReportController implements Initializable{

    @FXML
    private TableView<Customer> countryTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, Integer> customerPostal;
    @FXML
    private TableColumn<Customer, Integer> customerDiv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
    }

    /**
     * report of USA
     * @param event
     */
    public void onActionFirst(ActionEvent event){
        UpdateTable();
        customerDiv.setText("State");
        countryTable.setItems(CustomerDB.getAllCustomers(231));
    }

    /**
     * report Canada
     * @param event
     */
    public void onActionSecond(ActionEvent event){
        UpdateTable();
        customerDiv.setText("Province");
        countryTable.setItems(CustomerDB.getAllCustomers(38));
    }

    /**
     * report for U.K.
     * @param event
     */
    public void onActionThird(ActionEvent event){
        UpdateTable();
        customerDiv.setText("City");
        countryTable.setItems(CustomerDB.getAllCustomers(230));
    }

    /**
     * fill table from DB
     */
    public void UpdateTable() {
        customerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostal.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerDiv.setCellValueFactory(new PropertyValueFactory<>("Division"));
    }

}
