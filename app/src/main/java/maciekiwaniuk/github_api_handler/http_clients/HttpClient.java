package maciekiwaniuk.github_api_handler.http_clients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    protected final String data;

    public HttpClient(String URL) throws Exception {
        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }

        input.close();

        this.data = response.toString();
    }

    public JSONArray getDataAsJSONArray() throws JSONException {
        return new JSONArray(this.data);
    }

    public JSONObject getDataAsJSONObject() throws JSONException {
        return new JSONObject(this.data);
    }

}
