package com.sjl.yuehu.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class CameraGalleryUtil {

    public static final int HEAD_IMG_SIZE = 256;
    public static final int PRODUCT_INVOICE_SIZE = 720;

    public static final int IDCARD_IMG_WIDTH = 428;
    public static final int IDCARD_IMG_HEIGHT = 270;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int REQUEST_CODE_CROP = 3;

    File file;
    Uri imageUri = null;
    Uri corpUri = null;

    Context context;
    String dateString;

    public CameraGalleryUtil(Context context) {
        this(context, StringUtil.Date2String());
    }

    public CameraGalleryUtil(Context context, String dateString) {
        this.context = context;
        this.dateString = dateString;

        file = new File(CacheUtil.getImageCacheDir(context) + File.separator + dateString + ".jpg");

        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        imageUri = Uri.fromFile(file);
    }

    public Intent cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

    public Intent cropIntent(boolean keepRatio,
                             int aspectX,
                             int aspectY,
                             int outputX,
                             int outputY) {
        file = new File(CacheUtil.getImageCacheDir(context) + File.separator + dateString + "-copy.jpg");
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        corpUri = Uri.fromFile(file);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        if (keepRatio) {
            if (android.os.Build.MODEL.contains("HUAWEI")) {
                intent.putExtra("aspectX", 9998);
                intent.putExtra("aspectY", 9999);
            } else {
                intent.putExtra("aspectX", aspectX);
                intent.putExtra("aspectY", aspectY);
            }
        }
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, corpUri);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public Uri getCorpUri() {
        return corpUri;
    }

    public Intent galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    public boolean saveBitmapFromUri(Uri uri) {
        try {
            Bitmap bitmap = BitmapUtil.getBitmapFormUri(context, uri);
            if (bitmap == null) return false;

            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bout = new BufferedOutputStream(fout, 1024 * 8);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bout);
            try {
                bout.flush();
                bout.close();
                fout.close();
                bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Intent saveBitmapFromUri(Uri uri, boolean keepRatio, int aspectX, int aspectY, int outputX, int outputY) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtil.getBitmapFormUri(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveBitmap(bitmap, keepRatio, aspectX, aspectY, outputX, outputY);
    }

    private Intent saveBitmap(Bitmap bitmap, boolean keepRatio, int aspectX, int aspectY, int outputX, int outputY) {
        if (bitmap == null) return null;

        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            fout = new FileOutputStream(file);
            bout = new BufferedOutputStream(fout, 1024 * 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bout);
        try {
            bout.flush();
            bout.close();
            fout.close();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cropIntent(keepRatio, aspectX, aspectY, outputX, outputY);
    }


}
