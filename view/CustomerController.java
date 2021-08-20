package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import model.Customer;
import model.CustomerDB;
import util.DBConnection;
import javax.swing.*;


/**
 * Controller for Customer
 * @author An Nguyen
 */
public class CustomerController implements Initializable{

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerPhone;
    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, String> customerPostal;
    @FXML
    private TableColumn<Customer, String> customerCountry;
    @FXML
    private TableColumn<Customer, String> customerState;
    @FXML
    private TextField IDTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField postalTxt;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private Label warningTxt;
    private int countryID;
    private boolean emptyField= true;


    Main main = new Main();
    CustomerDB customer= new CustomerDB();
    private int index = -1;
    Connection connection =null;
    PreparedStatement pst = null;
    private final ObservableList<String> country = FXCollections.observableArrayList("Canada","United States","United Kingdom");

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
    }

    /** Update table existing customers */
    public void UpdateTable() {

        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryComboBox.setItems(country);
        customerTable.setItems(CustomerDB.getAllCustomers());
    }

    /**
     *     set default buttons view
     */
    public void setDefaultButton(){
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        addButton.setVisible(true);
        modButton.setVisible(true);
        deleteButton.setVisible(true);
    }

    /** reset and clear fields */
    public void resetField(){
        IDTxt.setText("");
        nameTxt.setText("");
        phoneTxt.setText("");
        addressTxt.setText("");
        postalTxt.setText("");
        warningTxt.setText(" ");
        stateComboBox.getSelectionModel().clearSelection();
        stateComboBox.getItems().clear();
        countryComboBox.getSelectionModel().clearSelection();
    }

    /** get division list from country ID
     * @throws SQLException
     */
    public void getStates() throws SQLException {
        stateComboBox.getSelectionModel().clearSelection();
        stateComboBox.getItems().clear();
        String country = countryComboBox.getValue();
        if (country!=null) {
            switch (country) {
                case "Canada":
                    countryID = 38;
                    stateComboBox.setPromptText("Select Province");
                    break;
                case "United States":
                    stateComboBox.setPromptText("Select State");
                    countryID = 231;
                    break;
                case "United Kingdom":
                    stateComboBox.setPromptText("Select City");
                    countryID = 230;
                    break;
            }
        } stateComboBox.setItems(CustomerDB.getDivision(countryID));
    }

    public void menuOnAction(ActionEvent event) throws IOException {
        main.changeScene("Menu.fxml");
    }

    /** grab text from table to and fill fields
     * @param event On action event for modification
     * */
    public void modOnAction(ActionEvent event){
        resetField();
        stateComboBox.getSelectionModel().clearSelection();
        stateComboBox.getItems().clear();
        index = customerTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            warningTxt.setText("Please Select a Row to Modify");
            return;
        }
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        addButton.setVisible(false);
        modButton.setVisible(false);
        deleteButton.setVisible(false);
        IDTxt.setVisible(true);
        IDTxt.setDisable(true);
        IDTxt.setText(String.valueOf(customerID.getCellData(index)));
        nameTxt.setText(customerName.getCellData(index));
        phoneTxt.setText(customerPhone.getCellData(index));
        addressTxt.setText(customerAddress.getCellData(index));
        postalTxt.setText(customerPostal.getCellData(index));
        countryComboBox.setValue(customerCountry.getCellData(index));
        stateComboBox.setValue(customerState.getCellData(index));
    }

    /** update existing customer
     * @param event on action when clicking save  button
     * */
    public void setSaveButton (ActionEvent event){
        checkEmpty();
            if(!emptyField) {
                try {
                    connection = DBConnection.getConnection();
                    String value1 = IDTxt.getText();
                    String value2 = nameTxt.getText();
                    String value3 = phoneTxt.getText();
                    String value4 = addressTxt.getText();
                    String value5 = postalTxt.getText();
                    String value6 = customer.stateToDivision(stateComboBox.getValue());
                    String sql = "update customers set customer_ID= " + value1 + ",customer_Name= '" + value2 + "',phone= '" +
                            value3 + "',Address= '" + value4 + "',Postal_Code= '" + value5 + "', Division_ID= '" + value6 + "' where customer_ID=" + value1 + " ";
                    pst = connection.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Updated");
                    UpdateTable();
                    setDefaultButton();
                    resetField();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
    }

    public void cancelOnAction(ActionEvent event){
        setDefaultButton();
        resetField();
    }

    /** check for existing appointment
     * @return number of existing appointment
     * @throws SQLException
     */
    public int checkExistAppointment() throws SQLException {
        connection = DBConnection.getConnection();
        Statement statement = DBConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(Customer_ID) as count from appointments where Customer_ID = "+ customerID.getCellData(index));
        rs.next();
        return rs.getInt("count");
    }

    /** Delete an Existing Customer
     * @param event on action event to delete customer
     * @throws SQLException trigger when encounter database access error
     * */
    public void deleteOnAction(ActionEvent event) throws SQLException{
        resetField();
        warningTxt.setText("");
        index = customerTable.getSelectionModel().getSelectedIndex();
        checkExistAppointment();
        if (index <= -1){
            warningTxt.setText("Please Select a Row to Delete");
            return;
        }else if (checkExistAppointment()>0){
            warningTxt.setText("Existing Appointments under current customer");
            return;
        }

        connection = DBConnection.getConnection();
        String sql = "delete from customers where customer_ID = ?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Delete Customer Record");
            alert.setContentText("Please confirm the deletion of "+customerName.getCellData(index));
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK) {
                    try {
                    pst = connection.prepareStatement(sql);
                    pst.setString(1, String.valueOf(customerID.getCellData(index)));
                    pst.execute();
                    resetField();
                    UpdateTable();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            });
    }

    /** Add new Customer */
    public void addOnAction (){
        checkEmpty();
        if (!emptyField) {
            connection = DBConnection.getConnection();
            String sql = "insert into customers (customer_Name, phone , Address , Postal_Code, Division_ID)values(?,?,?,?,? )";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, nameTxt.getText());
                pst.setString(2, phoneTxt.getText());
                pst.setString(3, addressTxt.getText());
                pst.setString(4, postalTxt.getText());
                pst.setString(5, customer.stateToDivision(stateComboBox.getValue()));
                pst.execute();
                JOptionPane.showMessageDialog(null, "Customer Added Successful");
                UpdateTable();
                resetField();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /** validate all fields before add/mod */
    public void checkEmpty(){
        warningTxt.setText("");
        emptyField = true;
        if (nameTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Customer Name");
        }else if(phoneTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Customer Phone");
        }else if(addressTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Customer Address");
        }else if(postalTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Customer Postal Code");
        }else if(countryComboBox.getValue() == null){
            warningTxt.setText("Please Select the Country");
        }else if(stateComboBox.getValue()==  null){
            warningTxt.setText("Please Select the State/Province/City");
        }else {
            emptyField = false;
        }
    }

}
