package repository;

import javafx.collections.ObservableList;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;


public interface CrudDAO<T,ID> extends SuperDAO{
    ObservableList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;
    boolean add(T t , Connection connection) throws SQLException, ClassNotFoundException;
    boolean update(T t , Connection connection) throws SQLException, ClassNotFoundException;
    boolean delete(ID id,Connection connection) throws SQLException, ClassNotFoundException;
    T search(ID id ,Connection connection) throws SQLException, ClassNotFoundException;
}
