package entity;

public class Item {
    private String itemCode;
    private String description ;
    private int packSize ;
    private double unitPrice ;
    private int qtyOnHand ;

    public Item() {
    }

    public Item(String itemCode, String description, int packSize, double unitPrice, int qtyOnHand) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setPackSize(packSize);
        this.setUnitPrice(unitPrice);
        this.setQtyOnHand(qtyOnHand);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int packSize) {
        this.packSize = packSize;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}
