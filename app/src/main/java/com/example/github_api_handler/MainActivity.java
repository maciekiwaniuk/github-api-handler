package com.example.github_api_handler;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
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

/**
 * @author Maciej Iwaniuk
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Main userData variable that contains information fetched from GitHub API.
     * Variable is bound to display fetched data about user.
     */
    private GithubApiUserData userData;

    /**
     * Binding for activity_main.xml.
     */
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        assignEvents();
    }

    /**
     * Assigns events for elements.
     */
    protected void assignEvents() {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(event -> {
            try {
                this.tryToFindUser();
                EditText searchEditText = findViewById(R.id.usernameText);
                searchEditText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button showRepositoriesBtn = (Button) findViewById(R.id.showRepositoriesBtn);
        showRepositoriesBtn.setOnClickListener(event -> {
            try {
                this.showRepositories();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Tries to find and fetch data about user via github api and display it.
     */
    protected void tryToFindUser() throws Exception {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        String username = usernameText.getText().toString();

        if (username.length() == 0) return;

        this.userData = new GithubApiUserData(username);
        this.binding.setUserData(this.userData);

        boolean userNotFoundError = false;
        try {
            this.userData.loadingUserData.set(true);
            this.userData.fetchData();

            Drawable avatarDrawable = StaticHelper.loadImageDrawableFromUrl(this.userData.avatarUrl);
            this.userData.avatarDrawable = StaticHelper.resizeImageDrawable(avatarDrawable, 350, getResources());

        } catch (Exception e) {
            e.printStackTrace();
            userNotFoundError = true;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean finalUserNotFoundError = userNotFoundError;
        handler.postDelayed(() -> {
            this.userData.loadingUserData.set(false);

            if (finalUserNotFoundError) {
                this.userData.userNotFoundError.set(true);
                return;
            }

            this.userData.readyToDisplayUserData.set(true);
        }, 1000);
    }

    protected void showRepositories() {

    }



}