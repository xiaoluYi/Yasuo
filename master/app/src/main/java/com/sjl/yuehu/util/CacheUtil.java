package com.sjl.yuehu.util;

import android.content.Context;
import android.os.Environment;

import com.sjl.yuehu.R;

import java.io.File;

/**
 * Created by simon on 2016/3/14.
 */
public class CacheUtil {

    public static File getHttpCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static String getImageCacheDir(Context context) {
        String path = getDiskCacheDir(context) + File.separator + "image" + File.separator;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return file.getPath();
    }

    public static String getDiskCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) // Environment.MEDIA_MOUNTED 手机装有SDCard,并且可以进行读写
                || !Environment.isExternalStorageRemovable()) {  //Environment.getExtemalStorageState() 获取SDcard的状态
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    public static String getDiskFileDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return context.getExternalFilesDir(null).getPath();
        } else {
            return context.getFilesDir().getPath();
        }
    }

    public static String getDiskDownloadDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getString(R.string.app_name) + File.separator;
            File file = new File(path);
            if (!file.exists())
                file.mkdir();
            return file.getPath();
        } else {
            return null;
        }
    }
}
