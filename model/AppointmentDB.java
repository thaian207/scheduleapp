package model;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnection;

/** Class make appointment database object */
public class AppointmentDB {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointment> atDateAppointments = FXCollections.observableArrayList();


    /** Return list of all Customers in DB
     * @return list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        allAppointments.clear();

        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, " +
                    "appointments.Description, appointments.Location , contacts.Contact_Name, " +
                    "appointments.Type , appointments.Start" +
                    ", appointments.End, appointments.Customer_ID," +
                    " appointments.User_ID, appointments.Contact_ID FROM appointments " +
                    "INNER JOIN contacts on appointments.Contact_ID= contacts.Contact_ID; ";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                allAppointments.add(new Appointment(Integer.parseInt(results.getString("Appointment_ID")),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact_Name"),
                        results.getString("Type"), dateConverter(results.getString("Start")),
                        timeConverter(results.getString("Start")),
                        timeConverter(results.getString("End")),
                        Integer.parseInt(results.getString("Customer_ID")),
                        Integer.parseInt(results.getString("User_ID")),
                        Integer.parseInt(results.getString("Contact_ID"))));
            }
            statement.close();

            return allAppointments;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    /** appointment at specific date
     * @param dateTime
     * @return appt list
     */
    public static ObservableList<Appointment> atDate(String dateTime) {
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Start, appointments.End FROM appointments " +
                    "WHERE cast(Start as date) = '" + dateTime + "'";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                atDateAppointments.add(new Appointment(results.getInt("Appointment_ID"), dateTimeConverter2(results.getString("Start")),
                        dateTimeConverter2(results.getString("End"))));
            }
            statement.close();

            return atDateAppointments;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    /** adjust time base on local time
     * @param dateTime
     * @return String time
     */
    public static String timeConverter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        ZoneId newZone = ZoneId.systemDefault();
        ZoneId oldZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        return newDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**  adjust date based on local date
     * @param dateTime
     * @return String date
     */
    public static String dateConverter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        ZoneId newZone = ZoneId.systemDefault();
        ZoneId oldZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        return newDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /** adjust time and date based on local time date
     *
     * @param dateTime
     * @return String date and time
     */
    public static String dateTimeConverter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' hh:mma");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        ZoneId newZone = ZoneId.systemDefault();
        ZoneId oldZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        return newDateTime.format(formatter2);
    }

    /** format date and time
     * @param dateTime
     * @return string date and time
     */
    public static String dateTimeConverter2(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        ZoneId newZone = ZoneId.systemDefault();
        ZoneId oldZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        return newDateTime.format(formatter);
    }

    /** adjust time zone when modifying appointments
     *
     * @return list of times
     */
    public static ObservableList<String> localTime() {
        ObservableList<String> times = FXCollections.observableArrayList(
                "2021-02-02 08:00:00","2021-02-02 09:00:00", "2021-02-02 10:00:00",
                "2021-02-02 11:00:00","2021-02-02 12:00:00","2021-02-02 13:00:00",
                "2021-02-02 14:00:00","2021-02-02 15:00:00","2021-02-02 16:00:00");
        int i = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<String> updatedTimes = FXCollections.observableArrayList();
        while (i < 9 ) {
            LocalDateTime localDateTime = LocalDateTime.parse(times.get(i), formatter);
            ZoneId newZone = ZoneId.systemDefault();
            ZoneId oldZone = ZoneId.of("America/New_York");
            LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
            i++;
            updatedTimes.add(newDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }return updatedTimes;

    }

    /** Return list of all Customers in DB
     * @return
     */
    public static ObservableList<Appointment> getWeeklyAppointments() {
        weeklyAppointments.clear();


        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, " +
                    "appointments.Description, appointments.Location , contacts.Contact_Name, " +
                    "appointments.Type , cast(appointments.Start as date),cast(appointments.Start as time)," +
                    " cast(appointments.End as time), appointments.Customer_ID," +
                    " appointments.User_ID, appointments.Contact_ID FROM appointments " +
                    "INNER JOIN contacts on appointments.Contact_ID= contacts.Contact_ID WHERE  WEEK(Start) =WEEK(current_date()) and YEAR(start) = YEAR(current_date()); ";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                weeklyAppointments.add(new Appointment(Integer.parseInt(results.getString("Appointment_ID")),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact_Name"),
                        results.getString("Type"), results.getString("cast(appointments.Start as date)"),
                        results.getString("cast(appointments.Start as time)"),
                        results.getString("cast(appointments.End as time)"),
                        Integer.parseInt(results.getString("Customer_ID")),
                        Integer.parseInt(results.getString("User_ID")),
                        Integer.parseInt(results.getString("Contact_ID"))));
            }
            statement.close();

            return weeklyAppointments;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    /** validate exist ID in the database
     * @param tableN
     * @param columnName
     * @param valueID
     * @return
     */
    public static boolean checkExist(String tableN,String columnName, int valueID) {
        Connection connect = DBConnection.getConnection();
        String query = ("SELECT COUNT(1) from " +tableN +" where " + columnName + " = " + valueID);
        try {
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                return rs.getInt(1) < 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }return true;
    }

    /** Return list of all Customers in DB by month
     *
     * @return
     */
    public static ObservableList<Appointment> getMonthlyAppointments() {
        monthlyAppointments.clear();

        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, " +
                    "appointments.Description, appointments.Location , contacts.Contact_Name, " +
                    "appointments.Type , cast(appointments.Start as date),cast(appointments.Start as time)," +
                    " cast(appointments.End as time), appointments.Customer_ID," +
                    " appointments.User_ID, appointments.Contact_ID FROM appointments " +
                    "INNER JOIN contacts on appointments.Contact_ID= contacts.Contact_ID WHERE  MONTH(Start) =MONTH(current_date()) and YEAR(start) = YEAR(current_date()); ";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                monthlyAppointments.add(new Appointment(Integer.parseInt(results.getString("Appointment_ID")),
                        results.getString("Title"), results.getString("Description"),
                        results.getString("Location"), results.getString("Contact_Name"),
                        results.getString("Type"), results.getString("cast(appointments.Start as date)"),
                        results.getString("cast(appointments.Start as time)"),
                        results.getString("cast(appointments.End as time)"),
                        Integer.parseInt(results.getString("Customer_ID")),
                        Integer.parseInt(results.getString("User_ID")),
                        Integer.parseInt(results.getString("Contact_ID"))));
            }
            statement.close();

            return monthlyAppointments;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }


    /** return list of all upcoming appts
     *
     * @return
     */
    public static ObservableList<Appointment> appointmentIn15Min() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId oldZone = ZoneId.systemDefault();
        ZoneId newZone = ZoneId.of("UTC");
        LocalDateTime newDateTime = localDateTime.atZone(oldZone).withZoneSameInstant(newZone).toLocalDateTime();
        LocalDateTime nextDateTime = newDateTime.plusMinutes(15);
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Start,  appointments.Start ,appointments.End FROM appointments WHERE start BETWEEN '" + newDateTime + "' AND '" + nextDateTime + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                upcomingAppointments.add(new Appointment(Integer.parseInt(results.getString("Appointment_ID")),
                        dateConverter(results.getString("Start")),
                        timeConverter(results.getString("Start")),
                        results.getString("End")));
                return upcomingAppointments;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

}