package bussiness.custom;

import bussiness.SuperBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.ObservableList;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    public ObservableList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String cId, Connection connection) throws SQLException, ClassNotFoundException;
}
