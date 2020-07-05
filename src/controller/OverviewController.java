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
    private TableColumn<CombinedProducts, String> nameTC;
    @FXML
    private TableColumn<CombinedProducts, String> brandTC;
    @FXML
    private TableColumn<CombinedProducts, String> categoryTC;
    @FXML
    private TableColumn<CombinedProducts, String> placeTC;
    @FXML
    private TableColumn<CombinedProducts, String> openTC;
    @FXML
    private TableColumn<CombinedProducts, Date> openSinceTC;
    @FXML
    private TableColumn<CombinedProducts, Integer> leftCapacityTC;
    @FXML
    private TableColumn<CombinedProducts, Integer> amountTC;
    @FXML
    private TableColumn<CombinedProducts, Integer> minAmountTC;




    public void initialize(){
        setupTableView();

        // set's the options for the check boxes
        setCategoryOptions();

        sortByCB.setItems(sortByOption);
        sortByCB.setValue(sortByOption.get(0));

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

        categoryCB.setOnAction(event -> {
            setCategoryAction();
            String sqlStmt = "SELECT id, productId, open, openSince, place, productAmount, amount FROM storedProducts" +
                    " WHERE place = '" + placeCB.getSelectionModel().getSelectedItem() + "'";
            setTableViewValues(sqlStmt, categoryCB.getSelectionModel().getSelectedItem());
        });

        placeCB.setOnAction(event -> {
            String sqlStmt = "SELECT id, productId, open, openSince, place, productAmount, amount FROM storedProducts" +
                    " WHERE place = '" + placeCB.getSelectionModel().getSelectedItem() + "'";
            setTableViewValues(sqlStmt, categoryCB.getSelectionModel().getSelectedItem());
        });

        sortByCB.setOnAction(event -> {
            storedProductsTV.getSortOrder().clear();
            switch(sortByCB.getSelectionModel().getSelectedItem()){
                case "geöffnet": openSinceTC.setSortType(TableColumn.SortType.DESCENDING);
                                 storedProductsTV.getSortOrder().add(openSinceTC);
                                 break;
                case "mindesmenge": break;
                case "alphabet" : nameTC.setSortType(TableColumn.SortType.ASCENDING);
                                  storedProductsTV.getSortOrder().add(nameTC);
                                  break;
            }
            storedProductsTV.sort();
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
    private ObservableList<CombinedProducts> getCombinedProducts(String stmt, String sortOption){
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

            // if the stored product is from the right product category
            if (category.equals(sortOption))
                combinedProducts.add(new CombinedProducts(name, brand, category, place, open, openSince, leftCapacity, amount, minAmount));
        }

        return combinedProducts;
    }

    /**
     * Set's the default Values for the Table View.
     */
    protected void setTableViewValues(String sqlStmt, String sortOption){
        storedProductsTV.setItems(getCombinedProducts(sqlStmt, sortOption));
    }
}
