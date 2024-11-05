package permissionManger.fxml.ownerPermissionManager.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import static constants.Constants.GET_PERMISSION_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class FetchRequestsTask extends Task<Map<String, String>> {
    String sheetName;
    public FetchRequestsTask(String sheetName) {
        this.sheetName = sheetName;
    }
    @Override
    protected Map<String, String> call() throws Exception {
        String urlWithParams = GET_PERMISSION_PATH + "?sheetName=" + URLEncoder.encode(sheetName, "UTF-8");
        Request request = new Request.Builder()
                .url(urlWithParams) // Replace with your actual servlet URL
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