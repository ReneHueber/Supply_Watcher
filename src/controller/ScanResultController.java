package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ScanResultController {

    @FXML
    private Label heading;
    @FXML
    private Label text;

    /**
     * Set a Massage if the Scan Result is in the Db or not.
     * @param scanOkay Product is in Db or not
     */
    protected void setScanResult(boolean scanOkay) {
        if (scanOkay) {
            heading.setStyle("-fx-text-fill: #46a651");
        } else {
            heading.setText("Scan FALSCH");
            text.setText("Produkt in Datenbank speichern");
            heading.setStyle("-fx-text-fill: #a64646");
        }

        startTimer();
    }

    /**
     * Start the timer that closes the Window after 1 second.
     */
    private void startTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Stage stage = (Stage) heading.getScene().getWindow();
                    stage.close();
                    timer.cancel();
                });
            }
        },1000);
    }
}
