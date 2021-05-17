package products;

import com.gembox.spreadsheet.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;

/**
 * Class for operations with products
 */
public class Products {
    private final String path = "src/main/java/data/invoice.csv";

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
     * @return ObservableList with all products
     */
    public ObservableList getListOfProducts(){
        ObservableList<String> products = FXCollections.observableArrayList();
        int row = 2;
        ExcelCell cell = worksheet.getCell(row, 0);

        while(cell.getValueType() != CellValueType.NULL){
            products.add(cell.getValue().toString());
            row++;
            cell = worksheet.getCell(row, 0);
        }

        return products;
    }

    /**
     * Adds count of baked product to .csv file
     * @param number number of product
     * @param count baked count
     */
    public void bake(int number, int count){
        int row = 2;
        ExcelCell cell = worksheet.getCell(row + number, 1);
        if(cell.getValueType() == CellValueType.NULL){
            cell.setValue(count);
        }
        else{
            cell.setValue(cell.getIntValue() + count);
        }

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Subtract count of sold product from baked in .csv file
     * @param number number of product
     * @param count sold count
     */
    public void sell(int number, int count) {
        int row = 2;
        ExcelCell baked = worksheet.getCell(row + number, 1);
        ExcelCell sold = worksheet.getCell(row + number, 2);
        if(baked.getValueType() != CellValueType.NULL){
            if(sold.getValueType() == CellValueType.NULL){
                sold.setValue(Math.min(baked.getIntValue(), count));
            }
            else{
                sold.setValue(Math.min(baked.getIntValue(), sold.getIntValue() + count));
            }
        }

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills TableView with information about count of baked products
     * @param table TableView on baker window
     */
    public void fillBakedTable(TableView table){
        String[][] sourceData = new String[100][2];

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            for (int column = 0; column < 2; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row-2][column] = cell.getValue().toString();
            }

            row++;
            product = worksheet.getCell(row, 0);
        }

        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row1 : sourceData)
            data.add(FXCollections.observableArrayList(row1));
        table.setItems(data);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Product");
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column1);

        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Baked today");
        column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column2);
    }

    /**
     * Fills TableView with information about products in stock and their price
     * @param table TableView on seller window
     * @param markup mark-up for products
     */
    public void fillSellTable(TableView table, double markup){
        String[][] sourceData = new String[100][3];
        double[] costs = getCosts();

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            int baked;

            sourceData[row-2][0] = product.getValue().toString();

            ExcelCell cell = worksheet.getCell(row, 1);
            baked = cell.getIntValue();

            cell = worksheet.getCell(row, 2);
            if (cell.getValueType() != CellValueType.NULL)
                sourceData[row-2][1] = String.valueOf(baked - cell.getIntValue());
            else
                sourceData[row-2][1] = String.valueOf(baked);

            sourceData[row-2][2] = String.valueOf(Math.ceil(costs[row-2]*(1+markup/100)*100)/100);

            row++;
            product = worksheet.getCell(row, 0);
        }

        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row1 : sourceData)
            data.add(FXCollections.observableArrayList(row1));
        table.setItems(data);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Product");
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column1);

        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("In stock");
        column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column2);

        TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Price, EUR");
        column3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
        column3.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column3);
    }

    /**
     * Fills TableView with information about baked, sold products and sum of money earned from each product
     * @param table TableView on admin window
     * @param markup mark-up for products
     */
    public void fillAdminTable(TableView table, double markup){
        String[][] sourceData = new String[100][4];
        double[] costs = getCosts();

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            for (int column = 0; column < 3; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL)
                    sourceData[row-2][column] = cell.getValue().toString();
                if(column == 2){
                    if (cell.getValueType() != CellValueType.NULL)
                        sourceData[row-2][3] = String.valueOf(Math.ceil(cell.getIntValue()*costs[row-2]*(1+markup/100)*100)/100);
                }
            }

            row++;
            product = worksheet.getCell(row, 0);
        }

        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row1 : sourceData)
            data.add(FXCollections.observableArrayList(row1));
        table.setItems(data);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Product");
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column1);

        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Baked today");
        column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column2);

        TableColumn<ObservableList<String>, String> column3 = new TableColumn<>("Sold today");
        column3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
        column3.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column3);

        TableColumn<ObservableList<String>, String> column4 = new TableColumn<>("Sum, EUR");
        column4.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
        column4.setCellFactory(TextFieldTableCell.forTableColumn());
        table.getColumns().add(column4);
    }

    /**
     * Nullifying baked and sold products
     */
    public void setNewDay() {
        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            for (int column = 1; column < 3; column++) {
                ExcelCell cell = worksheet.getCell(row, column);
                if (cell.getValueType() != CellValueType.NULL) {
                    worksheet.getCell(row, column + 2).setValue(cell.getIntValue());
                }
                cell.setValue(0);
            }

            row++;
            product = worksheet.getCell(row, 0);
        }

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return count of sold products
     */
    public int getSold(){
        int soldToday = 0;

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            ExcelCell cell = worksheet.getCell(row, 2);
            if (cell.getValueType() != CellValueType.NULL) {
                soldToday += cell.getIntValue();
            }
            row++;
            product = worksheet.getCell(row, 0);
        }

        return soldToday;
    }

    /**
     * @param salary1 daily baker's salary
     * @param salary2 daily seller's salary
     * @param taxes daily taxes
     * @param markup mark-up for products
     * @return net profit
     */
    public double getEarned(double salary1, double salary2, double taxes, double markup){
        double[] costs = getCosts();
        double earnedToday = 0;

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            ExcelCell cell = worksheet.getCell(row, 2);
            if (cell.getValueType() != CellValueType.NULL) {
                earnedToday = earnedToday + cell.getIntValue() * (Math.ceil(costs[row-2] * markup) / 100); //Rounding price to #.## format
            }
            row++;
            product = worksheet.getCell(row, 0);
        }

        earnedToday = earnedToday - salary1 - salary2 - taxes;

        return earnedToday;
    }

    /**
     * @return array with prices on products
     */
    private double[] getCosts(){
        double[] costs = new double[100];

        int row = 2;
        ExcelCell product = worksheet.getCell(row, 0);

        while(product.getValueType() != CellValueType.NULL){
            int column = 6;
            ExcelCell ingredient = worksheet.getCell(0, column);
            costs[row-2] = 0;

            while(ingredient.getValueType() != CellValueType.NULL){
                ExcelCell kg = worksheet.getCell(row, column);
                ExcelCell price = worksheet.getCell(1, column);

                if (kg.getValueType() != CellValueType.NULL)
                    costs[row-2] = costs[row - 2] + Double.parseDouble(kg.getValue().toString())*Double.parseDouble(price.getValue().toString());

                column++;
                ingredient = worksheet.getCell(0, column);
            }

            row++;
            product = worksheet.getCell(row, 0);
        }
        return costs;
    }

    /**
     * Fills TableView with information about ingredients and their prices
     * @param table TableView with ingredients on baker window
     */
    public void fillIngrTable(TableView table){
        String[][] sourceData = new String[100][2];

        int column = 6;
        ExcelCell ingr = worksheet.getCell(0, column);

        while(ingr.getValueType() != CellValueType.NULL){

            sourceData[column-6][0] = ingr.getValue().toString();

            sourceData[column-6][1]="0";

            column++;
            ingr = worksheet.getCell(0, column);
        }

        table.getColumns().clear();

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (String[] row1 : sourceData)
            data.add(FXCollections.observableArrayList(row1));
        table.setItems(data);

        TableColumn<ObservableList<String>, String> column1 = new TableColumn<>("Ingredients");
        column1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column1.setEditable(false);
        table.getColumns().add(column1);


        TableColumn<ObservableList<String>, String> column2 = new TableColumn<>("Mass, Kg/item");
        column2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setOnEditCommit(
                (TableColumn.CellEditEvent<ObservableList<String>, String> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).set(t.getTablePosition().getColumn(), t.getNewValue());
                });
        table.getColumns().add(column2);
    }

    /**
     * Adds new product with receipt from TableView to .csv file
     * @param name name of new product
     * @param table TableView with amount of ingredients for new product
     */
    public void addNewProduct(String name, TableView table) {
        int row1 = 2;
        ExcelCell cell = worksheet.getCell(row1, 0);
        while(cell.getValueType() != CellValueType.NULL){
            row1++;
            cell = worksheet.getCell(row1, 0);
        }
        cell.setValue(name);

        for (int row2 = 0; row2 < table.getItems().size(); row2++){
            ObservableList cells = (ObservableList) table.getItems().get(row2);
            if (cells.get(1) != null)
                worksheet.getCell(row1, row2+6).setValue(cells.get(1).toString());
        }

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new ingredient to .csv file
     * @param name name of new ingredient
     * @param price price for 1kg of ingredient
     */
    public void addNewIngr(String name,  double price) {
        int column = 6;
        ExcelCell cell = worksheet.getCell(0, column);

        while(cell.getValueType()!=CellValueType.NULL){
            column++;
            cell = worksheet.getCell(0, column);
        }
        cell.setValue(name);

        cell = worksheet.getCell(1, column);
        cell.setValue(price);

        try {
            workbook.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
