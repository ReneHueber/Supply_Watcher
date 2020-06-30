package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OutsourceArticleController extends BasicController {

    private final ObservableList<String> unitOptions = FXCollections.observableArrayList(
            "Gramm",
            "ml",
            "Prozent"
    );

    @FXML
    private ComboBox<String> unitCB;
    @FXML
    private TextField amount;
    @FXML
    private Label amountError;

    @FXML
    private Button confirm;

    @FXML
    // TODO changes to real object
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
            OverviewController controller = openOverviewWindow();
        });

        basicInputLabelError(name, nameError, "Name eingeben!");
        basicInputLabelError(brand, brandError, "Marke eingeben!");


        /*
        * checks if the amount input is correct
        * if the input is not empty
        * if the input is a number
        * if the number is bigger than 0
        * if the number is smaller than 100 if percent is chosen as unit
         */
        amount.setOnKeyReleased(event -> {
            if (amount.getText().isEmpty())
                handleErrorLabel(amountError, "Menge eingeben!", true);
            else{
                try {
                    int number = Integer.parseInt(amount.getText());
                    if (number <= 0){
                        handleErrorLabel(amountError, "Menge größer als 0!", true);
                    }
                    else {
                        if (unitCB.getSelectionModel().getSelectedItem().equals("Prozent") && number > 100)
                            handleErrorLabel(amountError, "Nicht mehr als 100!", true);
                        else
                            handleErrorLabel(amountError, "", false);

                    }
                } catch (NumberFormatException e){
                    handleErrorLabel(amountError, "Zahl eingeben!", true);
                }
            }
        });

        // checks the entered amount if the unit checkbox value is changes
        // if the amount smaller than 100 if the unit is percent
        unitCB.setOnAction(event -> {
            try {
                int number = Integer.parseInt(amount.getText());
                if (unitCB.getSelectionModel().getSelectedItem().equals("Prozent") && number > 100)
                    handleErrorLabel(amountError,"Nicht mehr als 100!", true);
                else
                    handleErrorLabel(amountError, "", false);
            } catch (NumberFormatException ignored){
            }
        });
    }

}
