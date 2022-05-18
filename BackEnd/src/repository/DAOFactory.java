package repository;

import repository.custom.impl.CustomerDAOImpl;

public class DAOFactory {

        private static DAOFactory daoFactory;

        private DAOFactory(){

        }

        public static DAOFactory getDaoFactory(){
            return daoFactory==null? daoFactory = new DAOFactory() : daoFactory;
        }


        public  SuperDAO  getDAO(DAOTypes types){
            switch (types){
               /* case ITEM:
                    return new ItemDAOImpl();
                case ORDER:
                    return new PlaceOrderDAOImpl();*/
                case CUSTOMER:
                    return new CustomerDAOImpl();
                default:
                    return null;
            }
        }

    public  enum  DAOTypes{
        CUSTOMER
    }



}
