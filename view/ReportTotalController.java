package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Appointment;
import javafx.fxml.Initializable;
import model.Reports;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;


/** Controller class for ReportTotalController
 * generate the total amount appointments
 */
public class ReportTotalController implements Initializable {

    @FXML
    private Label annual;
    @FXML
    private Label board;
    @FXML
    private Label formal;
    @FXML
    private Label informal;
    @FXML
    private Label jan;
    @FXML
    private Label feb;
    @FXML
    private Label mar;
    @FXML
    private Label apr;
    @FXML
    private Label may;
    @FXML
    private Label jun;
    @FXML
    private Label jul;
    @FXML
    private Label aug;
    @FXML
    private Label sep;
    @FXML
    private Label oct;
    @FXML
    private Label nov;
    @FXML
    private Label dec;

    private static ObservableList<Appointment> totalMonth = Reports.totalMonth();
    private static ObservableList<Appointment> typeCount =  Reports.totalType();
/** fill text fields with total count */
    public void initialize(URL url, ResourceBundle rb) {
        formal.setText(String.valueOf(typeCount.get(2)));
        informal.setText(String.valueOf(typeCount.get(3)));
        annual.setText(String.valueOf(typeCount.get(0)));
        board.setText(String.valueOf(typeCount.get(1)));
        jan.setText(String.valueOf(Collections.frequency(totalMonth, "1")));
        feb.setText(String.valueOf(Collections.frequency(totalMonth, "2")));
        mar.setText(String.valueOf(Collections.frequency(totalMonth, "3")));
        apr.setText(String.valueOf(Collections.frequency(totalMonth, "4")));
        may.setText(String.valueOf(Collections.frequency(totalMonth, "5")));
        jun.setText(String.valueOf(Collections.frequency(totalMonth, "6")));
        jul.setText(String.valueOf(Collections.frequency(totalMonth, "7")));
        aug.setText(String.valueOf(Collections.frequency(totalMonth, "8")));
        sep.setText(String.valueOf(Collections.frequency(totalMonth, "9")));
        oct.setText(String.valueOf(Collections.frequency(totalMonth, "10")));
        nov.setText(String.valueOf(Collections.frequency(totalMonth, "11")));
        dec.setText(String.valueOf(Collections.frequency(totalMonth, "12")));
    }
}