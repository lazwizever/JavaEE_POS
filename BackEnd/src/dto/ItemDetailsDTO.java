package dto;

public class ItemDetailsDTO {
    private String itemCode;
    private String orderId;
    private String description;
    private String customerQTY;
    private String unitPrice;
    private String total;

    public ItemDetailsDTO() {
    }

    public ItemDetailsDTO(String itemCode, String orderId, String description, String customerQTY, String unitPrice, String total) {
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

    public String getCustomerQTY() {
        return customerQTY;
    }

    public void setCustomerQTY(String customerQTY) {
        this.customerQTY = customerQTY;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
