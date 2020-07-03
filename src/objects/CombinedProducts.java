package objects;

import java.sql.Date;

public class CombinedProducts {

    private final String name;
    private final String brand;
    private final String category;
    private final String place;
    private final boolean open;
    private final Date openSince;
    private final int leftCapacity;
    private final int amount;
    private final float minAmount;

    private final String openAsString;

    public CombinedProducts(String name, String brand, String category, String place, boolean open, Date openSince, int leftCapacity, int amount, float minAmount) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.place = place;
        this.open = open;
        this.openSince = openSince;
        this.leftCapacity = leftCapacity;
        this.amount = amount;
        this.minAmount = minAmount;
        this.openAsString = Boolean.toString(open);
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

    public String getPlace() {
        return place;
    }

    public boolean isOpen() {
        return open;
    }

    public String getOpenAsString(){
        return openAsString;
    }

    public Date getOpenSince() {
        return openSince;
    }

    public int getLeftCapacity() {
        return leftCapacity;
    }

    public int getAmount() {
        return amount;
    }

    public float getMinAmount() {
        return minAmount;
    }
}
