package bussiness.custom.impl;

import bussiness.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO{

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ObservableList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> customers = customerDAO.getAll(connection);

        ObservableList<CustomerDTO> obCusList = FXCollections.observableArrayList();

        for (Customer temp : customers) {
            CustomerDTO customerDTO = new CustomerDTO(
                    temp.getId(),temp.getName(),temp.getAddress(),temp.getCity(),temp.getProvince(),String.valueOf(temp.getPostalCode())
            );

            obCusList.add(customerDTO);
        }
        return obCusList;
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(
                customerDTO.getCustomerId(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCity(),
                customerDTO.getProvince(),Integer.parseInt(customerDTO.getPostalCode())

        );
       return customerDAO.add(customer,connection);
    }

    @Override
    public CustomerDTO searchCustomer(String cId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cId,connection);

        CustomerDTO customerDTO = new CustomerDTO(
                customer.getId(),customer.getName(),customer.getAddress(),customer.getCity(),
                customer.getProvince(),String.valueOf(customer.getPostalCode())
        );
        return customerDTO;

    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(
                customerDTO.getCustomerId(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),
                customerDTO.getCity(),customerDTO.getProvince(),Integer.parseInt(customerDTO.getPostalCode())
        );

        return customerDAO.update(customer,connection);
    }

    @Override
    public boolean deleteCustomer(String cusId, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(cusId,connection);
    }
}
