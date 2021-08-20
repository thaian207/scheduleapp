package model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnection;

/**
 * class to handle customer DB
 */
public class CustomerDB {
    
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<String> states = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        allCustomers.clear();
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT customers.customer_ID, customers.customer_Name, customers.phone , customers.Address , customers.Postal_Code, first_level_divisions.Division, countries.Country " +
                    "FROM customers " +
                    "INNER JOIN " +
                    "first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN " +
                    "countries on first_level_divisions.COUNTRY_ID = countries.COUNTRY_ID";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                allCustomers.add(new Customer(Integer.parseInt(results.getString("customer_ID")),
                        results.getString("customer_Name"),
                        results.getString("phone"),results.getString("Address") ,
                        results.getString("Postal_Code") ,
                        results.getString("Division"),
                        results.getString("Country")));
            }statement.close();
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** list of all customer
     *
     * @param country
     * @return list of all customer
     */
    public static ObservableList<Customer> getAllCustomers(int country) {
        allCustomers.clear();
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT customers.customer_ID, customers.customer_Name, customers.phone , customers.Address , customers.Postal_Code, first_level_divisions.Division, countries.Country " +
                    "FROM customers " +
                    "INNER JOIN " +
                    "first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN " +
                    "countries on first_level_divisions.COUNTRY_ID = countries.COUNTRY_ID where countries.COUNTRY_ID = "+country;
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                allCustomers.add(new Customer(Integer.parseInt(results.getString("customer_ID")),
                        results.getString("customer_Name"),
                        results.getString("phone"),results.getString("Address") ,
                        results.getString("Postal_Code") ,
                        results.getString("Division"),
                        results.getString("Country")));
            }
            statement.close();
            return allCustomers;

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /** list of division ID
     *
     * @param countryID
     * @return list of division ID
     * @throws SQLException
     */
    public static ObservableList<String> getDivision(int countryID) throws SQLException {
        states.clear();
        Statement statement = DBConnection.getConnection().createStatement();
        String query = "SELECT division FROM first_level_divisions where COUNTRY_ID = " + countryID;
        ResultSet results = statement.executeQuery(query);
        while (results.next()) {
            states.add(results.getString("division"));
        }return states;
    }

    /** return list of division
     * @param state
     * @return return list of division
     */
    public String stateToDivision(String state){
        try {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT Division_ID from first_level_divisions WHERE division = \""  + state + "\"";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) {
                return results.getString("Division_ID");
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } return "-1";
    }

}

