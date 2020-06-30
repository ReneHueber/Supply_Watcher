package controller;

import database.ReadFromDb;
import database.WriteToDb;
import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.Product;

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
    private Button confirm;

    @FXML
    // TODO changes to real object
    private ListView<String> lastArticles;


    public void initialize(){
        // set's the category options
        setCategoryOptions();

        // handles the changes of the combo boxes
        setCategoryAction();

        unitCB.setItems(unitOptions);
        unitCB.setValue(unitOptions.get(0));

        confirm.setOnAction(event -> {
            // TODO check if product already existing
            if (!nameError.isVisible() && !capacityError.isVisible() && !minimumError.isVisible()){
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

                WriteToDb.executeWriteStmt(stmt, barcodeVale, nameValue, brandValue, categoryValue, placeValue, unitVale, capacityValue, minValue);
                OverviewController controller = openOverviewWindow();
            }
        });

        basicInputLabelError(name, nameError, "Name eingeben!");
        basicInputLabelError(brand, brandError, "Marke eingeben!");

        barcode.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("ENTER")){
                String stmt = "SELECT barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE barcode = '" + barcode.getText() + "'";
                ObservableList<Product> products = ReadFromDb.getProducts(stmt);

                if (products != null){
                    if (products.size() == 0){
                        name.requestFocus();
                        openScanResultWindow(false);
                    }
                    else if (products.size() == 1){
                        setProductValues(products.get(0));
                        openScanResultWindow(true);
                    }
                    else {
                        openSelectProductWindow(products);
                    }
                }

            }
        });

        name.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("ENTER")){
                String stmt = "SELECT barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE name LIKE '%" + name.getText() + "%'";
                ObservableList<Product> products = ReadFromDb.getProducts(stmt);

                if (products != null){
                    if (products.size() == 0){
                        brand.requestFocus();
                        openScanResultWindow(false);
                    }
                    else if (products.size() == 1){
                        setProductValues(products.get(0));
                        openScanResultWindow(true);
                    }
                    else {
                        openSelectProductWindow(products);
                    }
                }
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

    private void openScanResultWindow(boolean scanResult){
        ProcessFxmlFiles resultWindow = new ProcessFxmlFiles("/fxml/scanResult.fxml", "Scan Ergebniss");
        ScanResultController controller = (ScanResultController) resultWindow.openInNewStage();
        controller.setScanResult(scanResult);
    }

    private void openSelectProductWindow(ObservableList<Product> products){
        ProcessFxmlFiles selectProducts = new ProcessFxmlFiles("/fxml/selectProductWindow.fxml", "Produkt wählen");
        SelectProductController controller = (SelectProductController) selectProducts.openInNewStage();
        controller.setValues(products);
        controller.setParentController(this);
    }
}
