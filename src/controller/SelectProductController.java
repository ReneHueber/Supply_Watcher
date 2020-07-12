package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Product;

public class SelectProductController {

    @FXML
    private TableView<Product> productsTV;
    @FXML
    private TableColumn<Product, String> barcodeTC;
    @FXML
    private TableColumn<Product, String> nameTC;
    @FXML
    private TableColumn<Product, String> brandTC;
    @FXML
    private TableColumn<Product, String> unitTC;
    @FXML
    private TableColumn<Product, Integer> capacityTC;

    @FXML
    private Button confirm;

    private StoreArticleController parentController;


    public void initialize(){
        setupTableView();

        confirm.setOnAction(event -> {
            Product selectedProduct = productsTV.getSelectionModel().getSelectedItem();

            if (selectedProduct != null){
                Stage currentStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                // stores the product into the db
                parentController.setProductValues(selectedProduct);
                parentController.storeProduct(selectedProduct);
                parentController.openScanResultWindow(true, "Produkt gewählt!", selectedProduct.getName() + " eingelagert");
                parentController.clearAllInputs();
                currentStage.close();
            }
        });
    }

    private void setupTableView(){
        barcodeTC.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        nameTC.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandTC.setCellValueFactory(new PropertyValueFactory<>("brand"));
        unitTC.setCellValueFactory(new PropertyValueFactory<>("unit"));
        capacityTC.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    }

    protected void setValues(ObservableList<Product> products){
        productsTV.setItems(products);
    }

    protected void setParentController(StoreArticleController parentController){
        this.parentController = parentController;
    }
}
