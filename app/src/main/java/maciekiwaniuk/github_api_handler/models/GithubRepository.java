package maciekiwaniuk.github_api_handler.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Contains information about single GitHub repository.
 */
public class GithubRepository {

    /**
     * Repository data
     */
    final public String title;
    final public String description;
    final public String language;
    final public String stars;
    final public String forks;
    final public String updatedAt;

    /**
     * Assigns variables from JSON Object
     */
    public GithubRepository(JSONObject repositoryData) throws JSONException {
        this.title = repositoryData.getString("name");
        this.description = repositoryData.getString("description");
        this.language = repositoryData.getString("language");
        this.stars = repositoryData.getString("stargazers_count");
        this.forks = repositoryData.getString("forks_count");
        this.updatedAt = repositoryData.getString("pushed_at");
    }
}
