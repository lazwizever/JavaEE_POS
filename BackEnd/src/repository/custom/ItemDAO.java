package repository.custom;

import entity.Item;
import repository.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean updateStock(String itemCode, int customerQTY, Connection connection) throws SQLException, ClassNotFoundException;
}
