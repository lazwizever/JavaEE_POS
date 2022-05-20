package repository.custom.impl;

import entity.Order;
import javafx.collections.ObservableList;
import repository.CrudUtil;
import repository.custom.OrderDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ObservableList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `Order` VALUES(?,?,?,?)",order.getOrderId(),order.getCusId(),order.getOrderDate(),order.getTotal());
    }

    @Override
    public boolean update(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Order order) {
        return false;
    }
}
