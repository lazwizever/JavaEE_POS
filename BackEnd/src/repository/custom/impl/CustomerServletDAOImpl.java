package repository.custom.impl;

import entity.Customer;
import repository.CrudUtil;
import repository.custom.CustomerDAO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")

public class CustomerServletDAOImpl extends  HttpServlet implements CustomerDAO {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String salary = req.getParameter("customerSalary");

//        resp.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into Customer values(?,?,?,?)");
            pstm.setObject(1, customerID);
            pstm.setObject(2, customerName);
            pstm.setObject(3, customerAddress);
            pstm.setObject(4, salary);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
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
            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }
    }

    @Override
    public JsonArray getAll() throws SQLException, ClassNotFoundException{

        ResultSet rst = CrudUtil.executeQuery("select * from Customer");

                JsonArrayBuilder customerArrayBuilder = Json.createArrayBuilder(); // json array
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                while (rst.next()) {

                    objectBuilder.add("id", rst.getString(1));
                    objectBuilder.add("name", rst.getString(2));
                    objectBuilder.add("address", rst.getString(3));
                    objectBuilder.add("city", rst.getString(4));
                    objectBuilder.add("province", rst.getString(5));
                    objectBuilder.add("postalCode", rst.getString(6));

                    customerArrayBuilder.add(objectBuilder.build());

                }

        return (JsonArray) customerArrayBuilder;
    }

    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
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
