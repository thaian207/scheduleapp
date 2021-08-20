package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * launch application
 * @author An Nguyen
 */
public class Main extends Application {

    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    /** set up initial stage and scene
     * @param primaryStage javafx main stage
     * */
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setScene(new Scene(root, 1000,650));
        stage.show();
    }
    /** method to change scene for application
     * @param fxml filename
     * @throws IOException for IO operations fails
     * */
    public void changeScene(String fxml) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage.setScene(new Scene(root, 1000,650));
        stage.show();
    }



}
