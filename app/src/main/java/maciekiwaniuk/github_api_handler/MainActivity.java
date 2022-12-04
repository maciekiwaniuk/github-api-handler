package maciekiwaniuk.github_api_handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;

import maciekiwaniuk.github_api_handler.databinding.ActivityMainBinding;
import maciekiwaniuk.github_api_handler.events.SearchUserEvent;
import maciekiwaniuk.github_api_handler.events.ShowRepositoriesEvent;
import maciekiwaniuk.github_api_handler.models.GithubUser;

/**
 * @author Maciej Iwaniuk
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Binding for activity_main.xml.
     */
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        GithubUser user = new GithubUser();
        this.binding.setUser(user);

        assignEvents(user);
    }

    /**
     * Assigns events for elements.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void assignEvents(GithubUser user) {
        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(event -> {
            try {
                new SearchUserEvent(user, this.binding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button showRepositoriesBtn = findViewById(R.id.showRepositoriesBtn);
        showRepositoriesBtn.setOnClickListener(event -> {
            try {
                new ShowRepositoriesEvent(user, this.binding);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }




}