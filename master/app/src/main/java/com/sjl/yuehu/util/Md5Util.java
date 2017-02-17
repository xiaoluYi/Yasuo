package com.sjl.yuehu.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private static final String MD5_KEY = "xinanli!_3o12";

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        return md5(text, MD5_KEY);
    }

    /**
     * 对字符串进行MD5加密，可指定加密KEY
     *
     * @param text
     * @param key
     * @return
     */
    public static String md5(String text, String key) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        msgDigest.update(text.getBytes());
        byte[] bytes = msgDigest.digest();
        byte tb;
        char low;
        char high;
        char tmpChar;
        String md5Str = new String();
        for (int i = 0; i < bytes.length; i++) {
            tb = bytes[i];
            tmpChar = (char) ((tb >>> 4) & 0x000f);
            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }
            md5Str += high;
            tmpChar = (char) (tb & 0x000f);
            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }
            md5Str += low;
        }
        return md5Str.toUpperCase();
    }

    public static String getPayPassword(String payPassword) {
        if (StringUtil.isEmpty(payPassword))
            return null;
        return md5(payPassword);
    }

    public static String getLoginPassword(String password) {
        if (StringUtil.isEmpty(password))
            return null;
        return md5(md5(password));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("123456 = " + md5("a123456"));
    }
}
