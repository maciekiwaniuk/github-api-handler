package maciekiwaniuk.github_api_handler.http_clients;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fetches information about user by passed username.
 */
public class GithubUserHttpClient extends HttpClient {
    public GithubUserHttpClient(String username) throws Exception {
        super("https://api.github.com/users/" + username);
    }

    /**
     * Returns fetched data as JSONObject.
     */
    public JSONObject getUserDataAsJSONObject() throws JSONException {
        return new JSONObject(this.data);
    }
}
