package gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller provides functionality for seller
 */
public class SellerController extends MainController{
    @FXML
    private Label login;
    @FXML
    private ChoiceBox products;
    @FXML
    private TextField count;
    @FXML
    private TableView table;

    private Number chosenProduct;

    /**
     * Fills ChoiceBox and Table
     * Saves index of chosen product
     */
    public void setProducts(){
        products.setItems(sys.getGoods().getListOfProducts());

        products.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number number)
            {
                chosenProduct = number;
            }
        });

        sys.getGoods().fillSellTable(table, sys.getSettings().getMarkup());
    }

    /**
     * Sets login of current user in label
     */
    public void setLogin(){
        login.setText("User: " + sys.getAccSys().getUser().getLogin());
    }

    /**
     * Subtract count of sold products button
     */
    @FXML
    public void Sell(){
        if(chosenProduct != null && count != null){
            sys.getGoods().sell((Integer) chosenProduct, Integer.parseInt(count.getText()));
        }

        count.setText("");
        setProducts();
    }

    /**
     * Switching to Login scene button
     * @param event
     */
    @FXML
    public void LogOut(ActionEvent event) {
        switchScene("../view/Login.fxml", event);
    }

}
