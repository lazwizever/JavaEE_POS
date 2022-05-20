package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String cusId;
    private String orderDate;
    private String total;
    private ArrayList<ItemDetailsDTO> items;


    public OrderDTO() {
    }

    public OrderDTO(String orderId, String cusId, String orderDate, String total, ArrayList<ItemDetailsDTO> items) {
        this.setOrderId(orderId);
        this.setCusId(cusId);
        this.setOrderDate(orderDate);
        this.setTotal(total);
        this.setItems(items);
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<ItemDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsDTO> items) {
        this.items = items;
    }
}
