package com.example.github_api_handler;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class GithubApiUserData {

    /**
     *
     */
    private final String userUrlAPI;

    /**
     *
     */
    public String username;
    public String name;
    public String avatarUrl;
    public Drawable avatarDrawable;
    public String reposUrl;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String bio;
    public int followers;
    public int following;
    public String createdAt;

    /**
     *
     */
    public ObservableBoolean userNotFoundError = new ObservableBoolean(false);

    /**
     *
     */
    public ObservableBoolean readyToDisplay = new ObservableBoolean(false);

    /**
     *
     */
    public ObservableBoolean loadingData = new ObservableBoolean(false);

    /**
     *
     *
     * @param username
     * @throws Exception
     */
    public GithubApiUserData(String username) throws Exception {
        this.userUrlAPI = "https://api.github.com/users/" + username;
    }

    /**
     *
     *
     * @throws Exception
     */
    public void fetchData() throws Exception {
        URL obj = new URL(this.userUrlAPI);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }

        input.close();

        JSONObject userData = new JSONObject(response.toString());

        this.assignVariablesAfterFetch(userData);
    }

    public void assignVariablesAfterFetch(JSONObject userData) throws JSONException {
        this.username = userData.getString("login");
        this.name = userData.getString("name");
        this.avatarUrl = userData.getString("avatar_url");
        this.reposUrl = userData.getString("repos_url");
        this.company = userData.getString("company");
        this.blog = userData.getString("blog");
        this.location = userData.getString("location");
        this.email = userData.getString("email");
        this.bio = userData.getString("bio");
        this.followers = Integer.parseInt(userData.getString("followers"));
        this.following = Integer.parseInt(userData.getString("following"));
        this.createdAt = userData.getString("created_at");
    }

}
