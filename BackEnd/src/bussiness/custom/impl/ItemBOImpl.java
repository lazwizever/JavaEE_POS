package bussiness.custom.impl;

import bussiness.custom.ItemBO;
import dto.ItemDTO;
import repository.DAOFactory;
import repository.custom.CustomerDAO;

import java.sql.Connection;

public class ItemBOImpl implements ItemBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveItem(ItemDTO item, Connection connection) {
        return false;
    }
}
