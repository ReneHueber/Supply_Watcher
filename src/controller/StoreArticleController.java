package controller;

import database.ReadFromDb;
import database.WriteToDb;
import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import objects.Product;
import objects.StoredProduct;

public class StoreArticleController extends BasicController {

    private final ObservableList<String> unitOptions = FXCollections.observableArrayList(
            "Gramm",
            "ml",
            "Stück"
    );
    @FXML
    private ComboBox<String> unitCB;
    @FXML
    private TextField capacity;
    @FXML
    private Label capacityError;
    @FXML
    private TextField minimum;
    @FXML
    private Label minimumError;

    @FXML
    private Button storeBtn;

    @FXML
    private TableView<Product> productsTV;
    @FXML
    private TableColumn<Product, String> barcodeTC;
    @FXML
    private TableColumn<Product, String> nameTC;
    @FXML
    private TableColumn<Product, String> brandTC;
    @FXML
    private TableColumn<Product, String> categoryTC;
    @FXML
    private TableColumn<Product, String> placeTC;
    @FXML
    private TableColumn<Product, String> unitTC;
    @FXML
    private TableColumn<Product, Integer> capacityTC;


    public void initialize(){
        // set's the category options
        setCategoryOptions();

        // setup of the table view and set's the default values
        setupTableView();

        // handles the changes of the combo boxes
        categoryCB.setOnAction(event -> {
            setCategoryAction();
        });

        unitCB.setItems(unitOptions);
        unitCB.setValue(unitOptions.get(0));

        storeBtn.setOnAction(event -> {
            // check's if the inputs are okay
            checkInputText(name, nameError, "Name eingeben!");
            checkInputText(brand, brandError, "Marke eingeben!");
            checkInputNumber(capacity, capacityError);

            // if all inputs are okay
            if (!nameError.isVisible() && !brandError.isVisible() && !capacityError.isVisible() && !minimumError.isVisible()){
                // check if the product is already existing
                String sqlStmt = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE name = '" + name.getText() +
                        "' AND brand = '" + brand.getText() + "'";
                ObservableList<Product> products = ReadFromDb.getProducts(sqlStmt);
                if (products.size() == 0){
                    String stmt = "INSERT INTO products(barcode, name, brand, category, place, unit, capacity, minAmount) VALUES (?,?,?,?,?,?,?,?)";
                    String barcodeVale = barcode.getText();
                    String nameValue = name.getText();
                    String brandValue = brand.getText();
                    String categoryValue = categoryCB.getSelectionModel().getSelectedItem();
                    String placeValue = placeCB.getSelectionModel().getSelectedItem();
                    String unitVale = unitCB.getSelectionModel().getSelectedItem();
                    String capacityValue = capacity.getText();
                    String minValue = minimum.getText();

                    if (minValue.isEmpty())
                        minValue = "0.0";

                    // writes the Product to the product database
                    WriteToDb.executeWriteStmt(stmt, barcodeVale, nameValue, brandValue, categoryValue, placeValue, unitVale, capacityValue, minValue);

                    // reads the product from the database, to get the id, is needed to set the product id
                    sqlStmt = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE name = '" + nameValue +
                            "' AND brand = '" + brandValue + "'";
                    Product selectedProduct = ReadFromDb.getProducts(sqlStmt).get(0);
                    // stores the article in the storedProducts Db
                    storeProduct(selectedProduct);
                    openScanResultWindow(true, "Product gespeichert!", nameValue + " eingelagert");
                }
                else {
                    storeProduct(products.get(0));
                    openScanResultWindow(true, "Product OKAY!", products.get(0).getName() + " eingelagert");
                }
                clearAllInputs();
            }
        });

        brand.setOnKeyReleased(event -> checkInputText(brand, brandError, "Marke eingeben!"));

        barcode.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                String stmt = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE barcode = '" + barcode.getText() + "'";
                ObservableList<Product> products = ReadFromDb.getProducts(stmt);

                if (products.size() == 0){
                    name.requestFocus();
                    openScanResultWindow(false, "Scan FALSCH!", "Produkt nicht in Datenbank");
                }
                else if (products.size() == 1){
                    setProductValues(products.get(0));
                    openScanResultWindow(true, "Scan OKAY!", products.get(0).getName() + " eingelagert");

                    // stores the Product into to stored products Db
                    storeProduct(products.get(0));

                    clearAllInputs();
                }
                else {
                    openSelectProductWindow(products);
                }

            }
        });

        name.setOnKeyReleased(event -> {
            // text is not empty
            if (!name.getText().isEmpty()) {
                handleErrorLabel(nameError, "", false);
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String stmt = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE name LIKE '%" + name.getText() + "%'";
                    ObservableList<Product> products = ReadFromDb.getProducts(stmt);

                    if (products.size() == 0) {
                        brand.requestFocus();
                        openScanResultWindow(false, "Scan FALSCH!", "Produkt nicht in Datenbank");
                    } else if (products.size() == 1) {
                        setProductValues(products.get(0));
                        openScanResultWindow(true, "Scan OKAY!", products.get(0).getName() + " eingelagert");

                        // stores the Product into to stored products Db
                        storeProduct(products.get(0));

                        clearAllInputs();
                    } else {
                        openSelectProductWindow(products);
                    }
                }
            }
            else {
                handleErrorLabel(nameError, "Name eingeben!", true);
            }
        });

        // checks if the inputs are correct
        capacity.setOnKeyReleased(event -> {
            if (capacity.getText().isEmpty()){
                handleErrorLabel(capacityError, "Menge angeben!", true);
            }
            else {
               checkInputNumber(capacity, capacityError);
            }
        });

        minimum.setOnKeyReleased(event -> {
            if (!minimum.getText().isEmpty()){
                checkInputNumber(minimum, minimumError);
            }
            else{
                handleErrorLabel(minimumError, "", false);
            }

        });

        clearBtn.setOnAction(event -> {
            clearAllInputs();
            // reset's all the errors because all the inputs are empty
            handleErrorLabel(nameError, "", false);
            handleErrorLabel(brandError, "", false);
            handleErrorLabel(capacityError, "", false);
            handleErrorLabel(minimumError, "", false);
        });
    }

    /**
     * Check is the input is a number and if it is bigger than 0
     * @param input Input Text Field
     * @param errorLabel Error Label to display the error
     */
    private void checkInputNumber(TextField input, Label errorLabel){
        try {
            int number = Integer.parseInt(input.getText());
            if (number <= 0)
                handleErrorLabel(errorLabel, "Menge größer als 0!", true);
            else
                handleErrorLabel(errorLabel, "", false);

        } catch (NumberFormatException e){
            handleErrorLabel(errorLabel, "Zahl eingeben!", true);
        }
    }

    /**
     * Set's the Values of the selected Product.
     * @param selectedProduct The selected Product
     */
    protected void setProductValues(Product selectedProduct){
        barcode.setText(selectedProduct.getBarcode());
        name.setText(selectedProduct.getName());
        brand.setText(selectedProduct.getBrand());
        categoryCB.setValue(selectedProduct.getCategory());
        placeCB.setValue(selectedProduct.getPlace());
        unitCB.setValue(selectedProduct.getUnit());
        capacity.setText(Integer.toString(selectedProduct.getCapacity()));
        minimum.setText(Float.toString(selectedProduct.getMinAmount()));
    }

    /**
     * Opens the scan result in a new window, displays if the product is in the db and stored or it have to be added.
     * @param scanResult If the scan product is in the db
     * @param heading Text for the Heading of the scan result window
     * @param message Text for the Massage of the scan result window
     */
    protected void openScanResultWindow(boolean scanResult, String heading, String message){
        ProcessFxmlFiles resultWindow = new ProcessFxmlFiles("/fxml/scanResult.fxml", "Scan Ergebniss");
        ScanResultController controller = (ScanResultController) resultWindow.openInNewStage();
        controller.setScanResult(scanResult, heading, message);
    }

    /**
     * Opens select product in a new window, to choose witch product you want to chose.
     * @param products List of the matching products
     */
    private void openSelectProductWindow(ObservableList<Product> products){
        ProcessFxmlFiles selectProducts = new ProcessFxmlFiles("/fxml/selectProductWindow.fxml", "Produkt wählen");
        SelectProductController controller = (SelectProductController) selectProducts.openInNewStage();
        controller.setValues(products);
        controller.setParentController(this);
    }

    /**
     * Clear all the inputs, to scan a new product.
     */
    protected void clearAllInputs(){
        clearCommonInputs();
        unitCB.setValue(unitOptions.get(0));
        capacity.setText("");
        minimum.setText("");
    }

    /**
     * Set's the Vales for the Table Columns.
     */
    protected void setupTableView(){
        barcodeTC.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandTC.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryTC.setCellValueFactory(new PropertyValueFactory<>("category"));
        placeTC.setCellValueFactory(new PropertyValueFactory<>("place"));
        unitTC.setCellValueFactory(new PropertyValueFactory<>("unit"));
        capacityTC.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    }

    /**
     * Get's the Values from the Database and set's it to the Table View.
     * @param sqlStmt What values should be selected
     */
    protected void setTableViewValues(String sqlStmt){
        ObservableList<Product> products = ReadFromDb.getProducts(sqlStmt);
        productsTV.setItems(products);
    }

    /**
     * Get's all the stored Products with the passed productId, that are not open from the Database.
     * @param productId The id of the search Product
     * @return A list of all stored Product with the passed Product id.
     */
    private ObservableList<StoredProduct> getStoredProducts(int productId){
        String stmt = "SELECT id, productId, open, openSince, place, productAmount, amount FROM storedProducts WHERE productId = " + productId
                + " AND open = false";
        return ReadFromDb.getStoredProducts(stmt);
    }

    /**
     * Stores the product to the storedProducts Database, or increases the amount.
     * @param selectedProduct The selected Product
     */
    protected void storeProduct(Product selectedProduct){
        String stmt = "";

        ObservableList<StoredProduct> storedProducts = getStoredProducts(selectedProduct.getId());
        if (storedProducts.size() > 0){
            stmt = "UPDATE storedProducts SET amount = ? WHERE id = " + storedProducts.get(0).getId();
            WriteToDb.executeWriteStmt(stmt, Integer.toString(storedProducts.get(0).getAmount() + 1));
        }
        else {
            stmt = "INSERT INTO storedProducts(productId, open, openSince, place, productAmount, amount) VALUES (?,?,?,?,?,?)";
            WriteToDb.executeWriteStmt(stmt, Integer.toString(selectedProduct.getId()), Integer.toString(0), null, selectedProduct.getPlace(),
                    Integer.toString(selectedProduct.getCapacity()), Integer.toString(1));
        }
    }
}
