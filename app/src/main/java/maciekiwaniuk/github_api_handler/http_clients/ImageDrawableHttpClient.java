package maciekiwaniuk.github_api_handler.http_clients;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Class which contains a bunch of static methods.
 */
final public class ImageDrawableHttpClient {
    private Drawable image;

    public ImageDrawableHttpClient(String url) {
        try {
            InputStream inputStream = (InputStream) new URL(url).getContent();

            this.image = Drawable.createFromStream(inputStream, "src name");
        } catch (Exception e) {
            this.image = null;
        }
    }

    /**
     * Returns image as Drawable from URL.
     */
    public Drawable getImage() {
        return this.image;
    }

    /**
     * Resizes Drawable image.
     */
    public Drawable getResizedImage(int size, Resources resources) {
        Bitmap bitmap = ((BitmapDrawable)this.image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, size, size, false);
        return new BitmapDrawable(resources, bitmapResized);
    }
}
