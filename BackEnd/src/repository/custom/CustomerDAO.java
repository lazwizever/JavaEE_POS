package repository.custom;

import entity.Customer;
import repository.CrudDAO;

import javax.json.JsonArray;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public JsonArray getAll() throws SQLException, ClassNotFoundException;
}
