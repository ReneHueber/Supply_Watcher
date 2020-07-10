package objects;

import java.sql.Date;

public class StoredProduct {

    private final int id;
    private final int productId;
    private final int leftCapacity;
    private final String placeOpen;
    private final Date openSince;
    private final int amountClosed;
    private final float amountOpen;

    public StoredProduct(int id, int productId, int leftCapacity, String placeOpen, Date openSince, int productAmount, float amountOpen) {
        this.id = id;
        this.productId = productId;
        this.leftCapacity = leftCapacity;
        this.placeOpen = placeOpen;
        this.openSince = openSince;
        this.amountClosed = productAmount;
        this.amountOpen = amountOpen;
    }

    public int getId(){
        return id;
    }

    public int getProductId(){
        return productId;
    }

    public int getLeftCapacity() {
        return leftCapacity;
    }

    public String getPlaceOpen() {
        return placeOpen;
    }

    public Date getOpenSince() {
        return openSince;
    }

    public int getAmountClosed() {
        return amountClosed;
    }

    public float getAmountOpen() {
        return amountOpen;
    }
}
