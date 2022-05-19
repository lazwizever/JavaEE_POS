package bussiness.custom;

import bussiness.SuperBO;
import dto.CustomerDTO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    public JsonArray getAllCustomers() throws SQLException, ClassNotFoundException;

    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException;
}
