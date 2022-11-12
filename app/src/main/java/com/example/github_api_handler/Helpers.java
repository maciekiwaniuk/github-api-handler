package com.example.github_api_handler;

import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

final public class Helpers {
    private Helpers() {}

    /**
     *
     * @param url
     * @return
     */
    public static Drawable loadImageFromUrl(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
