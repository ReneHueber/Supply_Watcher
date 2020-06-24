package controller;

import gui.ProcessFxmlFiles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Basic functions and gui elements that are equal at 3 controller classes
 */
public class basicController {

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
    protected TextField brand;

    @FXML
    protected ComboBox<String> categoryCB;
    @FXML
    protected ComboBox<String> placeCB;

    @FXML
    protected Button confirm;

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
    }

    /**
     * Opens the overview Window in the existing Stage.
     * @return The controller of the overview Window
     */
    protected overviewController openOverviewWindow(){
        ProcessFxmlFiles overview = new ProcessFxmlFiles("/fxml/overview.fxml", "Übersicht");
        Stage stage = (Stage) menuBar.getScene().getWindow();
        return (overviewController) overview.openInExistingStage(stage);
    }
}
