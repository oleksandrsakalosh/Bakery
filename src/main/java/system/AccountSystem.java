package system;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import people.*;

import java.io.IOException;

/**
 * Class for account system that holds, adds and delete users, checks login and password
 */
public class AccountSystem {
    private Human worker;

    private final String path = "src/main/java/data/users.csv";

    private ExcelFile workbook;
    {
        try {
            workbook = ExcelFile.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final ExcelWorksheet worksheet = workbook.getWorksheet(0);

    /**
     * Checks if there is user with such login and password, creates new Baker, Seller or Admin class.
     * @param login Entered login
     * @param password Entered password
     * @return true if login and password right, otherwise throws {@link LogInException} exception
     * @throws LogInException
     */
    public boolean logUser(String login, String password) throws LogInException {
        if(login.equals("admin") && password.equals("0000")){
            worker = new Admin("admin");
            return true;
        }
        else {
            for (ExcelRow row : worksheet.getRows()) {
                if(row.getIndex() != 0 && row.getIndex() != 1){
                    // Bakers
                    ExcelCell cell0 = row.getCell(0);
                    ExcelCell cell1 = row.getCell(1);
                    if ((cell0.getValueType() != CellValueType.NULL) && (cell1.getValueType() != CellValueType.NULL)){
                        if((login.equals(cell0.getValue().toString())) && (password.equals(cell1.getValue().toString()))){
                            worker = new Baker(login);
                            return true;
                        }
                    }

                    // Sellers
                    ExcelCell cell2 = row.getCell(2);
                    ExcelCell cell3 = row.getCell(3);
                    if ((cell2.getValueType() != CellValueType.NULL) && (cell3.getValueType() != CellValueType.NULL)){
                        if((login.equals(cell2.getValue().toString())) && (password.equals(cell3.getValue().toString()))){
                            worker = new Seller(login);
                            return true;
                        }
                    }
                }
            }
        }
        throw new LogInException();
    }

    /**
     * Adds new user to .csv file
     * @param profession profession of new worker
     * @param login login for new user
     * @param password password for new user
     */
    public void addUser(String profession,String login, String password){
        int column = 0, row = 2;

        if(profession.equals("Seller"))
            column = 2;

        while(worksheet.getCell(row, column).getValueType() != CellValueType.NULL){
            row++;
        }

        worksheet.getCell(row, column).setValue(login);
        worksheet.getCell(row, column+1).setValue(password);

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if password have 4 symbols, at least 1 letter and 1 number
     * @param pass possible password
     * @return true if password fits, otherwise false
     */
    public boolean checkPassword(String pass){
        boolean haveLetter = false;
        boolean haveNumber = false;
        boolean isBig = false;
        if(pass.length() >= 4){
            isBig = true;
        }
        for(int i = 0; i < pass.length(); i++){
            char c = pass.charAt(i);
            if((pass.charAt(i) >= 65 && pass.charAt(i) <= 90) || (pass.charAt(i) >= 97 && pass.charAt(i) <= 122)) haveLetter = true;
            if(pass.charAt(i) >= 48 && pass.charAt(i) <= 57) haveNumber = true;
        }
        if(haveLetter && haveNumber && isBig){
            return true;
        }
        else{
            AlertSystem alert = new AlertSystem("Alert", "Password must have 4 symbols, at least 1 letter and 1 number.");
            alert.showAlert();
            return false;
        }
    }

    /**
     * @return array with worker's name and profession
     */
    private String[][] getWorkersArray(){
        String[][] sourceData = new String[100][2];

        int row = 2;
        int i = 0;

        while(worksheet.getCell(row, 0).getValueType() != CellValueType.NULL){

            sourceData[i][0] = worksheet.getCell(row, 0).getValue().toString();

            sourceData[i][1] = "Baker";

            row++;
            i++;
        }

        row = 2;
        while(worksheet.getCell(row, 2).getValueType() != CellValueType.NULL){

            sourceData[i][0] = worksheet.getCell(row, 2).getValue().toString();

            sourceData[i][1] = "Seller";

            row++;
            i++;
        }
        return sourceData;
    }

    /**
     * Checks if user with such login doesn't exist
     * @param login login for new user
     * @return true if login is free, false if it isn't
     */
    public boolean checkUser(String login){
        String[][] sourceData = getWorkersArray();

        for(int i = 0; i < sourceData.length; i++){
            if(sourceData[i][0] == null)
                break;
            if(sourceData[i][0].equals(login)){
                AlertSystem alert = new AlertSystem("Error", "User with such name is already exists");
                alert.showAlert();
                return false;
            }
        }
        return true;
    }

    /**
     * Fills TableView with information about all workers
     * @param table TableView on admin window
     */
    public void fillUsersTable(TableView table){
        String[][] sourceData = getWorkersArray();

        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row1 : sourceData)
            data.add(FXCollections.observableArrayList(row1));
        table.setItems(data);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column1);

        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Position");
        column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column2);
    }

    /**
     * Deletes user from .csv file
     * @param id number of user
     * @return true if user deleted successfully, false if not
     */
    public boolean deleteUser(int id){
        int i = 0, row = 2, column = 0;
        while(i != id && worksheet.getCell(row, column).getValueType() != CellValueType.NULL){
            row++;
            i++;
        }
        if (worksheet.getCell(row, column).getValueType() == CellValueType.NULL) {
            row = 2;
            column = 2;
            while (i != id) {
                row++;
                i++;
            }
        }
        if(worksheet.getCell(row, column).getValueType() != CellValueType.NULL){
            worksheet.getCell(row, column).remove(RemoveShiftDirection.UP);
            worksheet.getCell(row, column+1).remove(RemoveShiftDirection.UP);
            try {
                workbook.save(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else
            return false;
    }

    /**
     * @return current logged user's class
     */
    public Human getUser(){
        return worker;
    }

}
