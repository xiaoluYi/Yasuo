package com.sjl.yuehu.api;

/**
 * Created by 小鹿 on 2017/2/8.
 */
public class ResultException extends RuntimeException {

    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}