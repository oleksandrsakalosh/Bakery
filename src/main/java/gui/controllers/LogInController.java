package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import system.AlertSystem;
import system.LogInException;

/**
 * Controller provides functionality for Login scene.
 */
public class LogInController extends MainController{

    @FXML
    private TextField user;
    @FXML
    private TextField password;

    /**
     * Logging in system button
     * @param event
     */
    @FXML
    public void logInButtonClicked(ActionEvent event) {
        try {
            if(sys.getAccSys().logUser(user.getText(), password.getText())){
                switchScene(sys.getAccSys().getUser().getScene(), event);
            }
        } catch (LogInException e) {
            AlertSystem alert = new AlertSystem("Error", e.getText());
            alert.showAlert();
            return;
        }
    }

    /**
     * Switching to Register scene button
     * @param event
     */
    @FXML
    public void SignIn(ActionEvent event) {
        switchScene("../view/SignInScene.fxml", event);
    }
}
