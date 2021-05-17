package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import system.AlertSystem;

public class SignInController extends MainController{

    @FXML
    private ChoiceBox workerSpecification;
    @FXML
    private TextField user;
    @FXML
    private TextField password;

    /**
     * Sets items in ChoiceBox
     */
    public void setChoiceBox(){
        workerSpecification.getItems().addAll("Baker", "Seller");
    }

    /**
     * Registration button
     * @param event
     */
    @FXML
    public void signInButtonClicked(ActionEvent event) {
        if(workerSpecification.getSelectionModel().isEmpty()){
            AlertSystem alert = new AlertSystem("Alert", "You haven't chosen worker specification.");
            alert.showAlert();
        }
        else{
            if(user.getText().equals("") || password.getText().equals("")){
                AlertSystem alert = new AlertSystem("Alert", "You haven't filled in all fields.");
                alert.showAlert();
            }
            else{
                if(sys.getAccSys().checkPassword(password.getText()) && sys.getAccSys().checkUser(user.getText())){
                    sys.getAccSys().addUser((String) workerSpecification.getSelectionModel().getSelectedItem(), user.getText(), password.getText());
                    switchScene("../view/Login.fxml", event);
                }
            }
        }
    }

    /**
     * Switching to Register scene button
     * @param event
     */
    @FXML
    public void LogIn(ActionEvent event) {
        switchScene("../view/Login.fxml", event);
    }
}
