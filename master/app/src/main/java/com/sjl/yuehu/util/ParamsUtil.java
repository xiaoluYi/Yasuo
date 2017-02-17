package com.sjl.yuehu.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sjl.yuehu.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by simon on 2016/4/21.
 */
public class ParamsUtil {

    public static final String LABEL_DISPLAY = "display";

    /**
     * 二维码类型
     */
    public static final String LABEL_QR_TYPE = "QRTYPE";
    public static final int QR_TYPE_VOUCHER_GIVE = 1;//带保证赠送
    public static final int QR_TYPE_ACCOUNT = 2;//账户二维码
    public static final int QR_TYPE_SHARE = 3;//账户二维码
    public static final int QR_TYPE_MERCHANT_FOR_PAY = 4;//账户二维码
    public static final int QR_TYPE_MERCHANT_GROUP = 5;//群组二维码

    /**
     * 数据库表名
     */
    public static final String ENTRY_KEY_USER = "user";


    /**
     * 待报证赠送方式
     */
    public static final String VOUCHER_GIVE_MANNER_QR = "1";
    public static final String VOUCHER_GIVE_MANNER_CHOICE = "2";

    /**
     * 是否公转
     */
    public static final String VOUCHER_ISPUBLIC_NO = "0";//否
    public static final String VOUCHER_ISPUBLIC_YES = "1";//是

    /**
     * 是否全部提交排队
     */
    public static final String VOUCHER_QUEUE_ALL_NO = "0";//否
    public static final String VOUCHER_QUEUE_ALL_YES = "1";//是


    /**
     * 自定义排队状态
     */
    public static final String VOUCHER_CUSTOM_STATUS_NO = "0";//未排队
    public static final String VOUCHER_CUSTOM_STATUS_ING = "1";//排队中
    public static final String VOUCHER_CUSTOM_STATUS_COMPLETE = "2";//已完成


    /**
     * 返回一个Bundle
     *
     * @param display
     * @return
     */
    public static Bundle getDisplayArgs(int display) {
        Bundle bundle = new Bundle();
        bundle.putInt(LABEL_DISPLAY, display);
        return bundle;
    }


    /**
     * 生成QR内容
     *
     * @param qrType
     * @param args
     * @return
     */
    public static String createUrlQr(String url, int qrType, String... args) throws Exception {
        String qrStr = url + "?" + LABEL_QR_TYPE + "=" + AESUtil.encrypt(AndroidBase64Util.encode((String.valueOf(qrType))));
        for (int i = 0; i < args.length; i = i + 2)
            qrStr += "&" + args[i] + "=" + AESUtil.encrypt(AndroidBase64Util.encode((args[i + 1])));
        return qrStr;
    }

    /**
     * 外链跳转QR内容
     *
     * @param qrType
     * @param args
     * @return
     * @throws Exception
     */
    public static String createSchemeQr(int qrType, String... args) throws Exception {
        return createUrlQr("yugang://www.yg123.net/direction", qrType, args);
    }

    /**
     * 解析URL行QR文本
     *
     * @param qrStr
     * @return
     */
    public static Map<String, String> parseUrlQr(String qrStr) throws Exception {
        if (StringUtil.isEmpty(qrStr))
            return null;

        int indexAsk = qrStr.indexOf("?");
        String url = qrStr.substring(0, indexAsk + 1);

        Map<String, String> map = new HashMap<>();
        map.put("url", url);

        String params = qrStr.substring(indexAsk + 1);
        String args[] = params.split("&");
        for (String kv : args) {
            int indexEqual = kv.indexOf("=");
            map.put(kv.substring(0, indexEqual), AndroidBase64Util.decode(AESUtil.decrypt(kv.substring(indexEqual + 1))));
        }
        return map;
    }

    /**
     * 生成QR内容
     *
     * @param qrType
     * @param args
     * @return
     */
    public static String createJsonQr(int qrType, String... args) {
        JsonObject jsonParent = new JsonObject();
        jsonParent.addProperty(LABEL_QR_TYPE, qrType);

        JsonObject jsonContent = new JsonObject();
        for (int i = 0; i < args.length; i = i + 2)
            jsonContent.addProperty(args[i], args[i + 1]);

        String content;
        try {
            content = AESUtil.encrypt(jsonContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            content = "";
        }
        jsonParent.addProperty("content", content);

        return jsonParent.toString();
    }

    /**
     * 解析JSON QR
     *
     * @param context
     * @param gson
     * @param content
     * @return
     */
    public static JsonObject parseJsonQr(Context context, Gson gson, String content) {
        JsonObject jsonObject = null;
        try {
            jsonObject = gson.fromJson(content, JsonObject.class);
            String realContent = AESUtil.decrypt(jsonObject.get("content").getAsString());
            jsonObject.add("content", gson.fromJson(realContent, JsonObject.class));
        } catch (Exception e) {
            showQrContent(context, content);
        }
        return jsonObject;
    }

    private static void showQrContent(Context context, String content) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("扫描内容")
                .setMessage(content)
                .setPositiveButton("复制", (dialog, which) -> {
                    dialog.dismiss();
                    StringUtil.copy(context, content);
                })
                .setNegativeButton("关闭", (dialog, which) -> {
                    dialog.dismiss();
                }).create();
        alertDialog.show();
    }

    /**
     * 生成文件参数
     *
     * @param file
     * @param progressListener
     * @return
     */
//    public static DefaultConfig.FileRequestBody genFileParams(File file, DefaultConfig.ProgressListener progressListener) {
//        return new DefaultConfig.FileRequestBody(file, progressListener);
//    }

