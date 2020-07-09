package objects;

import java.sql.Date;

public class CombinedProducts {

    private final String barcode;
    private final String name;
    private final String brand;
    private final String category;
    private final int capacity;
    private final int leftCapacity;
    private final String placeClosed;
    private final String placeOpen;
    private final Date openSince;
    private final int amountClosed;
    private final float amountOpen;
    private final float minAmount;
    private final String unit;

    public CombinedProducts(String barcode, String name, String brand, String category, int capacity,
                            int leftCapacity, String placeClosed, String placeOpen, Date openSince,
                            int amountClosed, float amountOpen, float minAmount, String unit) {
        this.barcode = barcode;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.capacity = capacity;
        this.leftCapacity = leftCapacity;
        this.placeClosed = placeClosed;
        this.placeOpen = placeOpen;
        this.openSince = openSince;
        this.amountClosed = amountClosed;
        this.amountOpen = amountOpen;
        this.minAmount = minAmount;
        this.unit = unit;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLeftCapacity() {
        return leftCapacity;
    }

    public String getPlaceClosed() {
        return placeClosed;
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

    public float getMinAmount() {
        return minAmount;
    }

    public String getCapacityWithUnit(){
        return addUnit(Integer.toString(capacity));
    }

    public String getLeftCapacityWithUnit(){
        return addUnit(Float.toString(leftCapacity));
    }

    private String addUnit(String number){
        switch(unit){
            case "Gramm": return number + "g";
            case "ml": return  number + "ml";
            case "Stück": return number + "st";
            default: return number;
        }
    }
}