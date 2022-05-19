package controller.servlet;

import bussiness.BOFactory;
import bussiness.custom.CustomerBO;
import bussiness.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;
import entity.Customer;
import javafx.collections.ObservableList;

import javax.annotation.Resource;
import javax.json.*;
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
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")

public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String cId = req.getParameter("cusId");
        String option = req.getParameter("option");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();

            switch (option) {

                case "SEARCH":
                    Customer customer = customerBO.searchCustomer(cId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getId());
                    objectBuilder.add("name", customer.getName());
                    objectBuilder.add("address", customer.getAddress());
                    objectBuilder.add("city", customer.getCity());
                    objectBuilder.add("province", customer.getProvince());
                    objectBuilder.add("postalCode", customer.getPostalCode());

                    break;

                case "GETALL":

                    ObservableList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (CustomerDTO c : allCustomers) {
                        JsonObjectBuilder ob = Json.createObjectBuilder();

                        ob.add("id", c.getCustomerId());
                        ob.add("name", c.getCustomerName());
                        ob.add("address", c.getCustomerAddress());
                        ob.add("city", c.getCity());
                        ob.add("province", c.getProvince());
                        ob.add("postalCode", c.getPostalCode());

                        arrayBuilder.add(ob.build());
                    }

                    writer.print(arrayBuilder.build());
                    break;
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }

    }

        @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        Connection connection = null;

        try {
            connection = ds.getConnection();

            CustomerDTO customerDTO = new CustomerDTO(
                    req.getParameter("customerID"),
                    req.getParameter("customerName"),
                    req.getParameter("customerAddress"),
                    req.getParameter("city"),
                    req.getParameter("province"),
                    Integer.parseInt(req.getParameter("postalCode"))

            );

            if (customerBO.addCustomer(customerDTO,connection)) {

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Customer Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

}
