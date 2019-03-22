package youcode.ca.cashregister;

/**
 * Created by GSchenk on 11/19/2018.
 */

public class Receipt
{
    private int id;
    private String customerName;

    public Receipt(int id, String customerName)
    {
        this.id = id;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
