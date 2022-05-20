package bussiness.custom;

import bussiness.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    CustomerDTO getCustomer(String cId, Connection connection) throws SQLException, ClassNotFoundException;
}
