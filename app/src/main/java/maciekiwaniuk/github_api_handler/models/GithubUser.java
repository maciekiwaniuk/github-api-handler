package maciekiwaniuk.github_api_handler.models;

import android.graphics.drawable.Drawable;

import androidx.databinding.ObservableBoolean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Contains information about GitHub's user
 */
public class GithubUser {

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
     * Specifies if progressBar that imitates loading data user should be visible.
     */
    public ObservableBoolean loadingUser = new ObservableBoolean(false);

    /**
     * Specifies if the element with fetched user data should be visible.
     */
    public ObservableBoolean readyToDisplayUser = new ObservableBoolean(false);

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

    public GithubUser() {}

    public void setAvatar(Drawable avatar) {
        this.avatarDrawable = avatar;
    }

    /**
     * Saves data from passed object to attributes.
     */
    public void assignVariablesFromFetchedJSONObject(JSONObject userData) throws JSONException {
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
