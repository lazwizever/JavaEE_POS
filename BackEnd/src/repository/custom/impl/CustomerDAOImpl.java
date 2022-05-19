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

   /* @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into Customer values(?,?,?,?,?,?)");
            pstm.setObject(1, req.getParameter("customerID"));
            pstm.setObject(2, req.getParameter("customerName"));
            pstm.setObject(3,  req.getParameter("customerAddress"));
            pstm.setObject(4, req.getParameter("city"));
            pstm.setObject(5, req.getParameter("province"));
            pstm.setObject(6, req.getParameter("postalCode"));

            if (pstm.executeUpdate() > 0) {

                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
    }*/

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


}
