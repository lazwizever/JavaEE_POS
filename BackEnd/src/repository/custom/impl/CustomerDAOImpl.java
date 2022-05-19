package repository.custom.impl;

import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.CrudUtil;
import repository.custom.CustomerDAO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ObservableList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM Customer");

        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (resultSet.next()){

            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    Integer.parseInt(resultSet.getString(6))
            );

            obList.add(customer);
        }

        return obList;

    }

    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Customer VALUES(?,?,?,?,?,?)",customer.getId(),
                customer.getName(),customer.getAddress(),customer.getCity(),
                customer.getProvince(),customer.getPostalCode());

    }


    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(String cId,Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer WHERE id =?",cId);
        if (rst.next()){
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    Integer.parseInt(rst.getString(6))
            );
        }else {
            return null;
        }
    }

}
