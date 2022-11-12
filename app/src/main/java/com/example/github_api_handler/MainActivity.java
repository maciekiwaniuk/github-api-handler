package com.example.github_api_handler;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        assignEvents();
    }

    /**
     *
     */
    protected void assignEvents() {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(event -> tryToFindUser());
    }

    /**
     *
     */
    protected void tryToFindUser() {
        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        infoTextView.setVisibility(View.GONE);

        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        String username = usernameText.getText().toString();
        if (username.length() == 0) {
            infoTextView.setText("");
            return;
        }

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        boolean foundUser = false;
        GithubApiUserData userData = null;
        try {
            userData = new GithubApiUserData(username);
            foundUser = true;

        } catch (Exception e) {
            e.printStackTrace();
            infoTextView.setText("Couldn't find user with passed username");
        }

        final boolean finalFoundUser = foundUser;
        final GithubApiUserData finalUserData = userData;
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);

            if (! finalFoundUser) {
                infoTextView.setVisibility(View.VISIBLE);
                return;
            }

            infoTextView.setVisibility(View.GONE);
            try {
                Drawable userAvatarImageDrawable = StaticHelper.loadImageFromUrl(finalUserData.get("avatar_url"));
                userAvatarImageDrawable = this.resize(userAvatarImageDrawable, 350);
                ImageView avatarImageView = (ImageView) findViewById(R.id.avatarImageView);
                avatarImageView.setImageDrawable(userAvatarImageDrawable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, 1000);
    }

    private Drawable resize(Drawable image, int size) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size, size, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

}