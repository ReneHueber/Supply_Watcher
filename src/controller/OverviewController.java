package controller;

import gui.ProcessFxmlFiles;
import gui.StoredProductListViewCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import objects.CombinedProducts;

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
    private ListView<CombinedProducts> storedProductsLv;


    public void initialize(){

        setupListView();

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
            controller.setTableViewValues("SELECT id, productId, open, openSince, place, productAmount, amount FROM storedProducts", "barcode");
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

    protected void setupListView(){
        storedProductsLv.setCellFactory(StoredProductListViewCell -> new StoredProductListViewCell());
        String sqlStmt = "SELECT id, productId, leftCapacity, placeOpen, openSince, amountClosed, amountOpen FROM storedProducts" +
                " WHERE placeOpen = 'Kühlschrank'";
        storedProductsLv.setItems(getCombinedProducts(sqlStmt, "Lebensmittel"));
    }

}
