package com.sjl.yuehu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * 作用：缓存工具类
 */
public class CachUtilsSp {
    /**
     * 保持数据
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        //判断sdcard是否存在
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sdcard可用
            try {
                String fileName = key;//文件的名称
                String dir = Environment.getExternalStorageDirectory()+"/xiaolu";
                File file = new File(dir,fileName);//文件
                File parentFile = file.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();//创建多级目录
                }

                if(!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(value.getBytes());
                fos.flush();
                fos.close();;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            SharedPreferences sp  = context.getSharedPreferences("xiaolu",Context.MODE_PRIVATE);
            sp.edit().putString(key,value).commit();
        }


    }

    /**
     * 得到缓存数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sdcard可用
            try {
                String fileName = key;//文件的名称
                String dir = Environment.getExternalStorageDirectory()+"/xiaolu";
                File file = new File(dir,fileName);//文件
                byte[] buffer = new byte[1024];
                int length;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileInputStream fos = new FileInputStream(file);
                while ((length = fos.read(buffer))!=-1){
                    baos.write(buffer,0,length);
                }

                fos.close();
                baos.flush();
                baos.close();
                return  baos.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }else{
            SharedPreferences sp  = context.getSharedPreferences("xiaolu",Context.MODE_PRIVATE);
            return sp.getString(key,"");
        }

    }

    public static void putBoolean(Context context, String key, boolean values) {
        SharedPreferences sp  = context.getSharedPreferences("xiaolu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,values).commit();
    }

    public static boolean getBoolean(Context context,String key) {
        SharedPreferences sp  = context.getSharedPreferences("xiaolu",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
}