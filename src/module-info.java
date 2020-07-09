module Supply.Watcher {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens controller;
    opens objects to javafx.base;
    opens gui;
}