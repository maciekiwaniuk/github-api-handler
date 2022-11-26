package com.example.github_api_handler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(inputStream, "src name");
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

    /**
     * Fetches data from passed URL and returns it as String.
     *
     * @param URL
     * @return
     * @throws Exception
     */
    public static String fetchDataFromUrlAsString(String URL) throws Exception {
        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }

        input.close();

        return response.toString();
    }

}
