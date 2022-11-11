package com.example.github_api_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

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
        EditText nicknameText = (EditText) findViewById(R.id.nicknameText);
        String nickname = nicknameText.getText().toString();
        if (nickname.length() == 0) {
            infoTextView.setText("");
            return;
        }

        RelativeLayout loadingPanel = (RelativeLayout) findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        try {
            GithubApiUserData userData = new GithubApiUserData(nickname);
            infoTextView.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
            infoTextView.setText("Couldn't find user with passed nickname");
            infoTextView.setVisibility(View.VISIBLE);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            loadingPanel.setVisibility(View.GONE);
        }, 500);
    }


}