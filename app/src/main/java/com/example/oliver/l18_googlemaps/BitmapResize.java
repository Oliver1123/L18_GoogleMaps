package com.example.oliver.l18_googlemaps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by oliver on 08.09.15.
 */
public class BitmapResize {

    /**
     * Decode given byte array into Bitmap with given sizes
     * @param data source byte array
     * @param reqWidth image width
     * @param reqHeight image height
     * @return Bitmap
     */
    public static Bitmap decodeBitmapFromByteArray(byte[] data, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        Log.d(Constants.TAG, "Image required width: " + reqWidth + ", height: " + reqHeight);
        Log.d(Constants.TAG, "Image before width: " + options.outWidth + ", height: " + options.outHeight);
        // Calculate new inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        Log.d(Constants.TAG, "Image after width: " + bitmap.getWidth()+ ", height: " + bitmap.getHeight());
        String result = String.format("decodeBitmapFromByteArray size: %4s KB", (bitmap.getByteCount() / 1024));
        Log.d(Constants.TAG, result);
        return bitmap;
    }

    public static Bitmap decodeBitmapFromUri(Context context, String uri, int reqWidth, int reqHeight) throws IOException {

        InputStream is = context.getContentResolver().openInputStream(Uri.parse(uri));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (is.read(buffer) != -1 ) {
            bos.write(buffer);
        }
        return decodeBitmapFromByteArray(bos.toByteArray(), reqWidth, reqHeight);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Real bitmap size
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate new inSampleSize
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
