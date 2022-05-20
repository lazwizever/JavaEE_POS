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
                    CustomerDTO customer = customerBO.searchCustomer(cId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getCustomerId());
                    objectBuilder.add("name", customer.getCustomerName());
                    objectBuilder.add("address", customer.getCustomerAddress());
                    objectBuilder.add("city", customer.getCity());
                    objectBuilder.add("province", customer.getProvince());
                    objectBuilder.add("postalCode", customer.getPostalCode());

                    writer.print(objectBuilder.build());
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

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());

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
                   req.getParameter("postalCode")

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


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            Connection connection = ds.getConnection();
            CustomerDTO customerDTO = new CustomerDTO(

                    jsonObject.getString("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("address"),
                    jsonObject.getString("city"),
                    jsonObject.getString("province"),
                    jsonObject.getString("postalCode")

            );

            if (customerBO.updateCustomer(customerDTO,connection)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message","Customer Successfully Updated.");
                objectBuilder.add("status",resp.getStatus());
                writer.print(objectBuilder.build());

            }else{
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message","Update Failed.");
                objectBuilder.add("status",400);
                writer.print(objectBuilder.build());

            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {

            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Update Failed.");
            objectBuilder.add("status",resp.getStatus());
            writer.print(objectBuilder.build());

            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/jason");
        String cId = req.getParameter("CusID");

        PrintWriter writer = resp.getWriter();

        try {

            Connection connection = ds.getConnection();

            if (customerBO.deleteCustomer(cId,connection)) {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_OK);
                objectBuilder.add("message","Customer Successfully Deleted.");
                objectBuilder.add("status",resp.getStatus());
                writer.print(objectBuilder.build());

            }else {

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message","Wrong Id Inserted.");
                objectBuilder.add("status",400);
                writer.print(objectBuilder.build());

            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {

            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Error");
            objectBuilder.add("status",resp.getStatus());
            writer.print(objectBuilder.build());

            e.printStackTrace();
        }
    }
}
