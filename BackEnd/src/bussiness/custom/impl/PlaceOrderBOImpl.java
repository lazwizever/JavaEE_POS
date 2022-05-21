package bussiness.custom.impl;

import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;
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
    ItemDetailsDAO itemDetailsDAO = (ItemDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMDETAIL);

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
            orderItems.add(new ItemDetails(itemDetailsDTO.getItemCode(),orderDTO.getOrderId(),itemDetailsDTO.getDescription(),
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

    @Override
    public ItemDTO searchItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id,connection);

        ItemDTO itemDTO = new ItemDTO(
                item.getItemCode(),item.getDescription(),String.valueOf(item.getPackSize()),String.valueOf(item.getUnitPrice()),
                String.valueOf(item.getQtyOnHand())
        );
        return itemDTO;
    }

    @Override
    public OrderDTO searchOrder(String id, Connection connection) throws SQLException, ClassNotFoundException {

        ArrayList<ItemDetailsDTO> itemDetailsDTOS = new ArrayList<>();

        Order order = orderDAO.search(id,connection);
        ArrayList<ItemDetails> itemDetails = itemDetailsDAO.getOrderItems(order.getOrderId(),connection);

        for (ItemDetails temp : itemDetails) {
            ItemDetailsDTO itemDetailsDTO = new ItemDetailsDTO(
                    temp.getItemCode(),temp.getOrderId(),temp.getDescription(),String.valueOf(temp.getCustomerQTY()),String.valueOf(temp.getUnitPrice()),String.valueOf(temp.getTotal())
            );
            itemDetailsDTOS.add(itemDetailsDTO);
        }

        OrderDTO orderDTO = new OrderDTO(
                order.getOrderId(),order.getCusId(),order.getOrderDate(),String.valueOf(order.getTotal()),itemDetailsDTOS

        );
        return orderDTO;
    }


}
