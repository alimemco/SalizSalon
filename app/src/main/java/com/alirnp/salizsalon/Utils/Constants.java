package com.alirnp.salizsalon.Utils;

public class Constants {

    public static String TIMES = "times";

    public static String DAY_NAME = "DAY_NAME";
    public static String MONTH_NAME = "MONTH_NAME";
    public static String DAY_OF_MONTH = "DAY_OF_MONTH";
    public static String PRICE = "PRICE";
    public static String RESERVE = "RESERVE";

    public static String POSITION = "POSITION";
    public static String IMAGE_LIST = "IMAGE_LIST";
    public static String DAY = "DAY";
    public static String HOUR = "HOUR";
    public static String SERVICES = "SERVICES";

    public static String REGISTER = "REGISTER";
    public static String LOGIN = "LOGIN";
    public static String USER_RESERVE_LIST = "USER_RESERVE_LIST";

    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String PHONE = "phone";
    public static String PASSWORD = "password";

    public static String SHARED_PREF_NAME = "sharedPrefName";

    public static String INTERFACE_ON_LOGIN_USER = "onLoginUser";
    public static String EVENT_LOGIN = "EVENT_LOGIN";
    public static String EVENT_RESERVED = "EVENT_RESERVED";


    public enum resultMap {
        DAY_NAME,
        MONTH_NAME,
        DAY_OF_MONTH,
        HOUR
    }

    public enum data {
        DAY,
        HOUR,
        SERVICES
    }

    public enum statusReserve {
        PENDING("pending"),
        FINALIZED("finalized"),
        DENIED("denied"),
        DONE("done");

        private String status;

        statusReserve(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
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

    public enum fragmentToShow {
        HOME(1),
        USER(2),
        ORDER(3);

        private int status;

        fragmentToShow(int status) {
            this.status = status;
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
