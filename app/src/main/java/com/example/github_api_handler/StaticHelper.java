package com.example.github_api_handler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Class which contains a bunch of static methods.
 */
final public class StaticHelper {
    private StaticHelper() {}

    /**
     * Returns image as Drawable from URL.
     *
     * @param url
     * @return - fetched Drawable image from passed URL
     */
    public static Drawable loadImageDrawableFromUrl(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(is, "src name");
            return drawable;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Resizes Drawable image.
     *
     * @param image
     * @param size
     * @param resources
     * @return
     */
    public static Drawable resizeImageDrawable(Drawable image, int size, Resources resources) {
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, size, size, false);
        return new BitmapDrawable(resources, bitmapResized);
    }

}
