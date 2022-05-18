package bussiness.custom.impl;

import bussiness.custom.CustomerBO;
import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import javax.json.*;
import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public JsonArray getAllCustomers() throws SQLException, ClassNotFoundException {
        JsonArray allCustomers = customerDAO.getAll();

        ObservableList obList = FXCollections.observableArrayList();

        for (int i = 0; i < allCustomers.size(); i++) {
            JsonObject jsonObject = allCustomers.getJsonObject(i);

            obList.addAll(new CustomerDTO(jsonObject.getString("id"), jsonObject.getString("name"),
                    jsonObject.getString("address"), jsonObject.getString("city"), jsonObject.getString("province"),
                    jsonObject.getInt("postalCode")));

        }
        return (JsonArray) obList;
    }
}
