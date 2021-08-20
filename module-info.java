module c.Schedule.App {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;


    opens view;
    opens model;
}