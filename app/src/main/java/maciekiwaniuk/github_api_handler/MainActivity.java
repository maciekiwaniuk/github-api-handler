package maciekiwaniuk.github_api_handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import maciekiwaniuk.github_api_handler.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Maciej Iwaniuk
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Main userData variable that contains information fetched from GitHub API.
     * Variable is bound to display fetched data about user.
     */
    private GithubApiUserData userData;

    /**
     * Binding for activity_main.xml.
     */
    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        assignEvents();
    }

    /**
     * Assigns events for elements.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void assignEvents() {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(event -> {
            try {
                this.tryToFindUser();
                EditText searchEditText = findViewById(R.id.usernameText);
                searchEditText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button showRepositoriesBtn = (Button) findViewById(R.id.showRepositoriesBtn);
        showRepositoriesBtn.setOnClickListener(event -> {
            try {
                this.showRepositories();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Tries to find and fetch data about user via github api and display it.
     */
    protected void tryToFindUser() throws Exception {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        String username = usernameText.getText().toString();

        if (username.length() == 0) return;

        this.userData = new GithubApiUserData(username);
        this.binding.setUserData(this.userData);

        boolean userNotFoundError = false;
        try {
            this.userData.loadingUserData.set(true);
            this.userData.fetchUserData();

            Drawable avatarDrawable = StaticHelper.loadImageDrawableFromUrl(this.userData.avatarUrl);
            this.userData.avatarDrawable = StaticHelper.resizeImageDrawable(avatarDrawable, 350, getResources());

        } catch (Exception e) {
            e.printStackTrace();
            userNotFoundError = true;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean finalUserNotFoundError = userNotFoundError;
        handler.postDelayed(() -> {
            this.userData.loadingUserData.set(false);

            if (finalUserNotFoundError) {
                this.userData.userNotFoundError.set(true);
                return;
            }

            this.userData.readyToDisplayUserData.set(true);
        }, 1000);
    }

    /**
     * Creates repositories of user passed by argument.
     * It creates in loop instances of single_user_repository.xml and adds it to layout.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void displayRepositories(ArrayList<GithubRepository> githubRepositories) {
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout repositoriesLayout = findViewById(R.id.repositoriesLayout);
        repositoriesLayout.removeAllViewsInLayout();

        for (int i = 0; i < githubRepositories.size(); i++) {
            View repositorySingleRepositoryLayout = inflater.inflate(R.layout.single_user_repository, findViewById(R.id.activity_main), false);

            GithubRepository repo = githubRepositories.get(i);

            TextView repoTitle = repositorySingleRepositoryLayout.findViewById(R.id.repoTitle);
            repoTitle.setText(repo.title);

            TextView repoDescription = repositorySingleRepositoryLayout.findViewById(R.id.repoDescription);
            if (!repo.description.equals("null")) {
                repoDescription.setText(repo.description);
            } else {
                repoDescription.setVisibility(View.GONE);
            }

            TextView repoLanguage = repositorySingleRepositoryLayout.findViewById(R.id.repoLanguage);
            if (!repo.language.equals("null")) {
                repoLanguage.setText(repo.language);
            } else {
                repoLanguage.setVisibility(View.GONE);
            }

            TextView repoStars = repositorySingleRepositoryLayout.findViewById(R.id.repoStars);
            if (Integer.parseInt(repo.stars) > 0) {
                repoStars.setText(" " + repo.stars);
            } else {
                repoStars.setVisibility(View.GONE);
            }

            TextView repoForks = repositorySingleRepositoryLayout.findViewById(R.id.repoForks);
            if (Integer.parseInt(repo.forks) > 0) {
                repoForks.setText(" " + repo.forks);
            } else {
                repoForks.setVisibility(View.GONE);
            }

            TextView repoWhenUpdated = repositorySingleRepositoryLayout.findViewById(R.id.repoWhenUpdated);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");
            LocalDateTime updatedAt = LocalDateTime.parse(repo.updatedAt.replaceAll("[ZT]", " "), formatter);
            String whenUpdatedMessage = new DateHandler(updatedAt).getMessageAboutTimeDifferenceSinceNow();
            repoWhenUpdated.setText(whenUpdatedMessage);

            repositoriesLayout.addView(repositorySingleRepositoryLayout);
        }

    }

    /**
     * Fetches user's repositories and displays it.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void showRepositories() {
        try {
            this.userData.loadingRepositories.set(true);
            this.userData.fetchRepositories();

            this.displayRepositories(this.userData.githubRepositories);

        } catch (Exception e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            this.userData.loadingRepositories.set(false);

            if (this.userData.githubRepositories.size() == 0) {
                this.userData.repositoriesNotFoundInfo.set(true);
            }

            this.userData.readyToDisplayRepositories.set(true);
        }, 1000);
    }



}