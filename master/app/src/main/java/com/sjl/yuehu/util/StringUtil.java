package com.sjl.yuehu.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by simon on 2016/3/15.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null) return true;
        if (str.trim().equals("")) return true;
        return false;
    }

    public static boolean isEmpty(EditText et) {
        return isEmpty(getText(et));
    }

    public static boolean isEmpty(TextView tv) {
        return isEmpty(getText(tv));
    }

    public static String getText(EditText editText) {
        if (editText == null) return "";
        return editText.getText().toString();
    }

//    public static String getText(SwitchButton switchButton) {
//        return switchButton.isChecked() ? "1" : "0";
//    }

    public static String getText(TextView textView) {
        if (textView == null) return null;
        return textView.getText().toString();
    }

    public static String getSecurityMobile(String mobile) {
        if (isEmpty(mobile)) return null;
        int length = mobile.length();
        if (length == 11) {
            String begin = mobile.substring(0, 3);
            String end = mobile.substring(8);
            return begin + "*****" + end;

        } else
            return mobile;
    }

    public static String groupMobile(String mobile) {
        if (isEmpty(mobile)) return null;
        char[] arr = mobile.toCharArray();
        StringBuilder sb_mobile = new StringBuilder();
        if (arr.length == 11) {
            sb_mobile.append(arr[0]).append(arr[1]).append(arr[2]);
            sb_mobile.append(" ").append(arr[3]).append(arr[4]).append(arr[5]).append(arr[6]);
            sb_mobile.append(" ").append(arr[7]).append(arr[8]).append(arr[9]).append(arr[10]);
            return sb_mobile.toString();
        }
        return mobile;
    }

    public static String getValue(String string) {
        return getValue(string, "");
    }

    public static String getValue(String string, String defaultVal) {
        return isEmpty(string) ? defaultVal : string;
    }

    public static String getMoney(String string) {
        return formatPrice(getValue(string, "0.00"));
    }

    public static double getFloatVal(String string) {
        return Double.parseDouble(getValue(string, "0"));
    }

    public static String getInt(String string) {
        return getValue(string, "0");
    }

    public static int getIntVal(String string) {
        return Integer.parseInt(getValue(string, "0"));
    }

    public static String formatPrice(String price) {
        return formatPrice(Double.parseDouble(price), 2);
    }

    public static String formatPrice(double price) {
        return formatPrice(price, 2);
    }

    public static String formatPrice(double price, int scale) {
        BigDecimal bigDecimal = new BigDecimal(price).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toPlainString();

    }

    public static String Date2String() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 手机号是否合法
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 其他: 177,178,179
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobile(String mobile) {
        if (StringUtil.isEmpty(mobile))
            return false;

        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (isEmpty(mobile))
            return false;
        else
            return mobile.matches(telRegex);
    }

    /**
     * 邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtil.isEmpty(email))
            return false;

        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str))
            return false;

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static void copy(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText("text", content);
        clipboardManager.setPrimaryClip(clipData);
    }

    public static double sum(String... floats) {
        BigDecimal decimal = new BigDecimal(0);
        for (String f : floats) {
            decimal = decimal.add(new BigDecimal(String.valueOf(f)));
        }

        return decimal.doubleValue();
    }
}
