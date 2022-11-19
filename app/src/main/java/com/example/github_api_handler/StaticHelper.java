package com.example.github_api_handler;

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
     * @return
     */
    public static Drawable loadImageDrawableFromUrl(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
