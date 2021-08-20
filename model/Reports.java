package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnection;

/** class for reports object */
public class Reports {

    private static ObservableList<String> monthCount = FXCollections.observableArrayList();
    private static ObservableList<String> typeCount = FXCollections.observableArrayList();
    private static ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();


    /** Return count all of types
     * @return
     */
    public static ObservableList totalType() {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            /*            String query = "select count(type) as 'count' from appointments where Type= '"+type+"'";*/
            ResultSet results = statement.executeQuery("select type, count(type) as 'count' from appointments group by type");
            while (results.next()) {
                typeCount.add(results.getString("count"));
            }statement.close();
            return typeCount;
        } catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** return month count
     *
     * @return return month count
     */
    public static ObservableList totalMonth() {

        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT  Month(Start) as 'month' from appointments";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                monthCount.add(results.getString("month"));
            }statement.close();
            return monthCount;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** return list all appt
     *
     * @param contact
     * @return all appointment
     */
    public static ObservableList<Appointment> getAllAppointments(int contact) {
        contactSchedule.clear();

        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments WHERE Contact_ID =" + contact;
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                contactSchedule.add(new Appointment(Integer.parseInt(results.getString("Appointment_ID")),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Type"), AppointmentDB.dateTimeConverter(results.getString("Start")),
                        AppointmentDB.dateTimeConverter(results.getString("End")), Integer.parseInt(results.getString("Customer_ID"))));
            }
            statement.close();
            return contactSchedule;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

}

