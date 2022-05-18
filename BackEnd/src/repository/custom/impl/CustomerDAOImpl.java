package repository.custom.impl;

import entity.Customer;
import repository.CrudUtil;
import repository.custom.CustomerDAO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAOImpl implements CustomerDAO{
   /* @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds = null;*/

    @Override
    public JsonArray getAll() throws SQLException, ClassNotFoundException{

        /*String option = req.getParameter("option");
        resp.setContentType("application/json");*/
        ResultSet rst = CrudUtil.executeQuery("select * from Customer");
        /*PrintWriter writer = resp.getWriter();*/

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
