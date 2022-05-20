package entity;

public class ItemDetails {
    private String itemCode;
    private String orderId;
    private String description;
    private int customerQTY;
    private double unitPrice;
    private double total;

    public ItemDetails() {
    }

    public ItemDetails(String itemCode, String orderId, String description, int customerQTY, double unitPrice, double total) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setDescription(description);
        this.setCustomerQTY(customerQTY);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerQTY() {
        return customerQTY;
    }

    public void setCustomerQTY(int customerQTY) {
        this.customerQTY = customerQTY;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
