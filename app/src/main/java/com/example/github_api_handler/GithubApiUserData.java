package com.example.github_api_handler;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
     * User's repositories
     */
    public ArrayList<GithubRepository> githubRepositories = new ArrayList<>();

    /**
     * Specifies if textView with error message should be visible.
     */
    public ObservableBoolean userNotFoundError = new ObservableBoolean(false);

    /**
     * Specifies if progressBar that imitates loading data user should be visible.
     */
    public ObservableBoolean loadingUserData = new ObservableBoolean(false);

    /**
     * Specifies if the element with fetched user data should be visible.
     */
    public ObservableBoolean readyToDisplayUserData = new ObservableBoolean(false);

    /**
     * Specifies if textView with info message that user doesn't have any repositories should be visible.
     */
    public ObservableBoolean repositoriesNotFoundInfo = new ObservableBoolean(false);

    /**
     * Specifies if the element with fetched repositories should be visible.
     */
    public ObservableBoolean loadingRepositories = new ObservableBoolean(false);

    /**
     * Specifies if progressBar that imitates loading repositories should be visible.
     */
    public ObservableBoolean readyToDisplayRepositories = new ObservableBoolean(false);

    /**
     * Initializes main API URL.
     */
    public GithubApiUserData(String username) throws Exception {
        this.userUrlAPI = "https://api.github.com/users/" + username;
    }

    /**
     * Fetches user data using URL and saves it to object attributes.
     */
    public void fetchUserData() throws Exception {
        JSONObject userData = new JSONObject(StaticHelper.fetchDataFromUrlAsString(this.userUrlAPI));

        this.assignUserDataAfterFetch(userData);
    }

    /**
     * Saves data from passed object to attributes.
     */
    public void assignUserDataAfterFetch(JSONObject userData) throws JSONException {
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

    /**
     * Fetches repositories using repos URL and saves it.
     */
    public void fetchRepositories() throws Exception {
        JSONArray repositoriesData = new JSONArray(StaticHelper.fetchDataFromUrlAsString(this.reposUrl));

        int limitNumberOfRepositories = 30;
        int count = 0;

        for (int i = 0; i < repositoriesData.length(); i++) {
            if (count == limitNumberOfRepositories) break;

            JSONObject repository = repositoriesData.getJSONObject(i);
            this.githubRepositories.add(new GithubRepository(repository));
            count++;
        }

    }

}
