package objects;

public class Product {

    private String barcode;
    private String name;
    private String brand;
    private String category;
    private String place;
    private String unit;
    private int capacity;
    private float minAmount;

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
