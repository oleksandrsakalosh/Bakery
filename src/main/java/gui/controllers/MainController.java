package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import system.BakerySystem;

import java.io.IOException;

/**
 * Controller provides main functionality for all controllers
 */
public class MainController {

    /**
     * Bakery system
     */
    protected BakerySystem sys;

    /**
     * Window of the program
     */
    protected Stage primaryStage;

    /**
     * Pass window and system between controllers
     * @param primaryStage Window of the program
     * @param sys Bakery system {@link BakerySystem}
     */
    void giveSys(Stage primaryStage, BakerySystem sys){
        this.primaryStage = primaryStage;
        this.sys = sys;
    }

    /**
     * Switches scene to new
     * @param fileName Path to the file for switching
     * @param event Event that has been started
     */
    public void switchScene(String fileName, ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainController controller = loader.getController();
        controller.giveSys(this.primaryStage, this.sys);

        switch (fileName){
            case("../view/BakerScene.fxml"):{
                ((BakerController)controller).setLogin();
                ((BakerController)controller).setProducts();
                break;
            }
            case ("../view/SellerScene.fxml"):{
                ((SellerController)controller).setLogin();
                ((SellerController)controller).setProducts();
                break;
            }
            case ("../view/AdminScene.fxml"):{
                ((AdminController)controller).setLogin();
                ((AdminController)controller).setTables();
                break;
            }
            case ("../view/SignInScene.fxml"):{
                ((SignInController)controller).setChoiceBox();
            }
            default: break;
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
