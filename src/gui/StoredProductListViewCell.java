package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import objects.CombinedProducts;

import java.io.IOException;

/**
 * Set's the custom Items for every Cell in the List View.
 */
public class StoredProductListViewCell extends ListCell<CombinedProducts> {

    @FXML
    private GridPane parentLayout;

    @FXML
    private Label barcodeLb;
    @FXML
    private Label nameLb;
    @FXML
    private Label brandLb;

    @FXML
    private Label categoryLb;
    @FXML
    private Label capacityLb;
    @FXML
    private Label amountLeftLb;

    @FXML
    private Label placeClosedLb;
    @FXML
    private Label placeOpenLb;
    @FXML
    private Label openSinceLb;

    @FXML
    private Label amountClosedLb;
    @FXML
    private Label amountOpenLb;
    @FXML
    private Label minAmountLb;

    private FXMLLoader fxmlLoader;

    /**
     * Set's the values for the custom List view Items.
     * Is call for every visible Item in the List view.
     * @param product Object of the passed Observable List
     * @param empty If the Object is empty
     */
    @Override
    protected void updateItem(CombinedProducts product, boolean empty) {
        super.updateItem(product, empty);

        // loads the fxml file if the passed object is not empty
        if (empty || product == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/storedProductListView.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            // set's the values for the gui elements
            barcodeLb.setText(product.getBarcode());
            nameLb.setText(product.getName());
            brandLb.setText(product.getBrand());
            categoryLb.setText(product.getCategory());
            capacityLb.setText(product.getCapacityWithUnit());
            amountLeftLb.setText(product.getLeftCapacityWithUnit());
            placeClosedLb.setText(product.getPlaceClosed());
            placeOpenLb.setText(product.getPlaceOpen());
            openSinceLb.setText(product.getOpenSince().toString());
            amountClosedLb.setText(Integer.toString(product.getAmountClosed()));
            amountOpenLb.setText(Float.toString(product.getAmountOpen()));
            minAmountLb.setText(Float.toString(product.getMinAmount()));

            // set's the custom layout
            setText(null);
            setGraphic(parentLayout);
        }
    }
}

