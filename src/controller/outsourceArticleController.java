package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class outsourceArticleController extends basicController {

    private final ObservableList<String> unitOptions = FXCollections.observableArrayList(
            "Gramm",
            "Prozent"
    );

    @FXML
    private ComboBox<String> unitCB;
    @FXML
    private TextField amount;

    @FXML
    private Button confirm;

    @FXML
    private ListView<String> articleLV;

    public void initialize(){

        // set's the combo box options
        setCategoryOptions();
        unitCB.setItems(unitOptions);
        unitCB.setValue(unitOptions.get(0));

        // handles the changes of the combo box
        setCategoryAction();

        // handles the click of the confirm Button
        confirm.setOnAction(event -> {
            overviewController controller = openOverviewWindow();
        });
    }

}
