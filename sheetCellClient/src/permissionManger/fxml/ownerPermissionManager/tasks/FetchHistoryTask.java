package permissionManger.fxml.ownerPermissionManager.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import static constants.Constants.GET_HISTORY_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class FetchHistoryTask extends Task<List<String>> {
    String sheetName;

    public FetchHistoryTask(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    protected List<String> call() throws Exception {
        String urlWithParams = GET_HISTORY_PATH + "?sheetName=" + URLEncoder.encode(sheetName, "UTF-8");

        Request request = new Request.Builder()
                .url(urlWithParams) // Replace with your actual servlet URL
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