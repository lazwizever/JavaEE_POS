package bussiness.custom.impl;

import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.ItemDetails;
import entity.Order;
import repository.DAOFactory;
import repository.custom.CustomerDAO;
import repository.custom.ItemDAO;
import repository.custom.ItemDetailsDAO;
import repository.custom.OrderDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    ItemDetailsDAO itemDetailsDAO = (ItemDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public CustomerDTO getCustomer(String cId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cId,connection);

        CustomerDTO customerDTO = new CustomerDTO(
                customer.getId(),customer.getName(),customer.getAddress(),customer.getCity(),
                customer.getProvince(),String.valueOf(customer.getPostalCode())
        );
        return customerDTO;
    }

    @Override
    public boolean placeOrder(OrderDTO orderDTO, Connection con) {
        Connection connection = null;

        Order order = new Order(orderDTO.getOrderId(),orderDTO.getCusId(),orderDTO.getOrderDate(),Double.parseDouble(orderDTO.getTotal()));

        ArrayList<ItemDetailsDTO> orders = orderDTO.getItems();
        ArrayList<ItemDetails> orderItems = new ArrayList<>();

        for (ItemDetailsDTO itemDetailsDTO : orders) {
            orderItems.add(new ItemDetails(itemDetailsDTO.getItemCode(),itemDetailsDTO.getDescription(),
                    Integer.parseInt(itemDetailsDTO.getCustomerQTY()),Double.parseDouble(itemDetailsDTO.getUnitPrice()),
                    Double.parseDouble(itemDetailsDTO.getTotal())));
        }

        try {
            connection = con;
            connection.setAutoCommit(false);

            if (orderDAO.add(order,connection)){
                L1:for (ItemDetails temp : orderItems) {
                    if (itemDetailsDAO.add(temp,connection)){
                        if (itemDAO.updateStock(temp.getItemCode(),temp.getCustomerQTY(),connection)){
                            continue L1;
                        }else {
                            connection.rollback();
                            return false;
                        }
                    }else {
                        connection.rollback();
                        return false;
                    }
                }
                connection.commit();
                return true;
            }else {
                connection.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }




    /*private boolean saveItemDetail(OrderDTO orderDTO, Connection connection) {
        for (ItemDetailsDTO item : orderDTO.getItems()) {
            boolean ifOrderDetailSaved = ItemDetailsDAO.add(new ItemDetails(
                            item.getItemCode(), item.getDescription(), Integer.parseInt(item.getCustomerQTY()),
                            Double.parseDouble(item.getUnitPrice()), Double.parseDouble(item.getTotal())),
                    connection
            );
            if (ifOrderDetailSaved){
                if (updateQtyOnHand(item.getItemCode(),item.getSellQty(),connection)){

                }else {
                    return false;
                }
            }else {
                return false;
            }
        }

        return true;
    }*/
}
