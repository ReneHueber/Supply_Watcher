package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Controls the Gui elements of the overview Window.
 */
public class overviewController {

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
    private ComboBox<String> categoryCB;
    @FXML
    private ComboBox<String> placeCB;
    @FXML
    private ComboBox<String> sortByCB;


    public void initialize(){
        // set's the options and values for the check boxes
        categoryCB.setItems(categoryOptions);
        placeCB.setItems(placeOptionFood);
        sortByCB.setItems(sortByOption);

        categoryCB.setValue(categoryOptions.get(0));
        placeCB.setValue(placeOptionFood.get(0));
        sortByCB.setValue(sortByOption.get(0));

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
}
