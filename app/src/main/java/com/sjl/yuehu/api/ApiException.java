package com.sjl.yuehu.api;

/**
 * Created by 小鹿 on 2017/2/8.
 */
public class ApiException  extends Exception {

    private final int code;
    private String displayMessage;

    public static final int UNKNOWN = 1000;
    public static final int PARSE_ERROR = 1001;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        setDisplayMessage(throwable.getMessage());
    }

    public int getCode() {
        return code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg;
    }
}
