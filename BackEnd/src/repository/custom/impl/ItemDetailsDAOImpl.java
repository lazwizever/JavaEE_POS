package repository.custom.impl;

import entity.ItemDetails;
import javafx.collections.ObservableList;
import repository.custom.ItemDetailsDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDetailsDAOImpl implements ItemDetailsDAO {
    @Override
    public ObservableList<ItemDetails> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(ItemDetails itemDetails, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ItemDetails itemDetails, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetails search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
