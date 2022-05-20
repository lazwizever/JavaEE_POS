package controller.servlet;

import bussiness.BOFactory;
import bussiness.custom.CustomerBO;
import bussiness.custom.PlaceOrderBO;
import dto.CustomerDTO;
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


@WebServlet(urlPatterns = "/placeOrder")

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

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        JsonArray items = jsonObject.getJsonArray("items");

        try {

            Connection connection = dataSource.getConnection();

            ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
            for (JsonValue item : items) {
                JsonObject jo = item.asJsonObject();
                orderDetailDTOS.add(new OrderDetailDTO(
                        jo.getString("oId"),
                        jo.getString("itemId"),
                        jo.getString("itemKind"),
                        jo.getString("itemName"),
                        Integer.parseInt(jo.getString("sellQty")),
                        Double.parseDouble(jo.getString("unitPrice")),
                        Integer.parseInt(jo.getString("itemDiscount")),
                        Double.parseDouble(jo.getString("total"))
                ));
            }

            OrderDTO orderDTO = new OrderDTO(
                    jsonObject.getString("orderId"),
                    jsonObject.getString("cusId"),
                    jsonObject.getString("orderDate"),
                    Double.parseDouble(jsonObject.getString("grossTotal")),
                    Double.parseDouble(jsonObject.getString("netTotal")),
                    orderDetailDTOS
            );

            if (placeOrderBO.placeOrder(orderDTO,connection)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Successfully Purchased Order.");
                objectBuilder.add("status", resp.getStatus());
                writer.print(objectBuilder.build());

            }

            connection.close();

        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_OK);

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("data",e.getLocalizedMessage());
            objectBuilder.add("message","Error");
            objectBuilder.add("status",resp.getStatus());
            writer.print(objectBuilder.build());

            e.printStackTrace();
        }
    }*/
}
