package controller;

import gui.ProcessFxmlFiles;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ProcessFxmlFiles overViewWindow = new ProcessFxmlFiles("/fxml/overview.fxml", "Übersicht");
        OverviewController controller = (OverviewController) overViewWindow.openInExistingStage(primaryStage);
        controller.setTableViewValues();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
