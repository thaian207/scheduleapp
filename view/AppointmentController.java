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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import model.Appointment;
import model.AppointmentDB;
import util.DBConnection;
import javax.swing.*;


/**
 * FXML Controller class
 *
 * @author An Nguyen
 */
public class AppointmentController implements Initializable{

    @FXML
    private TableView<Appointment> appTable;
    @FXML
    private TableColumn<Appointment, Integer> customerID;
    @FXML
    private TableColumn<Appointment, Integer> userID;
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
    private TableColumn<Appointment, String> appDate;
    @FXML
    private TableColumn<Appointment, String> appEnd;
    @FXML
    private TableColumn<Appointment, String> appContact;
    @FXML
    private TableColumn<Appointment, String> appLocation;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private ComboBox<String> startComboBox;
    @FXML
    private ComboBox<String> endComboBox;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField titleTxt;
    @FXML
    private TextField IDTxt;
    @FXML
    private TextField descTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private TextField customerIDTxt;
    @FXML
    private TextField userIDTxt;
    @FXML
    private Button addButton;
    @FXML
    private Button modButton;
    @FXML
    private Button menuButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label warningTxt;
    private boolean emptyField= true;

    Main main = new Main();
    int index = -1;
    Connection conn =null;
    PreparedStatement pst = null;



    /** Lambda expression used to format and adjust according to local time Zone */
    TimeInterface formatDateTime =(time) ->{
        String date = String.valueOf(datePicker.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date + " "+ time, formatter);
        ZoneId oldZone = ZoneId.systemDefault();
        ZoneId newZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        return newDateTime.format(formatter);
    };


    /**    Lambda expression used to prevent overlapping appointments    */
    checkExistInterface checkExistApp = (String date, String startT, String endT) ->{
        Timestamp start = Timestamp.valueOf(startT);
        Timestamp end = Timestamp.valueOf(endT);
        ObservableList<Appointment> atDateApp = AppointmentDB.atDate(date);
        assert atDateApp != null;
        for (Appointment appointment : atDateApp) {
            Timestamp eStart = Timestamp.valueOf(appointment.getAppStart());
            Timestamp eEnd = Timestamp.valueOf(appointment.getAppEnd());
            System.out.println(IDTxt.getText());
            System.out.println(appointment.getAppID());
            if (start.equals(eStart) && end.equals(eEnd)&&IDTxt.getText().equals(String.valueOf(appointment.getAppID()))){
                return false;
            }else if(start.equals(eStart) && !IDTxt.getText().equals(String.valueOf(appointment.getAppID()))) {
                return true;
            } else if (start.before(eStart) && end.after(eStart) && !IDTxt.getText().equals(String.valueOf(appointment.getAppID()))) {
                return true;
            } else if (start.before(eEnd) && end.after(eStart)&& !IDTxt.getText().equals(String.valueOf(appointment.getAppID()))) {
                return true;
            }
        }
        return false;
    };


    /**lists with items for various radio box */
    private final ObservableList<String> contacts = FXCollections.observableArrayList( "Frosty Snowman", "Yuna Spira", "Bruce Wayne");
    private final ObservableList<String> types = FXCollections.observableArrayList("Formal", "Informal", "Board", "Annual");


    /**
     * set default buttons
     */
    public void setDefaultButton(){
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        addButton.setVisible(true);
        modButton.setVisible(true);
        deleteButton.setVisible(true);
        IDTxt.setVisible(false);
        menuButton.setVisible(true);
    }

    /**
     * reset all fields
     */
    public void resetField(){
        titleTxt.setText("");
        descTxt.setText("");
        locationTxt.setText("");
        typeComboBox.setValue("");
        customerIDTxt.setText("");
        datePicker.setValue(null);
        startComboBox.setValue(null);
        endComboBox.setValue(null);
        contactComboBox.setValue(null);
        userIDTxt.setText("");
        IDTxt.setText("");
    }

    /**
     * take user back to main menu
     * @param event
     * @throws IOException
     */
    public void menuOnAction(ActionEvent event) throws IOException {
        main.changeScene("Menu.fxml");
    }

