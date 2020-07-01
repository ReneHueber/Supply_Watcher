package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class ScanResultController {

    @FXML
    private Label headingLB;
    @FXML
    private Label textLB;

    /**
     * Set a Massage if the Scan Result is in the Db or not.
     * @param scanOkay Product is in Db or not
     */
    protected void setScanResult(boolean scanOkay, String heading, String text) {
        if (scanOkay)
            headingLB.setStyle("-fx-text-fill: #46a651");
        else
            headingLB.setStyle("-fx-text-fill: #a64646");

        headingLB.setText(heading);
        textLB.setText(text);

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
                    Stage stage = (Stage) headingLB.getScene().getWindow();
                    stage.close();
                    timer.cancel();
                });
            }
        },1500);
    }
}
