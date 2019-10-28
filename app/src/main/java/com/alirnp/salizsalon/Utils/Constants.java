package com.alirnp.salizsalon.Utils;

public class Constants {

    public static String TIMES = "times";

    public static String POSITION = "POSITION";
    public static String IMAGE_LIST = "IMAGE_LIST";
    public static String DAY = "DAY";
    public static String HOUR = "HOUR";
    public static String SERVICES = "SERVICES";

    public enum resultMap {
        DAY_NAME,
        MONTH_NAME,
        DAY_OF_MONTH,
        HOUR
    }

    public enum state {
        SUCCESS(1),
        ITEM_NOT_FOUND(2),
        SEARCHING(3);

        private int status ;

        state(int status) {
            this.status = status ;
        }

        public int getStatus() {
            return status;
        }
    }


    public enum viewType {
        TIME(1),
        SERVICES(2);

        private int viewType;

        viewType(int viewType) {
            this.viewType = viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

}
