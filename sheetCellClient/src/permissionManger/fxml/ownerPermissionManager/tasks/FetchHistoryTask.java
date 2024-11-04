package permissionManger.fxml.ownerPermissionManager.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

import static constants.Constants.GET_HISTORY_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class FetchHistoryTask extends Task<List<String>> {
    @Override
    protected List<String> call() throws Exception {
        Request request = new Request.Builder()
                .url(GET_HISTORY_PATH) // Replace with your actual servlet URL
                .build();

        try (
                Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                // Assuming the response is a JSON array of strings
                return new Gson().fromJson(responseData, new TypeToken<List<String>>() {
                }.getType());
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}