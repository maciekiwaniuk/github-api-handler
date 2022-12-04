package maciekiwaniuk.github_api_handler.events;

import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONObject;

import maciekiwaniuk.github_api_handler.R;
import maciekiwaniuk.github_api_handler.databinding.ActivityMainBinding;
import maciekiwaniuk.github_api_handler.http_clients.GithubUserHttpClient;
import maciekiwaniuk.github_api_handler.http_clients.ImageDrawableHttpClient;
import maciekiwaniuk.github_api_handler.models.GithubUser;

/**
 * Handles click event on search button. Searches and displays data about user by passed username.
 */
final public class SearchUserEvent {

    public SearchUserEvent(GithubUser user, ActivityMainBinding binding) {
        EditText usernameText = binding.getRoot().findViewById(R.id.usernameText);
        String username = usernameText.getText().toString();

        if (username.length() == 0) return;

        LinearLayout repositoriesLayout = binding.getRoot().findViewById(R.id.repositoriesLayout);
        repositoriesLayout.removeAllViewsInLayout();

        user.loadingUser.set(true);
        user.readyToDisplayUser.set(false);
        user.readyToDisplayRepositories.set(false);
        user.userNotFoundError.set(false);

        boolean userNotFoundError = false;
        try {
            GithubUserHttpClient githubUserHttpClient = new GithubUserHttpClient(username);

            JSONObject githubUserDataJSONObject = githubUserHttpClient.getUserDataAsJSONObject();
            user.assignVariablesFromFetchedJSONObject(githubUserDataJSONObject);

            ImageDrawableHttpClient imageDrawableHttpClient = new ImageDrawableHttpClient(user.avatarUrl);
            user.setAvatar(imageDrawableHttpClient.getResizedImage(350, binding.getRoot().getResources()));
        } catch (Exception e) {
            userNotFoundError = true;

        }

        final Handler handler = new Handler(Looper.getMainLooper());
        final boolean finalUserNotFoundError = userNotFoundError;
        handler.postDelayed(() -> {
            binding.setUser(user);

            user.loadingUser.set(false);

            if (finalUserNotFoundError) {
                user.userNotFoundError.set(true);
            } else {
                user.readyToDisplayUser.set(true);
            }
        }, 1000);

    }





}