package entity;

public class Order {
    private String orderId;
    private String cusId;
    private String orderDate;
    private double total;


    public Order() {
    }

    public Order(String orderId, String cusId, String orderDate, double total) {
        this.setOrderId(orderId);
        this.setCusId(cusId);
        this.setOrderDate(orderDate);
        this.setTotal(total);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
