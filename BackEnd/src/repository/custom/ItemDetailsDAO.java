package repository.custom;

import entity.ItemDetails;
import repository.CrudDAO;

import java.sql.Connection;

public interface ItemDetailsDAO extends CrudDAO<ItemDetails,String> {
   /* static boolean add(ItemDetails orderItem, Connection connection) {
        return false;
    }*/
}
