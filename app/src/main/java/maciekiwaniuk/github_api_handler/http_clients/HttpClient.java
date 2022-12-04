package maciekiwaniuk.github_api_handler.http_clients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Fetches data from passed URL.
 */
public class HttpClient {

    /**
     * Variable of passed data stored as a String.
     */
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

}
