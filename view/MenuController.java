package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** Menu Controller Class
 * @author An Nguyen
 */
public class MenuController implements Initializable {

    @FXML
    private TableView<Appointment> appTable;
    @FXML
    private TableColumn<Appointment, Integer> appID;
    @FXML
    private TableColumn<Appointment, String> appStart;
    @FXML
    private TableColumn<Appointment, String> appDate;
    @FXML
    private Label warningTxt;

    Stage stage = new Stage();
    Main main= new Main();

    /** initialize menu and check for upcoming appointments
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        UpcomingAppointments();
        if (appID.getCellData(0)== null ){

            warningTxt.setText("***No Appointments in the Next 15 Minutes***");
        }
    }

    /** Generate next 15min Appointments */
    public void UpcomingAppointments() {
        appID.setCellValueFactory(new PropertyValueFactory<>("appID"));
        appDate.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appTable.setItems(AppointmentDB.appointmentIn15Min());
    }


    /** @param event mouse event to logoff
     * @throws IOException
     */
    public void logoffButtonOnAction(ActionEvent event) throws IOException {
        main.changeScene("Login.fxml");
    }

    /** change scene to customer
     * @param event mouse event
     * @throws IOException
     */
    public void customerButtonOnAction(ActionEvent event) throws IOException {
        main.changeScene("Customer.fxml");
    }

    /** change scene to to view/add/update/delete appointments
     * @param event
     * @throws IOException
     */
    public void appointmentButtonOnAction(ActionEvent event) throws IOException {
        main.changeScene("Appointment.fxml");
    }

    /** report total
     * @param event mouse event
     * @throws IOException
     */
    public void onActionTotal(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportTotal.fxml"));
        stage.setScene(new Scene(root, 400,600));
        stage.show();
    }

    /** generate schedule based on contact
     * @param event
     * @throws IOException
     */
    public void onActionSchedule(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ContactSchedule.fxml"));
        stage.setScene(new Scene(root, 1000,600));
        stage.show();
    }

    /** generate country report from customer table
     * @param event
     * @throws IOException
     */
    public void onActionCountry(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CountryReport.fxml"));
        stage.setScene(new Scene(root, 1000,600));
        stage.show();
    }

}
