package gui.controllers;

import com.gembox.spreadsheet.SpreadsheetInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import system.BakerySystem;

public class Start extends Application {

    /**
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");

        BakerySystem sys = new BakerySystem();
        sys.deserializeLogic();

        FXMLLoader loader = new FXMLLoader((getClass().getResource("../view/logIn.fxml")));
        Parent root = loader.load();
        LogInController logInController = loader.getController();
        logInController.giveSys(primaryStage, sys);

        primaryStage.setTitle("Bakery");
        primaryStage.getIcons().add(new Image("data/logo.jpg"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}