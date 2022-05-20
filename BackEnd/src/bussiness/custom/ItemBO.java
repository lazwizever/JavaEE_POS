package bussiness.custom;

import bussiness.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO item, Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;
}
