package com.example.github_api_handler;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.github_api_handler.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private GithubApiUserData userData;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        assignEvents();
    }

    /**
     *
     */
    protected void assignEvents() {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(event -> {
            try {
                tryToFindUser();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     */
    protected void tryToFindUser() throws Exception {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        String username = usernameText.getText().toString();

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        this.userData = new GithubApiUserData(username);
        this.binding.setUserData(this.userData);
        
        try {
            this.userData.loadingData = true;
            this.userData.fetchData();

            Drawable avatarDrawable = StaticHelper.loadImageDrawableFromUrl(this.userData.avatarUrl);
            this.userData.avatarDrawable = this.resize(avatarDrawable, 350);

        } catch (Exception e) {
            e.printStackTrace();
            this.userData.userNotFoundError = false;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            this.userData.loadingData = false;

            this.userData.readyToDisplay = true;

            this.binding.setUserData(this.userData);
        }, 1000);
    }

    private Drawable resize(Drawable image, int size) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size, size, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

}