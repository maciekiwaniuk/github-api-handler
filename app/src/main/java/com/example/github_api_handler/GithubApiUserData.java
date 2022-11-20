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
 * Fetches and saves data via GitHub API about user by his username.
 * Variable of this class is bound for main_activity.xml to display fetched data about user.
 */
public class GithubApiUserData {

    /**
     * Url which provides all information about user by his username.
     */
    private final String userUrlAPI;

    /**
     * User's information.
     */
    public String username;
    public String name;
    public String reposUrl;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String bio;
    public String createdAt;
    public String avatarUrl;
    public String followers;
    public String following;
    public Drawable avatarDrawable;


    /**
     * Specifies if textView with error message should be visible.
     */
    public ObservableBoolean userNotFoundError = new ObservableBoolean(false);

    /**
     * Specifies if progressBar that imitates loading data should be visible.
     */
    public ObservableBoolean readyToDisplay = new ObservableBoolean(false);

    /**
     * Specifies if the whole element with fetched user data should be visible.
     */
    public ObservableBoolean loadingData = new ObservableBoolean(false);

    /**
     * Initializes main API URL.
     *
     * @param username
     * @throws Exception
     */
    public GithubApiUserData(String username) throws Exception {
        this.userUrlAPI = "https://api.github.com/users/" + username;
    }

    /**
     * Fetches data using URL and saves it to object attributes.
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

    /**
     * Saves data from passed object to attributes.
     *
     * @param userData
     * @throws JSONException
     */
    public void assignVariablesAfterFetch(JSONObject userData) throws JSONException {
        String name = userData.getString("name");
        String company = userData.getString("company");
        String blog = userData.getString("blog");
        String location = userData.getString("location");
        String email = userData.getString("email");
        String bio = userData.getString("bio");

        this.username = userData.getString("login");
        this.avatarUrl = userData.getString("avatar_url");
        this.reposUrl = userData.getString("repos_url");
        this.followers = userData.getString("followers") + " followers";
        this.following = userData.getString("following") + " following";
        this.createdAt = "Account created at " + userData.getString("created_at").substring(0, 10);

        this.name = (name.equals("null") ? null : name);
        this.company = (company.equals("null") ? null : company);
        this.blog = (blog.equals("null") ? null : blog);
        this.location = (location.equals("null") ? null : location);
        this.email = (email.equals("null") ? null : email);
        this.bio = (bio.equals("null") ? null : bio);
    }

}
