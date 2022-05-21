package controller.servlet;

import bussiness.BOFactory;
import bussiness.custom.CustomerBO;
import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
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

        String id = req.getParameter("id");
        String option = req.getParameter("option");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();

            switch (option) {

                case "getCustomer":
                    CustomerDTO customer = placeOrderBO.getCustomer(id, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", customer.getCustomerId());
                    objectBuilder.add("name", customer.getCustomerName());
                    objectBuilder.add("address", customer.getCustomerAddress());
                    objectBuilder.add("city", customer.getCity());
                    objectBuilder.add("province", customer.getProvince());
                    objectBuilder.add("postalCode", customer.getPostalCode());

                    writer.print(objectBuilder.build());
                    break;

                case "getItem":
                    ItemDTO itemDTO1 = placeOrderBO.searchItem(id, connection);

                    JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();

                    objectBuilder1.add("id", itemDTO1.getItemCode());
                    objectBuilder1.add("description", itemDTO1.getDescription());
                    objectBuilder1.add("packSize", itemDTO1.getPackSize());
                    objectBuilder1.add("unitPrice", itemDTO1.getUnitPrice());
                    objectBuilder1.add("qtyOnHand", itemDTO1.getQtyOnHand());
                    writer.print(objectBuilder1.build());
                    break;

                case "searchOrder":
                    OrderDTO orderDTO = placeOrderBO.searchOrder(id, connection);
                    JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();

                    objectBuilder2.add("orderId", orderDTO.getOrderId());
                    objectBuilder2.add("customerId", orderDTO.getCusId());
                    objectBuilder2.add("orderDate", orderDTO.getOrderDate());
                    objectBuilder2.add("total", orderDTO.getTotal());

                    writer.print(objectBuilder2.build());
                    break;


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

            JsonNumber t = jsonObject1.getJsonNumber("__total");

            String total = String.valueOf(t);

            allItems.add(new ItemDetailsDTO(
                    jsonObject1.getString("__itemCode"),
                    jsonObject1.getString("__orderId"),
                    jsonObject1.getString("__description"),
                    jsonObject1.getString("__customerQTY"),
                    jsonObject1.getString("__unitPrice"),
                    total
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
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message","Successfully Purchased Order.");
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
