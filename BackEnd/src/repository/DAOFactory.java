package repository;

import repository.custom.impl.CustomerDAOImpl;
import repository.custom.impl.ItemDAOImpl;
import repository.custom.impl.OrderDAOImpl;

public class DAOFactory {

        private static DAOFactory daoFactory;

        private DAOFactory(){

        }

        public static DAOFactory getDaoFactory(){
            return daoFactory==null? daoFactory = new DAOFactory() : daoFactory;
        }


        public  SuperDAO  getDAO(DAOTypes types){
            switch (types){
                case ITEM:
                    return new ItemDAOImpl();
                /*case ORDER:
                    return new OrderDAOImpl();*/
                case CUSTOMER:
                    return new CustomerDAOImpl();
                default:
                    return null;
            }
        }

    public  enum  DAOTypes{
        CUSTOMER,ITEM,ORDER
    }



}
