package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class storeArticleController extends basicController {

    @FXML
    private TextField capacity;
    @FXML
    private Label capacityError;
    @FXML
    private TextField minimum;
    @FXML
    private Label minimumError;

    @FXML
    private Button confirm;

    @FXML
    // TODO changes to real object
    private ListView<String> lastArticles;


    public void initialize(){
        // set's the category options
        setCategoryOptions();

        // handles the changes of the combo boxes
        setCategoryAction();

        confirm.setOnAction(event -> {
            overviewController controller = openOverviewWindow();
        });

        handleNameError();

        // checks if the inputs are correct
        capacity.setOnKeyReleased(event -> {
            if (capacity.getText().isEmpty()){
                handleErrorLabel(capacityError, "Menge angeben!", true);
            }
            else {
               checkInputNumber(capacity, capacityError);
            }
        });

        minimum.setOnKeyReleased(event -> {
            if (!minimum.getText().isEmpty()){
                checkInputNumber(minimum, minimumError);
            }
            else{
                handleErrorLabel(minimumError, "", false);
            }

        });
    }

    /**
     * Check is the input is a number and if it is bigger than 0
     * @param input Input Text Field
     * @param errorLabel Error Label to display the error
     */
    private void checkInputNumber(TextField input, Label errorLabel){
        try {
            int number = Integer.parseInt(input.getText());
            if (number <= 0)
                handleErrorLabel(errorLabel, "Menge größer als 0!", true);
            else
                handleErrorLabel(errorLabel, "", false);

        } catch (NumberFormatException e){
            handleErrorLabel(errorLabel, "Zahl eingeben!", true);
        }
    }
}
