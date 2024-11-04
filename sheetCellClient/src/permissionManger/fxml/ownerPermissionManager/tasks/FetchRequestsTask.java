package permissionManger.fxml.ownerPermissionManager.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.util.Map;

import static constants.Constants.GET_PERMISSION_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class FetchRequestsTask extends Task<Map<String, String>> {
    @Override
    protected Map<String, String> call() throws Exception {

        Request request = new Request.Builder()
                .url(GET_PERMISSION_PATH) // Replace with your actual servlet URL
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                return new Gson().fromJson(responseData, new TypeToken<Map<String, String>>(){}.getType());
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}