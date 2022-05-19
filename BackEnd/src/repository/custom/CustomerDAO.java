package repository.custom;

import entity.Customer;
import javafx.collections.ObservableList;
import repository.CrudDAO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public ObservableList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException;
}
