package bussiness;


import bussiness.custom.impl.CustomerBOImpl;
import bussiness.custom.impl.ItemBOImpl;
import bussiness.custom.impl.PlaceOrderBOImpl;

public class BOFactory {

        private static BOFactory boFactory;

        private BOFactory(){

        }

        public static BOFactory getBOFactory(){
            if (boFactory==null){
                boFactory = new BOFactory();
            }
            return boFactory;
        }

        public SuperBO getBO(BOTypes types){
            switch (types){
                case CUSTOMER:
                    return new CustomerBOImpl();

                case ITEM:
                    return new ItemBOImpl();

                case PLACEORDER:
                    return new PlaceOrderBOImpl();

                default:
                    return null;

            }
        }

        public enum BOTypes{
           CUSTOMER,ITEM,PLACEORDER
        }

}
