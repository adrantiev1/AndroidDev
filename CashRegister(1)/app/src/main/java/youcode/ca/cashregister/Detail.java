package youcode.ca.cashregister;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class Detail
{
    int id;
    int receiptId;
    String description;
    double price;

    public Detail(int id, int receiptId, String description, double price)
    {
        this.id = id;
        this.receiptId = receiptId;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
