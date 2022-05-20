package controller.servlet;

import bussiness.BOFactory;
import bussiness.custom.CustomerBO;
import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDetailsDTO;
import dto.OrderDTO;
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
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(urlPatterns = "/order")

public class PlaceOrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PLACEORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String cId = req.getParameter("cusId");
        String option = req.getParameter("option");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();

            switch (option) {

                case "GetCustomer":
                    CustomerDTO customer = placeOrderBO.getCustomer(cId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getCustomerId());
                    objectBuilder.add("name", customer.getCustomerName());
                    objectBuilder.add("address", customer.getCustomerAddress());
                    objectBuilder.add("city", customer.getCity());
                    objectBuilder.add("province", customer.getProvince());
                    objectBuilder.add("postalCode", customer.getPostalCode());

                    writer.print(objectBuilder.build());
                    break;

               /* case "GETALL":

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

                    break;*/

            }

            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        JsonArray items = jsonObject.getJsonArray("items");

        ArrayList<ItemDetailsDTO> allItems = new ArrayList<>();


        for (JsonValue temp : items) {

            JsonObject jsonObject1 = temp.asJsonObject();
            System.out.println("balala"+ jsonObject1.getString("__unitPrice"));

            allItems.add(new ItemDetailsDTO(
                    jsonObject1.getString("__itemCode"),
                    jsonObject1.getString("__description"),
                    jsonObject1.getString("__customerQTY"),
                    jsonObject1.getString("__unitPrice"),
                    jsonObject1.getString("__total")
            ));
        }

        try {
            Connection connection = ds.getConnection();

            OrderDTO orderDTO = new OrderDTO(

                    jsonObject.getString("orderId"),
                    jsonObject.getString("customerId"),
                    jsonObject.getString("orderDate"),
                    jsonObject.getString("netTotal"),
                    allItems

            );

            if (placeOrderBO.placeOrder(orderDTO,connection)) {
                System.out.println("one");
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

        } catch (SQLException e) {

            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Update Failed.");
            objectBuilder.add("status",resp.getStatus());
            writer.print(objectBuilder.build());

            e.printStackTrace();
        }
    }
}
