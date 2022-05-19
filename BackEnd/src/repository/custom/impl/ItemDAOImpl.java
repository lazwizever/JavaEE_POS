package repository.custom.impl;

import entity.Customer;
import entity.Item;
import javafx.collections.ObservableList;
import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public ObservableList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Item search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
