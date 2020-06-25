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

        /*
        * checks if the capacity input is correct
        * if the input is not empty
        * if the input is a number
        * if the input is bigger than 0
         */
        capacity.setOnKeyReleased(event -> {
            if (capacity.getText().isEmpty()){
                handleErrorLabel(capacityError, "Menge angeben!", true);
            }
            else {
                try {
                    int number = Integer.parseInt(capacity.getText());
                    if (number <= 0)
                        handleErrorLabel(capacityError, "Menge größer als 0!", true);
                    else
                        handleErrorLabel(capacityError, "", false);

                } catch (NumberFormatException e){
                    handleErrorLabel(capacityError, "Zahl eingeben!", true);
                }
            }
        });
    }
}
