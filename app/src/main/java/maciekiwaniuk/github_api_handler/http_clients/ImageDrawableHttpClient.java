package maciekiwaniuk.github_api_handler.http_clients;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Fetches image by URL and transforms it to Drawable.
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
     * Returns Drawable image.
     */
    public Drawable getImage() {
        return this.image;
    }

    /**
     * Returns resized Drawable image.
     */
    public Drawable getResizedImage(int size, Resources resources) {
        Bitmap bitmap = ((BitmapDrawable)this.getImage()).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(bitmap, size, size, false);
        return new BitmapDrawable(resources, bitmapResized);
    }
}
