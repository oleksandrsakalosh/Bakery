package gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import system.AlertSystem;

/**
 * Controller provides functionality for baker
 */
public class BakerController extends MainController{
    @FXML
    private Label login;
    @FXML
    private ChoiceBox products;
    @FXML
    private TextField count;
    @FXML
    private TableView table;
    @FXML
    private TextField nameProduct;
    @FXML
    public TableView ingrTable;
    @FXML
    private TextField nameIngr;
    @FXML
    private TextField priceIngr;

    private Number chosenProduct;

    /**
     * Fills ChoiceBox and Tables
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

        sys.getGoods().fillBakedTable(table);
        sys.getGoods().fillIngrTable(ingrTable);
    }

    /**
     * Sets login of current user in label
     */
    public void setLogin(){
        login.setText("User: " + sys.getAccSys().getUser().getLogin());
    }

    /**
     * Adding count of baked products button
     */
    @FXML
    public void addBaked() {
        if(chosenProduct != null){
            if(count.getText().equals("")){
                AlertSystem alert = new AlertSystem("Error", "You haven't entered count");
                alert.showAlert();
            }
            else {
                sys.getGoods().bake((Integer) chosenProduct, Integer.parseInt(count.getText()));

                count.setText("");
                setProducts();
            }
        }
        else {
            AlertSystem alert = new AlertSystem("Error", "You haven't chosen product");
            alert.showAlert();
        }
    }

    /**
     * Adding new product button
     */
    @FXML
    public void addProduct() {
        sys.getGoods().addNewProduct(nameProduct.getText(), ingrTable);

        nameProduct.setText("");
        setProducts();
    }

    /**
     * Adding new ingredient button
     */
    @FXML
    public void addIngr()  {
        sys.getGoods().addNewIngr(nameIngr.getText(), Double.parseDouble(priceIngr.getText()));
        sys.getGoods().fillIngrTable(ingrTable);

        nameIngr.setText("");
        priceIngr.setText("");
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
