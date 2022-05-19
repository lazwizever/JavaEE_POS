package controller.servlet;

import bussiness.custom.ItemBO;
import dto.ItemDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        try {
            Connection connection = ds.getConnection();

            ItemDTO item = new ItemDTO(
                    jsonObject.getString("code"),
                    jsonObject.getString("kind"),
                    jsonObject.getString("itemName"),
                    Integer.parseInt(jsonObject.getString("qtyOnHand")),
                    Double.parseDouble(jsonObject.getString("unitPrice"))
            );

            if (itemBO.saveItem(item, connection)){
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("message", "Item Successfully Added.");
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
    }
}
