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

    /*@Override
    public JsonArray getAllCustomers() throws SQLException, ClassNotFoundException {
        JsonArray allCustomers = customerDAO.getAll();

        ObservableList obList = FXCollections.observableArrayList();

        for (int i = 0; i < allCustomers.size(); i++) {
            JsonObject jsonObject = allCustomers.getJsonObject(i);

            obList.addAll(new CustomerDTO(jsonObject.getString("id"), jsonObject.getString("name"),
                    jsonObject.getString("address"), jsonObject.getString("city"), jsonObject.getString("province"),
                    jsonObject.getInt("postalCode")));

        }
        return (JsonArray) obList;
    }*/


    @Override
    public ObservableList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> customers = customerDAO.getAll(connection);

        ObservableList<CustomerDTO> obCusList = FXCollections.observableArrayList();

        for (Customer temp : customers) {
            CustomerDTO customerDTO = new CustomerDTO(
                    temp.getId(),temp.getName(),temp.getAddress(),temp.getCity(),temp.getProvince(),temp.getPostalCode()
            );

            obCusList.add(customerDTO);
        }
        return obCusList;
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(
                customerDTO.getCustomerId(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCity(),
                customerDTO.getProvince(),customerDTO.getPostalCode()

        );
       return customerDAO.add(customer,connection);
    }

    @Override
    public Customer searchCustomer(String cId, Connection connection) {
        return null;
    }
}
