package maciekiwaniuk.github_api_handler.events;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import maciekiwaniuk.github_api_handler.R;
import maciekiwaniuk.github_api_handler.databinding.ActivityMainBinding;
import maciekiwaniuk.github_api_handler.handlers.DateHandler;
import maciekiwaniuk.github_api_handler.http_clients.GithubRepositoriesHttpClient;
import maciekiwaniuk.github_api_handler.models.GithubRepository;
import maciekiwaniuk.github_api_handler.models.GithubUser;

/**
 * Handles click event on show repositories button. Searches and displays repositories of found user.
 */
final public class ShowRepositoriesEvent {
    ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ShowRepositoriesEvent(GithubUser user, ActivityMainBinding binding) {
        this.binding = binding;

        user.loadingRepositories.set(true);
        user.readyToDisplayRepositories.set(false);
        user.repositoriesNotFoundInfo.set(false);

        boolean repositoriesNotFoundInfo = false;
        try {
            GithubRepositoriesHttpClient githubRepositoriesHttpClient = new GithubRepositoriesHttpClient(user.reposUrl);

            ArrayList<GithubRepository> githubRepositories = githubRepositoriesHttpClient.getDataAsRepositoriesArray();

            this.displayRepositories(githubRepositories);

        } catch (Exception e) {
            repositoriesNotFoundInfo = true;
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean finalRepositoriesNotFoundInfo = repositoriesNotFoundInfo;
        handler.postDelayed(() -> {
            this.binding.setUser(user);

            user.loadingRepositories.set(false);

            if (finalRepositoriesNotFoundInfo) {
                user.repositoriesNotFoundInfo.set(true);
            } else {
                user.readyToDisplayRepositories.set(true);
            }
        }, 1000);
    }

    /**
     * Creates and displays views of single_user_repository.xml by passed list of repositories.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void displayRepositories(ArrayList<GithubRepository> githubRepositories) {
        LayoutInflater inflater = LayoutInflater.from(this.binding.getRoot().getContext());
        LinearLayout repositoriesLayout = this.binding.getRoot().findViewById(R.id.repositoriesLayout);
        repositoriesLayout.removeAllViewsInLayout();

        for (int i = 0; i < githubRepositories.size(); i++) {
            View repositorySingleRepositoryLayout = inflater.inflate(R.layout.single_user_repository, this.binding.getRoot().findViewById(R.id.activity_main), false);
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
}