    /**
     * 参数加密 并进行BASE64字段值加密
     *
     * @param args
     * @return
     */
    public static String genParams(String... args) {
        return genParams(true, args);
    }

    /**
     * Aes加密参数
     *
     * @param base64 是否把字段值进行base64加密
     * @param args   需要加密的字段和值
     * @return
     */
    public static String genParams(boolean base64, String... args) {
        JsonObject jsonObject = new JsonObject();

        for (int i = 0; i < args.length - 1; i = i + 2) {
            if (base64) {
                String val = StringUtil.isEmpty(args[i + 1]) ? "" : AndroidBase64Util.encode(args[i + 1]);
                jsonObject.addProperty(args[i], val);
            } else
                jsonObject.addProperty(args[i], args[i + 1]);
        }

        String para = null;
        try {
            para = AESUtil.encrypt(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return para;
    }

    /**
     * 把JSON进行AES加密
     *
     * @param json
     * @return
     */
    public static String genParams(String json) {
        String para = null;
        try {
            para = AESUtil.encrypt(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return para;
    }

    /**
     * 解密AES密文为JSON
     *
     * @param aesString
     * @return
     */
    public static String denParams(String aesString) {
        String json = null;
        try {
            json = AESUtil.decrypt(aesString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 解密AES密文为JSON,密文字段进行过base64加密
     *
     * @param param
     * @return
     * @throws Exception
     */
    public static String miwJiema(String param) throws Exception {
        JSONObject json = new JSONObject();
        try {
            String plainText = AESUtil.decrypt(param);
            JSONObject jsonObject = new JSONObject(plainText);
            Iterator<?> it = jsonObject.keys();
            while (it.hasNext()) {
                String t_key = (String) it.next();
                if (t_key.equals("code")) {
                    //json.put(t_key, jsonObject.get(t_key)); hnh 0426
                    json.put(t_key, AndroidBase64Util.decode((String) jsonObject.get(t_key)));
                    continue;
                }
                Object t_obj = jsonObject.get(t_key);
                if (t_obj == null) {
                    json.put(t_key, "");
                    continue;
                }
                if (t_obj instanceof String) {
                    if (t_obj != null && ((String) t_obj).length() > 0) {
                        json.put(t_key, AndroidBase64Util.decode(((String) t_obj)));  //Base64解码后放入JSONObject
                    } else {
                        json.put(t_key, "");
                    }
                } else if (t_obj instanceof JSONArray) {
                    // 处理包含的List
                    json.put(t_key, parseJSONListDecoder((JSONArray) t_obj));
                } else if (t_obj instanceof JSONObject) {
                    json.put(t_key, parseJSONObjectDecoder((JSONObject) t_obj));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        if (BuildConfig.DEBUG)
//            Timber.e("returnJson=" + json.toString());
        return json.toString();
    }

    private static JSONArray parseJSONListDecoder(JSONArray t_list) throws Exception {
        JSONArray jsonArray = new JSONArray();
        if (t_list != null) {
            int size = t_list.length();
            for (int i = 0; i < size; i++) {
                Object t_obj = t_list.get(i);
//            }
//            for (Object t_obj : t_list) {
                if (t_obj == null) continue;
                if (t_obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) t_obj;
                    Iterator<?> it = jsonObject.keys();
                    JSONObject json = new JSONObject();
                    while (it.hasNext()) {
                        String t_key = (String) it.next();
                        if (jsonObject.get(t_key) instanceof String) {
                            if (jsonObject.get(t_key) != null && ((String) jsonObject.get(t_key)).length() > 0) {
                                json.put(t_key, AndroidBase64Util.decode(((String) jsonObject.get(t_key))));  //Base64解码后放入Map
                            } else {
                                json.put(t_key, "");
                            }
                        } else if (jsonObject.get(t_key) instanceof JSONArray) {
                            // 处理包含的List
                            json.put(t_key, parseJSONListDecoder((JSONArray) jsonObject.get(t_key)));
                        } else if (jsonObject.get(t_key) instanceof JSONObject) {
                            json.put(t_key, parseJSONObjectDecoder((JSONObject) jsonObject.get(t_key)));
                        }
                    }
                    jsonArray.put(json);
                } else {
                    jsonArray.put(AndroidBase64Util.decode(t_obj.toString()));
                }
            }
        }
        return jsonArray;
    }

    private static JSONObject parseJSONObjectDecoder(JSONObject jsonObject) throws Exception {
        JSONObject json = new JSONObject();
        Iterator<?> it = jsonObject.keys();
        while (it.hasNext()) {
            String t_key = (String) it.next();
            if (jsonObject.get(t_key) == null) {
                json.put(t_key, "");
                continue;
            }
            if (jsonObject.get(t_key) instanceof String) {
                if (jsonObject.get(t_key) != null && ((String) jsonObject.get(t_key)).length() > 0) {
                    json.put(t_key, AndroidBase64Util.decode(((String) jsonObject.get(t_key))));  //Base64解码后放入Map
                } else {
                    json.put(t_key, "");
                }
            } else if (jsonObject.get(t_key) instanceof JSONArray) {
                // 处理包含的List
                json.put(t_key, parseJSONListDecoder((JSONArray) jsonObject.get(t_key)));
            } else if (jsonObject.get(t_key) instanceof JSONObject) {
                json.put(t_key, parseJSONObjectDecoder((JSONObject) jsonObject.get(t_key)));
            }
        }
        return json;
    }
}
