package objects;

public class Product {

    private final String barcode;
    private final String name;
    private final String brand;
    private final String category;
    private final String place;
    private final String unit;
    private final int capacity;
    private final float minAmount;

    public Product(String barcode, String name, String brand, String category, String place, String unit, int capacity, float minAmount) {
        this.barcode = barcode;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.place = place;
        this.unit = unit;
        this.capacity = capacity;
        this.minAmount = minAmount;
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

    public String getPlace() {
        return place;
    }

    public String getUnit() {
        return unit;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getMinAmount() {
        return minAmount;
    }
}
