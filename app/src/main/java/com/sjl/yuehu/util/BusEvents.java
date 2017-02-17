package com.sjl.yuehu.util;

public class BusEvents {

    public static final String EVENT_EXIT_SYSTEM = "EVENT_EXIT_SYSTEM";

    public static final String EVENT_GIVE_VOUCHER = "EVENT_GIVE_VOUCHER";

    public static final String EVENT_MODIFY_HEAD = "EVENT_MODIFY_HEAD";

    public static final String EVENT_SHOP_APPLY_ADDRESS = "EVENT_SHOP_APPLY_ADDRESS";
    public static final String EVENT_SHOP_APPLY_HEAD = "EVENT_SHOP_APPLY_HEAD";
    public static final String EVENT_SHOP_APPLY_LOGO = "EVENT_SHOP_APPLY_LOGO";
    public static final String EVENT_SHOP_APPLY_SIGNATURE = "EVENT_SHOP_APPLY_SIGNATURE";
    public static final String EVENT_SHOP_APPLY_LICENSE = "EVENT_SHOP_APPLY_LICENSE";
    public static final String EVENT_SHOP_APPLY_LETTER = "EVENT_SHOP_APPLY_LETTER";
    public static final String EVENT_SHOP_APPLY_BUSINESS_LICENSE = "EVENT_SHOP_APPLY_BUSINESS_LICENSE";
    public static final String EVENT_SHOP_APPLY_CERTIFICATE = "EVENT_SHOP_APPLY_CERTIFICATE";
    public static final String EVENT_SHOP_APPLY_GALLERY = "EVENT_SHOP_APPLY_GALLERY";
    public static final String EVENT_SHOP_APPLY_OTHER = "EVENT_SHOP_APPLY_OTHER";

    public static final String EVENT_SHOP_GROUP_CREATE = "EVENT_SHOP_GROUP_CREATE";
    public static final String EVENT_SHOP_GROUP_INFO_EDIT = "EVENT_SHOP_GROUP_INFO_EDIT";
    public static final String EVENT_SHOP_GROUP_INFO_EDIT_MENU_YES = "EVENT_SHOP_GROUP_INFO_EDIT_MENU_YES";
    public static final String EVENT_SHOP_GROUP_INFO_EDIT_MENU_NO = "EVENT_SHOP_GROUP_INFO_EDIT_MENU_NO";

    public static final String EVENT_MERCHANT_CITY_PICKER = "EVENT_MERCHANT_CITY_PICKER";
    public static final String EVENT_MERCHANT_CITY_PICKER_RESULT = "EVENT_MERCHANT_CITY_PICKER_RESULT";

    private String event;

    private Object obj;

    public BusEvents(String event) {
        this.event = event;
    }

    public BusEvents(String event, Object obj) {
        this.event = event;
        this.obj = obj;
    }

    public String getEvent() {
        return event;
    }

    public Object getObj() {
        return obj;
    }
}
