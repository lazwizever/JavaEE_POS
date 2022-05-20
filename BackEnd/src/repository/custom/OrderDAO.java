package repository.custom;

import entity.Order;
import repository.CrudDAO;

public interface OrderDAO extends CrudDAO<Order,String> {
    boolean add(Order order);
}
