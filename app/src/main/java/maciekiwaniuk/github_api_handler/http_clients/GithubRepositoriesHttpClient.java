package maciekiwaniuk.github_api_handler.http_clients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import maciekiwaniuk.github_api_handler.models.GithubRepository;

public class GithubRepositoriesHttpClient extends HttpClient {

    public GithubRepositoriesHttpClient(String URL) throws Exception {
        super(URL);
    }

    public ArrayList<GithubRepository> getDataAsRepositoriesArray() throws JSONException {
        ArrayList<GithubRepository> githubRepositories = new ArrayList<>();

        JSONArray repositoriesData = new JSONArray(this.data);
        int limitNumberOfRepositories = 30;
        int count = 0;

        for (int i = 0; i < repositoriesData.length(); i++) {
            if (repositoriesData.getJSONObject(i).getString("pushed_at").equals("null")) continue;
            if (count == limitNumberOfRepositories) break;

            JSONObject repository = repositoriesData.getJSONObject(i);
            githubRepositories.add(new GithubRepository(repository));
            count++;
        }

        return githubRepositories;
    }
}
