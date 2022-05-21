package repository.custom;

import entity.ItemDetails;
import repository.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDetailsDAO extends CrudDAO<ItemDetails,String> {

    ArrayList<ItemDetails> getOrderItems(String orderId, Connection connection) throws SQLException, ClassNotFoundException;
}
