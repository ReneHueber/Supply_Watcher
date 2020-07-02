package controller;

import database.WriteToDb;
import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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


    public void initialize(){
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
            String sqlStmt = "SELECT barcode, name, brand, category, place, unit, capacity, minAmount FROM products WHERE barcode = ''";
            controller.setTableViewValues(sqlStmt);
        });

        outsource.setOnAction(event -> {
            ProcessFxmlFiles outsourceArticle = new ProcessFxmlFiles("/fxml/outsource.fxml", "Artikel Auslagern");
            Stage stage = (Stage) menuBar.getScene().getWindow();
            OutsourceArticleController controller = (OutsourceArticleController) outsourceArticle.openInExistingStage(stage);
        });
    }
}
