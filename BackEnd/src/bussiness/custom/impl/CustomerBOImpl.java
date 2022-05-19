package bussiness.custom.impl;

import bussiness.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import javax.json.*;
import javax.servlet.http.HttpServlet;
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
    public JsonArray getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(
                customerDTO.getCustomerId(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCity(),
                customerDTO.getProvince(),customerDTO.getPostalCode()

        );
       return customerDAO.add(customer,connection);
    }
}
