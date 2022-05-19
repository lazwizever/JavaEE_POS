package bussiness.custom;

import bussiness.SuperBO;
import dto.ItemDTO;

import java.sql.Connection;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO item, Connection connection);

}
