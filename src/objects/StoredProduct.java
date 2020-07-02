package objects;

import java.sql.Date;

public class StoredProduct {

    private final int id;
    private final int productId;
    private final boolean open;
    private final Date openSince;
    private final String place;
    private final int productAmount;
    private final int amount;

    public StoredProduct(int id, int productId, boolean open, Date openSince, String place, int productAmount, int amount) {
        this.id = id;
        this.productId = productId;
        this.open = open;
        this.openSince = openSince;
        this.place = place;
        this.productAmount = productAmount;
        this.amount = amount;
    }

    public int getId(){
        return id;
    }

    public int getProductId(){
        return productId;
    }

    public boolean isOpen() {
        return open;
    }

    public Date getOpenSince() {
        return openSince;
    }

    public String getPlace() {
        return place;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public int getAmount() {
        return amount;
    }
}
