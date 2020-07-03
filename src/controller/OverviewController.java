package controller;

import database.ReadFromDb;
import database.WriteToDb;
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
import java.util.ArrayList;

/**
 * Controls the Gui elements of the overview Window.
 */
public class OverviewController extends BasicController {

    private final ObservableList<String> sortByOption = FXCollections.observableArrayList(
            "geöffnet",
            "mindesmenge",
            "alphabet"
    );


    @FXML
    private Button store;
    @FXML
    private Button outsource;

    @FXML
    private ComboBox<String> sortByCB;

    @FXML
    private TableView<CombinedProducts> storedProductsTV;
    @FXML
    private TableColumn<String, CombinedProducts> nameTC;
    @FXML
    private TableColumn<String, CombinedProducts> brandTC;
    @FXML
    private TableColumn<String, CombinedProducts> categoryTC;
    @FXML
    private TableColumn<String, CombinedProducts> placeTC;
    @FXML
    private TableColumn<String, CombinedProducts> openTC;
    @FXML
    private TableColumn<Date, CombinedProducts> openSinceTC;
    @FXML
    private TableColumn<Integer, CombinedProducts> leftCapacityTC;
    @FXML
    private TableColumn<Integer, CombinedProducts> amountTC;
    @FXML
    private TableColumn<Integer, CombinedProducts> minAmountTC;




    public void initialize(){
        setupTableView();

        // set's the options for the check boxes
        setCategoryOptions();

        sortByCB.setItems(sortByOption);
        sortByCB.setValue(sortByOption.get(0));

        // handles the changes of the combo boxes
        setCategoryAction();

        store.setOnAction(event -> {
            ProcessFxmlFiles storeArticle = new ProcessFxmlFiles("/fxml/storeArticle.fxml", "Artikel Einlager");
            Stage stage = (Stage) menuBar.getScene().getWindow();
            StoreArticleController controller = (StoreArticleController) storeArticle.openInExistingStage(stage);
            String sqlStmt = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE barcode = ''";
            controller.setTableViewValues(sqlStmt);
        });

        outsource.setOnAction(event -> {
            ProcessFxmlFiles outsourceArticle = new ProcessFxmlFiles("/fxml/outsource.fxml", "Artikel Auslagern");
            Stage stage = (Stage) menuBar.getScene().getWindow();
            OutsourceArticleController controller = (OutsourceArticleController) outsourceArticle.openInExistingStage(stage);
        });
    }

    /**
     * Set's the values for the Table Columns
     */
    private void setupTableView(){
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
    private ObservableList<CombinedProducts> getCombinedProducts(String stmt){
        String sqlAllProducts = "SELECT id, barcode, name, brand, category, place, unit, capacity, minAmount FROM products";

        ObservableList<StoredProduct> storedProducts = ReadFromDb.getStoredProducts(stmt);
        ObservableList<Product> products = ReadFromDb.getProducts(sqlAllProducts);

        ObservableList<CombinedProducts> combinedProducts = FXCollections.observableArrayList();

        for (StoredProduct storedProduct : storedProducts){
            Product product = products.get(storedProduct.getProductId() - 1);

            String name = product.getName();
            String brand = product.getBrand();
            String category = product.getCategory();
            String place = storedProduct.getPlace();
            boolean open = storedProduct.isOpen();
            Date openSince = storedProduct.getOpenSince();
            int leftCapacity = storedProduct.getProductAmount();
            int amount = storedProduct.getAmount();
            float minAmount = product.getMinAmount();

            combinedProducts.add(new CombinedProducts(name, brand, category, place, open, openSince, leftCapacity, amount, minAmount));
        }

        return combinedProducts;
    }

    /**
     * Set's the default Values for the Table View.
     */
    protected void setTableViewValues(){
        // TODO get the values specified by the right chose box option
        String sqlStmt = "SELECT id, productId, open, openSince, place, productAmount, amount FROM storedProducts";
        storedProductsTV.setItems(getCombinedProducts(sqlStmt));
    }
}
