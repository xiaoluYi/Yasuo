package com.sjl.yuehu.util;

import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.UUID;

public class YunMaiUtil {

    //获取版本号
    private static String OSVersion = android.os.Build.VERSION.RELEASE;
    // 获取手机型号
    private static String mxh = android.os.Build.MODEL;
    //访问服务器地址
    public static final String ENGINE_URL = "http://www.yunmaiocr.com/SrvXMLAPI";
    public static final String USERNAME = "84653ec2-5ab9-4952-8144-d6429a6297f5";//开发者平台API帐号
    public static final String PASSWORD = "oWWklddDkkDHoLxNIxkyGASxLAWCgf";//开发者平台API密码

    public static ArrayList<String[]> getArray(String action, String ext) {
        ArrayList<String[]> arr = new ArrayList<>();
        String key = UUID.randomUUID().toString();
        String time = String.valueOf(System.currentTimeMillis());
        String verify = MD5(action + USERNAME + key + time + PASSWORD);
        arr.add(new String[]{"action", action});
        arr.add(new String[]{"client", USERNAME});
        arr.add(new String[]{"system", OSVersion + mxh});
        arr.add(new String[]{"password", MD5(PASSWORD)});
        arr.add(new String[]{"key", key});
        arr.add(new String[]{"time", time});
        arr.add(new String[]{"verify", verify});
        arr.add(new String[]{"ext", ext});
        return arr;
    }

    public static byte[] getPara(ArrayList<String[]> arr, byte[] file) {
        String xml = getXML(arr, false);
        byte[] dest = new byte[xml.getBytes().length + file.length + "<file></file>".getBytes().length];
        int pos = 0;
        System.arraycopy(xml.getBytes(), 0, dest, pos, xml.getBytes().length);
        pos += xml.getBytes().length;
        System.arraycopy("<file>".getBytes(), 0, dest, pos, "<file>".getBytes().length);
        pos += "<file>".getBytes().length;
        System.arraycopy(file, 0, dest, pos, file.length);
        pos += file.length;
        System.arraycopy("</file>".getBytes(), 0, dest, pos, "</file>".getBytes().length);
        return dest;
    }

    public static void doHttp() {

    }

    private final static String MD5(String pwd) {
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = pwd.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            return new String(str);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getXML(ArrayList<String[]> arr, boolean IsUpper) {
        if (arr == null || arr.size() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        String tag;
        for (int i = 0; i < arr.size(); i++) {
            tag = arr.get(i)[0];
            if (IsUpper) {
                tag = tag.toUpperCase();
            }
            sb.append("<");
            sb.append(tag);
            sb.append(">");
            sb.append(arr.get(i)[1]);
            sb.append("</");
            sb.append(tag);
            sb.append(">");
        }
        return sb.toString();
    }
}