    /**
     * remove all filled fields
     * @param event
     */
    public void cancelOnAction(ActionEvent event) {
        setDefaultButton();
        resetField();
    }

    /**
     * fill tableview for all existing appointments
     */
    public void onActionAll(){
        UpdateTable();
        appTable.setItems(AppointmentDB.getAllAppointments());
    }

    /**
     * fill tableview for all weekly appointments
     * @param event
     */
    public void onActionWeekly(ActionEvent event){
        UpdateTable();
        appTable.setItems(AppointmentDB.getWeeklyAppointments());
    }


    /**
     * fill tableview for all monthly appointments
     * @param event
     */
    public void onActionMonthly(ActionEvent event){
        UpdateTable();
        appTable.setItems(AppointmentDB.getMonthlyAppointments());
    }

    /** Initialize and fill table with existing appointments */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        appTable.setItems(AppointmentDB.getAllAppointments());
        typeComboBox.setItems(types);
        contactComboBox.setItems(contacts);
        startComboBox.setItems(AppointmentDB.localTime());
        endComboBox.setItems(AppointmentDB.localTime());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(
                        empty ||
                                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                                date.isBefore(LocalDate.now()));
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: red;");
                }
            }
        });
    }


    /**
     * fill tableview with existing appointments
     */
    public void UpdateTable() {
        appID.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescription.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appLocation.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appContact.setCellValueFactory(new PropertyValueFactory<>("appContact"));
        appDate.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }



    /**
     * fill all fields when updating an existing appointment
     * @param event
     */
    public void modOnAction(ActionEvent event) {
        resetField();
        index = appTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            warningTxt.setText("Please Select a Row to Modify");
            return;
        }
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        addButton.setVisible(false);
        IDTxt.setVisible(true);
        addButton.setVisible(false);
        deleteButton.setVisible(false);
        modButton.setVisible(false);
        menuButton.setVisible(false);

        index = appTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        IDTxt.setText(String.valueOf(appID.getCellData(index)));
        titleTxt.setText(appTitle.getCellData(index));
        descTxt.setText(appDescription.getCellData(index));
        locationTxt.setText(appLocation.getCellData(index));
        typeComboBox.setValue(String.valueOf(appType.getCellData(index)));
        customerIDTxt.setText(customerID.getCellData(index).toString());
        startComboBox.setValue(String.valueOf(appStart.getCellData(index)));
        datePicker.setValue(LocalDate.parse(appDate.getCellData(index)));
        endComboBox.setValue(String.valueOf(appEnd.getCellData(index)));
        contactComboBox.setValue(String.valueOf(appContact.getCellData(index)));
        userIDTxt.setText(String.valueOf(userID.getCellData(index)));

    }


    /** Update an Existing Appointment
     * @param  event mouse on action when clicking Save button
     * */
    public void setSaveButton (ActionEvent event){
        checkEmpty();
        if(!emptyField) {
            try {
                conn = DBConnection.getConnection();
                String value1 = IDTxt.getText();
                String value2 = titleTxt.getText();
                String value3 = descTxt.getText();
                String value4 = locationTxt.getText();
                String value5 = typeComboBox.getValue();
                String value6 = formatDateTime.timeConvert(startComboBox.getValue());
                String value7 = formatDateTime.timeConvert(endComboBox.getValue());
                String value8 = customerIDTxt.getText();
                String value9 = userIDTxt.getText();
                int value10 = contactComboBox.getSelectionModel().getSelectedIndex() + 1;
                String sql = "update appointments set Appointment_ID= " + value1 + ",Title= '" + value2 + "',Description= '" +
                        value3 + "',Location= '" + value4 + "',Type= '" + value5 + "', Start= '" + value6 + "',End= '" + value7 + "', Customer_ID= " + value8 + ",User_ID= " + value9 + ",Contact_ID= " + value10 + " where Appointment_ID=" + value1 + " ";
                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Updated");
                UpdateTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            onActionAll();
            setDefaultButton();
            resetField();
        }
    }

    /**
     * delete an appointment from DB
     * @param event
     */
    public void deleteOnAction(ActionEvent event) {
        resetField();
        warningTxt.setText("");
        index = appTable.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            warningTxt.setText("Please Select a Row to Delete");
            return;
        }


        conn = DBConnection.getConnection();
        String sql = "delete from appointments where Appointment_ID = ?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Deletion of "+appTitle.getCellData(index)+" with Appt ID: " + appID.getCellData(index) + "?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                try {
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, String.valueOf(appID.getCellData(index)));
                    pst.execute();
                    resetField();
                    UpdateTable();
                    warningTxt.setText("Appointment ID: "+appID.getCellData(index)+ " with Type: "+appType.getCellData(index)+ "\n Deleted Successfully!");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });
        onActionAll();
        resetField();
        setDefaultButton();
    }


    /**
     * combining time and date from text fields
     * @param time
     * @return
     */
    public String dateFormat2(String time){
        String date = String.valueOf(datePicker.getValue());
        return date + " "+ time;
    }

    /**
     * add new appointment to DB
     */
    public void addOnAction (){
        checkEmpty();
        if(!emptyField) {
            conn = DBConnection.getConnection();
            String sql = "Insert into appointments(Title, Description, Location , Type , Start, End, Customer_ID, User_ID, Contact_ID) values(?,?,?,?,?,?,?,?,?)";

            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, titleTxt.getText());
                pst.setString(2, descTxt.getText());
                pst.setString(3, locationTxt.getText());
                pst.setString(4, typeComboBox.getValue());
                pst.setString(5, formatDateTime.timeConvert(startComboBox.getValue()));
                pst.setString(6, formatDateTime.timeConvert(endComboBox.getValue()));
                pst.setString(7, customerIDTxt.getText());
                pst.setString(8, userIDTxt.getText());
                pst.setString(9, String.valueOf(contactComboBox.getSelectionModel().getSelectedIndex() + 1));
                pst.execute();

                JOptionPane.showMessageDialog(null, "Appointment Added Successful");
                UpdateTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            onActionAll();
            resetField();
            setDefaultButton();
        }
    }

    /**
     * validate all field entered
     */
    public void checkEmpty(){
        warningTxt.setText("");
        emptyField = true;
        if (titleTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Title");
        }else if(descTxt.getText().isBlank()){
            warningTxt.setText("Please Enter Description");
        }else if(locationTxt.getText().isBlank()){
            warningTxt.setText("Please Enter A Location");
        }else if(typeComboBox.getValue().isBlank()){
            warningTxt.setText("Please Enter A Type");
        }else if(datePicker.getValue() == null){
            warningTxt.setText("Please Select The Date");
        }else if(startComboBox.getValue()==  null){
            warningTxt.setText("Please Select The Start Time");
        }else if(endComboBox.getValue()==  null){
            warningTxt.setText("Please Select The End Time");
        }else if(contactComboBox.getValue()==  null) {
            warningTxt.setText("Please Select the End Time");
        }else if(startComboBox.getSelectionModel().getSelectedIndex() >= endComboBox.getSelectionModel().getSelectedIndex()){
            warningTxt.setText("End Time Must Be After Start Time");
        }else if(customerIDTxt.getText().isBlank()){
            warningTxt.setText("Please Enter A Valid Customer ID");
        }else if(userIDTxt.getText().isBlank()){
            warningTxt.setText("Please Enter A Valid User ID");

        }else if(AppointmentDB.checkExist("customers","Customer_ID", Integer.parseInt(customerIDTxt.getText()))){
            warningTxt.setText("**Customer ID does NOT Exist***");
        }
        else if(AppointmentDB.checkExist("users","User_ID", Integer.parseInt(userIDTxt.getText()))){
            warningTxt.setText("**User ID does NOT Exist***");
        }
        //lamb expression used to determine overlapping
        else if(checkExistApp.checkApp(String.valueOf(datePicker.getValue()),dateFormat2(startComboBox.getValue()), dateFormat2(endComboBox.getValue()))){
            warningTxt.setText("**Overlapping Appt is Not Allowed**");
        }

        else {
            emptyField = false;
        }
    }

}
