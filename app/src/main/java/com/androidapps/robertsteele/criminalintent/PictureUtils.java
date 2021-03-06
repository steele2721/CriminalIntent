package com.androidapps.robertsteele.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class PictureUtils {

    public static Bitmap getScaledBitMap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float scrHeight = options.outHeight;

        int inSampleSize = 1;
        if(scrHeight > destHeight || srcWidth > destWidth) {
            float heightScale = scrHeight / destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);

        }
            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitMap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitMap(path, size.x, size.y);
    }
}
