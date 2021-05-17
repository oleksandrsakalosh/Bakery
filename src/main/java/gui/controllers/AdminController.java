package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import system.AlertSystem;

/**
 * Controller provides functionality for admin
 */
public class AdminController extends MainController{

    @FXML
    private Label login;
    @FXML
    private TableView table;
    @FXML
    private TableView workersTable;
    @FXML
    private TextField bakerSalary;
    @FXML
    private TextField sellerSalary;
    @FXML
    private TextField taxes;
    @FXML
    private TextField markup;

    /**
     * Resets tableviews and text fields
     */
    public void setTables(){
        sys.getGoods().fillAdminTable(table, sys.getSettings().getMarkup());
        sys.getAccSys().fillUsersTable(workersTable);
        sys.deserializeLogic();
        bakerSalary.setText("");
        bakerSalary.setPromptText(String.valueOf(sys.getSettings().getBakerSalary()));
        sellerSalary.setText("");
        sellerSalary.setPromptText(String.valueOf(sys.getSettings().getSellerSalary()));
        taxes.setText("");
        taxes.setPromptText(String.valueOf(sys.getSettings().getTaxes()));
        markup.setText("");
        markup.setPromptText(String.valueOf(sys.getSettings().getMarkup()));
    }

    /**
     * Sets login of current user in label
     */
    public void setLogin(){
        login.setText("User: " + sys.getAccSys().getUser().getLogin());
    }

    /**
     * Switching to Login scene button
     * @param event
     */
    @FXML
    public void LogOut(ActionEvent event) {
        switchScene("../view/Login.fxml", event);
    }

    /**
     * Button:
     * Creating {@link AlertSystem} with new window including incomes information
     * Nullifying baked and sold products
     * @param event
     */
    @FXML
    private void NewDay(ActionEvent event) {
        sys.deserializeLogic();

        AlertSystem message = new AlertSystem("Message", "Today you sold " +
                sys.getGoods().getSold() + " product(s) and earned " +
                sys.getGoods().getEarned(sys.getSettings().getBakerSalary(), sys.getSettings().getSellerSalary(), sys.getSettings().getTaxes(), sys.getSettings().getMarkup())
                + "EUR of net profit.");
        message.showAlert();

        sys.getGoods().setNewDay();
        setTables();
    }

    /**
     * Deleting of worker button
     * @param event
     */
    @FXML
    public void deleteSelected(ActionEvent event) {
        int index = workersTable.getSelectionModel().getSelectedIndex();
        workersTable.getItems().removeAll(workersTable.getSelectionModel().getSelectedItem());
        if(index != -1){
            if(!sys.getAccSys().deleteUser(index)){
                AlertSystem alert = new AlertSystem("Error", "Failed to delete user");
                alert.showAlert();
            }
        }
        else{
            AlertSystem alert = new AlertSystem("Alert", "Choose user to delete");
            alert.showAlert();
        }
    }

    /**
     * Clicking button saves new settings using serialization
     * @param event
     */
    @FXML
    public void save(ActionEvent event) {
        double paBakerSalary = sys.getSettings().getBakerSalary();
        double paSellerSalary = sys.getSettings().getSellerSalary();
        double paTaxes = sys.getSettings().getTaxes();
        int paMarkup = sys.getSettings().getMarkup();

        if (!bakerSalary.getText().equals("")) {
            paBakerSalary = Double.parseDouble(bakerSalary.getText());
        }
        if (!sellerSalary.getText().equals("")) {
            paSellerSalary = Double.parseDouble(sellerSalary.getText());
        }
        if (!taxes.getText().equals("")) {
            paTaxes = Double.parseDouble(taxes.getText());
        }
        if (!markup.getText().equals("")) {
            paMarkup = Integer.parseInt(markup.getText());
        }

        sys.serializeLogic(paBakerSalary, paSellerSalary, paTaxes, paMarkup);
        setTables();
        AlertSystem alert = new AlertSystem("Message", "Changes successfully saved.");
        alert.showAlert();
    }
}
