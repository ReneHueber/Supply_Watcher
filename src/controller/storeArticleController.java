package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class storeArticleController extends basicController {

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
        // set's the category options
        setCategoryOptions();

        // handles the changes of the combo boxes
        setCategoryAction();

        confirm.setOnAction(event -> {
            overviewController controller = openOverviewWindow();
        });
    }
}
