package bussiness.custom;

import bussiness.SuperBO;

import javax.json.JsonArray;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    public JsonArray getAllCustomers() throws SQLException, ClassNotFoundException;
}
