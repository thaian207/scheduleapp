package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.DBConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller Class for Login panel
 * @author An Nguyen
 */
public class LoginController implements Initializable{

    public LoginController(){
    }

    @FXML
    private Button login;
    @FXML
    private Label statusText;
    @FXML
    private Label langTxt;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button exit;
    @FXML
    private Label locID;
    private String status1;
    private String status2;
    private String status3;

    Main main= new Main();
    FileWriter fWriter;
    PrintWriter outputFile;

    /**
     *initialize login panel
     *fill in label with appropriate language
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        ZoneId id = ZoneId.systemDefault();
        rb = ResourceBundle.getBundle("lang/login", locale);
        userName.setPromptText(rb.getString("userName"));
        password.setPromptText(rb.getString("password"));
        login.setText(rb.getString("login"));
        exit.setText(rb.getString("exit"));
        locID.setText("Zone ID :" +id.toString());
        langTxt.setText(rb.getString("language"));
        status1 = rb.getString("status1");
        status2 = rb.getString("status2");
        status3 = rb.getString("status3");
        //logging user login activities
        try {
            fWriter = new FileWriter("src/login_activity.txt",true);
            outputFile = new PrintWriter(fWriter);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    /** check login and call for validation
     * @param event mouse on action event
     * */
    public void loginButtonOnAction(ActionEvent event) {
        if (userName.getText().isBlank()  || password.getText().isBlank()) {
            statusText.setText(status1);
        }else {
            checkLogin();
        }
    }

    /** Exit Application
     * @param event mouse on action exit app
     * */
    public void exitButtonOnAction(ActionEvent event){
        Stage stage= (Stage) exit.getScene().getWindow();
        stage.close();
    }

    /**
     * when called validate user and pass
     * log user login attempts
     */
    public void checkLogin(){
        Connection connect = DBConnection.getConnection();
        String loginTxt = ("SELECT count(1) FROM users WHERE User_Name = '"+ userName.getText()+ "' AND Password ='" + password.getText()+"'");
        try{
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(loginTxt);
            while(rs.next()){
                if(rs.getInt(1)== 1) {
                    statusText.setText(status2);
                    main.changeScene("Menu.fxml");
                    outputFile.println("Login Attempt --- Username: " + userName.getText() + " --- Date: " + LocalDate.now() +"--- Time: " +LocalTime.now()+ "---Status : Successful");
                    outputFile.close();
                }else {
                    statusText.setText(status3);
                    outputFile.println("Login Attempt ---Username: " + userName.getText() + " --- Date: " + LocalDate.now() +"---- Time: " +LocalTime.now()+ "---Status : Failed");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
