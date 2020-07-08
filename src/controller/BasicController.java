package controller;

import database.ReadFromDb;
import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.CombinedProducts;
import objects.Product;
import objects.StoredProduct;

import java.sql.Date;

/**
 * Basic functions and gui elements that are equal at 3 controller classes
 */
public class BasicController {

    // the list of options available for the combo boxes
    protected final ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "Lebensmittel",
            "Verbrauchsgüter"
    );

    protected final ObservableList<String> placeOptionFood = FXCollections.observableArrayList(
            "Kühlschrank",
            "Vorratskammer"
    );

    protected final ObservableList<String> placeOptionGoods = FXCollections.observableArrayList(
            "Vorratsschrank",
            "Keller"
    );

    @FXML
    protected MenuBar menuBar;

    @FXML
    protected TextField barcode;

    @FXML
    protected TextField name;
    @FXML
    protected Label nameError;

    @FXML
    protected TextField brand;
    @FXML
    protected Label brandError;

    @FXML
    protected ComboBox<String> categoryCB;
    @FXML
    protected ComboBox<String> placeCB;

    // for the overview and outsource controller
    @FXML
    protected TableView<CombinedProducts> storedProductsTV;
    @FXML
    protected TableColumn<CombinedProducts, String> nameTC;
    @FXML
    protected TableColumn<CombinedProducts, String> brandTC;
    @FXML
    protected TableColumn<CombinedProducts, String> categoryTC;
    @FXML
    protected TableColumn<CombinedProducts, String> placeTC;
    @FXML
    protected TableColumn<CombinedProducts, String> openTC;
    @FXML
    protected TableColumn<CombinedProducts, Date> openSinceTC;
    @FXML
    protected TableColumn<CombinedProducts, Integer> leftCapacityTC;
    @FXML
    protected TableColumn<CombinedProducts, Integer> amountTC;
    @FXML
    protected TableColumn<CombinedProducts, Integer> minAmountTC;

    /**
     * Set's the available options, and the first value for the check boxes
     */
    protected void setCategoryOptions(){
        // set's the options and values for the check boxes
        categoryCB.setItems(categoryOptions);
        placeCB.setItems(placeOptionFood);

        categoryCB.setValue(categoryOptions.get(0));
        placeCB.setValue(placeOptionFood.get(0));
    }

    /**
     * Handles the change of the available options if the category is changed
     */
    protected void setCategoryAction(){
        // changes the place option if the category is changed
        if (categoryCB.getSelectionModel().getSelectedItem().equals("Lebensmittel")){
            placeCB.setItems(placeOptionFood);
            placeCB.setValue(placeOptionFood.get(0));
        }
        else{
            placeCB.setItems(placeOptionGoods);
            placeCB.setValue(placeOptionGoods.get(0));
        }
    }

    /**
     * Handles the basic error message if the a input Text Field is empty.
     * @param input The Text Field to check
     * @param errorLabel The Label witch should display the error
     */
    protected void checkInputText(TextField input, Label errorLabel, String massage){
        if (input.getText().isEmpty())
            handleErrorLabel(errorLabel, massage, true);
        else
            handleErrorLabel(errorLabel, "", false);
    }

    /**
     * Displays the passed error Massage or hides the error Massage.
     * True Error Massage displayed
     * @param errorLabel Error Label
     * @param text Displayed error Massage
     * @param displayError If the error Label should be displayed or hide
     */
    protected void handleErrorLabel(Label errorLabel, String text, boolean displayError){
        if (displayError){
            errorLabel.setVisible(true);
            errorLabel.setText(text);
        }
        else{
            errorLabel.setText("");
            errorLabel.setVisible(false);
        }
    }

    /**
     * Opens the overview Window in the existing Stage.
     * @return The controller of the overview Window
     */
    protected OverviewController openOverviewWindow(){
        ProcessFxmlFiles overview = new ProcessFxmlFiles("/fxml/overview.fxml", "Übersicht");
        Stage stage = (Stage) menuBar.getScene().getWindow();
        return (OverviewController) overview.openInExistingStage(stage);
    }

    /**
     * Clears the barcode, name, brand, categoryCB and placeCB
     */
    protected void clearCommonInputs(){
        barcode.setText("");
        name.setText("");
        brand.setText("");
        categoryCB.setValue(categoryOptions.get(0));
        placeCB.setValue(placeOptionFood.get(0));

    }

    /**
     * Set's the values for the Table Columns
     */
    protected void setupTableView(){
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandTC.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryTC.setCellValueFactory(new PropertyValueFactory<>("category"));
        placeTC.setCellValueFactory(new PropertyValueFactory<>("place"));
        openTC.setCellValueFactory(new PropertyValueFactory<>("openAsString"));
        openSinceTC.setCellValueFactory(new PropertyValueFactory<>("openSince"));
        leftCapacityTC.setCellValueFactory(new PropertyValueFactory<>("leftCapacity"));
        amountTC.setCellValueFactory(new PropertyValueFactory<>("amount"));
        minAmountTC.setCellValueFactory(new PropertyValueFactory<>("minAmount"));
    }

    /**
     * Creates the Combined Products, values of the stored Products and the products Db.
     * @param stmt Witch stored Products should be selected
     * @return The combined Products
     */
    protected ObservableList<CombinedProducts> getCombinedProducts(String stmt, String sortOption){
        String sqlAllProducts = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products";

        ObservableList<StoredProduct> storedProducts = ReadFromDb.getStoredProducts(stmt);
        ObservableList<Product> products = ReadFromDb.getProducts(sqlAllProducts);

        ObservableList<CombinedProducts> combinedProducts = FXCollections.observableArrayList();

        for (StoredProduct storedProduct : storedProducts){

            Product product = getProduct(products, storedProduct.getProductId());

            assert product != null;
            String barcode = product.getBarcode();
            String name = product.getName();
            String brand = product.getBrand();
            String category = product.getCategory();
            String place = storedProduct.getPlace();
            boolean open = storedProduct.isOpen();
            Date openSince = storedProduct.getOpenSince();
            int leftCapacity = storedProduct.getProductAmount();
            int amount = storedProduct.getAmount();
            float minAmount = product.getMinAmount();

            // if the stored product is from the right product category or no category is needed
            if (sortOption.equals("barcode")){
                if (barcode.isEmpty())
                    combinedProducts.add(new CombinedProducts(name, brand, category, place, open, openSince, leftCapacity, amount, minAmount));
            }
            else if (sortOption.equals(category)){
                combinedProducts.add(new CombinedProducts(name, brand, category, place, open, openSince, leftCapacity, amount, minAmount));
            }
        }

        return combinedProducts;
    }

    /**
     * Set's the default Values for the Table View.
     */
    protected void setTableViewValues(String sqlStmt, String sortOption){
        storedProductsTV.setItems(getCombinedProducts(sqlStmt, sortOption));
    }

    /**
     * Get's the product with the right product id.
     * @param products List of all the Products
     * @param productId Wished product id
     * @return Product with the right product id
     */
    private Product getProduct(ObservableList<Product> products, int productId){
        for (Product product : products){
            if (product.getId() == productId)
                return product;
        }
        return null;
    }
}
