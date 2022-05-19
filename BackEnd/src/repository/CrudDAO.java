package repository;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;


public interface CrudDAO<T,ID> extends SuperDAO{
    JsonArray getAll() throws SQLException, ClassNotFoundException;
    boolean add(T t , Connection connection) throws SQLException, ClassNotFoundException;
    boolean update(T t) throws SQLException, ClassNotFoundException;
    boolean delete(ID id) throws SQLException, ClassNotFoundException;
}
