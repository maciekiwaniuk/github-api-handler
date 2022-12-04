package maciekiwaniuk.github_api_handler.http_clients;

import org.json.JSONException;
import org.json.JSONObject;

public class GithubUserHttpClient extends HttpClient {
    public GithubUserHttpClient(String username) throws Exception {
        super("https://api.github.com/users/" + username);
    }

    public JSONObject getUserDataAsJSONObject() throws JSONException {
        return new JSONObject(this.data);
    }
}
