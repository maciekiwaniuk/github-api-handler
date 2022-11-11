package com.example.github_api_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

        View rootView = findViewById(android.R.id.content);

        assignEvents(rootView);
    }

    /**
     *
     */
    protected void assignEvents(View view) {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(event -> tryToFindUser(view));
    }

    /**
     *
     */
    protected void tryToFindUser(View view) {
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
                Drawable drawable = MainActivity.loadImageFromUrl(finalUserData.get("avatar_url"));
                ImageView avatarImageView = (ImageView) findViewById(R.id.avatarImageView);
                avatarImageView.setImageDrawable(drawable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, 1000);
    }

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