package controller;

import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class storeArticleController {

    // the list of options available for the combo boxes
    private final ObservableList<String> categoryOptions = FXCollections.observableArrayList(
            "Lebensmittel",
            "Verbrauchsgüter"
    );

    private final ObservableList<String> placeOptionFood = FXCollections.observableArrayList(
            "Kühlschrank",
            "Vorratskammer"
    );

    private final ObservableList<String> placeOptionGoods = FXCollections.observableArrayList(
            "Vorratsschrank",
            "Keller"
    );

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField barcode;

    @FXML
    private TextField name;
    @FXML
    private TextField brand;

    @FXML
    private ComboBox<String> categoryCB;
    @FXML
    private ComboBox<String> placeCB;

    @FXML
    private TextField capacity;
    @FXML
    private TextField minimum;

    @FXML
    private Button confirm;

    @FXML
    // TODO changes to real object
    private ListView<String> lastArticles;


    public void initialize(){

        // set's the options and values for the check boxes
        categoryCB.setItems(categoryOptions);
        placeCB.setItems(placeOptionFood);

        categoryCB.setValue(categoryOptions.get(0));
        placeCB.setValue(placeOptionFood.get(0));


        // changes the place option if the category is changed
        categoryCB.setOnAction(event -> {
            if (categoryCB.getSelectionModel().getSelectedItem().equals("Lebensmittel")){
                placeCB.setItems(placeOptionFood);
                placeCB.setValue(placeOptionFood.get(0));
            }
            else{
                placeCB.setItems(placeOptionGoods);
                placeCB.setValue(placeOptionGoods.get(0));
            }

        });

        confirm.setOnAction(event -> {
            ProcessFxmlFiles overview = new ProcessFxmlFiles("/fxml/overview.fxml", "Übersicht");
            Stage stage = (Stage) menuBar.getScene().getWindow();
            overviewController controller = (overviewController) overview.openInExistingStage(stage);
        });
    }
}
