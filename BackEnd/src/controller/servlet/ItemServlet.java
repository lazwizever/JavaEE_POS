package controller.servlet;

import bussiness.BOFactory;
import bussiness.custom.CustomerBO;
import bussiness.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
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


@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;


    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String itemId = req.getParameter("itemCode");
        String option = req.getParameter("option");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();

            switch (option) {

                case "SEARCH":
                    ItemDTO itemDTO1 = itemBO.searchCustomer(itemId, connection);

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    objectBuilder.add("id", itemDTO1.getItemCode());
                    objectBuilder.add("description", itemDTO1.getDescription());
                    objectBuilder.add("packSize", itemDTO1.getPackSize());
                    objectBuilder.add("unitPrice", itemDTO1.getUnitPrice());
                    objectBuilder.add("qtyOnHand", itemDTO1.getQtyOnHand());

                    writer.print(objectBuilder.build());
                    break;

                case "GETALL":

                    ObservableList<ItemDTO> allItems = itemBO.getAllItems(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (ItemDTO itemDTO : allItems) {
                        JsonObjectBuilder ob = Json.createObjectBuilder();

                        ob.add("id", itemDTO.getItemCode());
                        ob.add("description", itemDTO.getDescription());
                        ob.add("packSize", itemDTO.getPackSize());
                        ob.add("unitPrice", itemDTO.getUnitPrice());
                        ob.add("qtyOnHand", itemDTO.getQtyOnHand());

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

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        try {
            Connection connection = ds.getConnection();

            ItemDTO itemDTO = new ItemDTO(
                    req.getParameter("itemId"),
                    req.getParameter("description"),
                    req.getParameter("packSize"),
                    req.getParameter("unitPrice"),
                    req.getParameter("inputQTY")
            );

            if (itemBO.saveItem(itemDTO, connection)){

                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Item Successfully Added.");
                objectBuilder.add("status", resp.getStatus());
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            Connection connection = ds.getConnection();
            ItemDTO itemDTO = new ItemDTO(

                    jsonObject.getString("id"),
                    jsonObject.getString("description"),
                    jsonObject.getString("packSize"),
                    jsonObject.getString("unitPrice"),
                    jsonObject.getString("qtyOnHand")
            );

            if (itemBO.updateItem(itemDTO,connection)) {
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
}
