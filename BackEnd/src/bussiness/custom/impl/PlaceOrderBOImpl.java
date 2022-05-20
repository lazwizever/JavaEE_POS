package bussiness.custom.impl;

import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
import entity.Customer;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public CustomerDTO getCustomer(String cId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cId,connection);

        CustomerDTO customerDTO = new CustomerDTO(
                customer.getId(),customer.getName(),customer.getAddress(),customer.getCity(),
                customer.getProvince(),String.valueOf(customer.getPostalCode())
        );
        return customerDTO;
    }
}
