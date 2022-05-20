package bussiness.custom.impl;

import bussiness.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.DAOFactory;
import repository.custom.CustomerDAO;
import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                itemDTO.getItemCode(),itemDTO.getDescription(),Integer.parseInt(itemDTO.getPackSize()),Double.parseDouble(itemDTO.getUnitPrice()),
                Integer.parseInt(itemDTO.getQtyOnHand())
        );

       return itemDAO.add(item,connection);
    }

    @Override
    public ObservableList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<Item> allItems = itemDAO.getAll(connection);

        ObservableList<ItemDTO> items = FXCollections.observableArrayList();

        for (Item temp : allItems) {
            ItemDTO itemDTO = new ItemDTO(
                    temp.getItemCode(),temp.getDescription(),String.valueOf(temp.getPackSize()),String.valueOf(temp.getUnitPrice()),
                    String.valueOf(temp.getQtyOnHand())
            );
            items.add(itemDTO);
        }
        return items;
    }

    @Override
    public ItemDTO searchCustomer(String itemId, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemId,connection);

        ItemDTO itemDTO = new ItemDTO(
                item.getItemCode(),item.getDescription(),String.valueOf(item.getPackSize()),String.valueOf(item.getUnitPrice()),
                String.valueOf(item.getPackSize())
        );
        return itemDTO;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(
                itemDTO.getItemCode(),itemDTO.getDescription(),Integer.parseInt(itemDTO.getPackSize()),
                Double.parseDouble(itemDTO.getUnitPrice()),Integer.parseInt(itemDTO.getQtyOnHand())
        );

        return itemDAO.update(item,connection);
    }
}
