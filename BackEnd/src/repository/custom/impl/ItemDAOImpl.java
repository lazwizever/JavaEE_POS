package repository.custom.impl;

import entity.Customer;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.CrudUtil;
import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public ObservableList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection, "SELECT * FROM Item");

        ObservableList<Item> obList = FXCollections.observableArrayList();

        while (resultSet.next()){

            Item item = new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3)),
                    Double.parseDouble(resultSet.getString(4)),
                    Integer.parseInt(resultSet.getString(5))
            );

            obList.add(item);
        }

        return obList;
    }

    @Override
    public boolean add(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Item VALUES(?,?,?,?,?)",item.getItemCode(),item.getDescription(),
                item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE Item SET description = ?,packSize = ?,unitPrice = ?,qtyOnHand = ? WHERE itemCode = ?",item.getDescription(),
                item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getItemCode());
    }

    @Override
    public boolean delete(String itemId, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"Delete from Item WHERE itemCode=?",itemId);
    }

    @Override
    public Item search(String itenId, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Item WHERE itemCode =?",itenId);
        if (rst.next()){
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    Integer.parseInt(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    Integer.parseInt(rst.getString(5))
            );
        }else {
            return null;
        }
    }


    @Override
    public boolean updateStock(String itemCode, int customerQTY, Connection connection) throws SQLException, ClassNotFoundException {
        int qyOnHand = search(itemCode,connection).getQtyOnHand();
        return CrudUtil.executeUpdate(connection,"UPDATE Item SET qtyOnHand = ? WHERE itemCode = ?",qyOnHand-customerQTY,itemCode);
    }
}
