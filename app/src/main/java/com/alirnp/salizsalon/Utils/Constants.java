package com.alirnp.salizsalon.Utils;

public class Constants {

    public static final String POSTS = "POSTS";
    public static final String CATEGORY = "CATEGORY";
    public static String TIMES = "TIMES";

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
    public static String EDIT = "EDIT";
    public static String CHECK_ADMIN = "CHECK_ADMIN";
    public static String USER_RESERVE_LIST = "USER_RESERVE_LIST";

    public static String FIRST_NAME = "FIRST_NAME";
    public static String LAST_NAME = "LAST_NAME";
    public static String PHONE = "PHONE";
    public static String NEW_PHONE = "NEW_PHONE";
    public static String PASSWORD = "PASSWORD";
    public static String LEVEL = "LEVEL";

    public static String SHARED_PREF_NAME = "sharedPrefName";

    public static String INTERFACE_ON_LOGIN_USER = "onLoginUser";
    public static String EVENT_LOGIN = "EVENT_LOGIN";
    public static String EVENT_RESERVED = "EVENT_RESERVED";

    public static String NEW_COMER_PER = "تازه وارد";
    public static String NORMAL_PER = "کاربر عادی";
    public static String GOLD_PER = "کاربر طلایی";
    public static String ADMIN_PER = "مدیر کل";

    public enum user_level {
        NEW_COMER("NEW_COMER"),
        NORMAL("NORMAL"),
        GOLD("GOLD"),
        ADMIN("ADMIN");

        private String lvl;

        user_level(String lvl) {
            this.lvl = lvl;
        }

        public String getLevel() {
            return lvl;
        }
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

        private int status;

        state(int status) {
            this.status = status;
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
