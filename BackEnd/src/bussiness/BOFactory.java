package bussiness;


import bussiness.custom.impl.CustomerBOImpl;
import bussiness.custom.impl.ItemBOImpl;

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

                /*case ORDER:
                    return new OrderBOImpl();
                case PURCHASE_ORDER:
                    return new PurchaseOrderBOImpl();
                case SYSTEM_REPORTS:
                    return new SystemReportsBOImpl();*/
                default:
                    return null;

            }
        }

        public enum BOTypes{
           CUSTOMER,ITEM
        }

}
