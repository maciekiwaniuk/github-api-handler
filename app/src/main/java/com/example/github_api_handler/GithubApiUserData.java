package com.example.github_api_handler;

import android.util.Log;

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
    private JSONObject userData;

    /**
     *
     *
     * @param username
     * @throws Exception
     */
    public GithubApiUserData(String username) throws Exception {
        this.userUrlAPI = "https://api.github.com/users/" + username;

        this.fetchData();
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

        this.userData = new JSONObject(response.toString());
    }

    /**
     *
     *
     * @param element
     * @return String
     * @throws JSONException
     */
    public String get(String element) throws JSONException {
        return this.userData.getString(element);
    }
}
