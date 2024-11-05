package permissionManger.fxml.ownerPermissionManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.HttpClientUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.util.Duration;
import okhttp3.*;
import permissionManger.fxml.ownerPermissionManager.tasks.FetchHistoryTask;
import permissionManger.fxml.ownerPermissionManager.tasks.FetchRequestsTask;


import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

import static constants.Constants.*;
import static http.HttpClientUtil.HTTP_CLIENT;

public class OwnerManagerController {
    private String currentSheetInformation;
    private int numberOfRowsInHistory = 0;
    Map<String, String> currentRequestsMap;
    private Gson gson = new Gson();
    @FXML
    private GridPane historyGridPane;

    @FXML
    private GridPane requestsGridPane;

    @FXML
    private Label sheetName3;

    @FXML
    private Label sheetNameLbl;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x5;

    @FXML
    private Font x52;

    @FXML
    private Font x521;

    @FXML
    private Font x522;

    @FXML
    private Font x523;

    @FXML
    private Color x6;

    @FXML
    private Color x62;

    @FXML
    private Color x621;

    @FXML
    private Color x622;

    @FXML
    private Color x623;

    @FXML
    public void initialize(String sheetName) {
        startDataRefresh();
        currentSheetInformation = sheetName;
        sheetNameLbl.setText("Set Permissions for "+sheetName);
    }

//    public OwnerManagerController(String sheetName){
//        currentSheetInformation = sheetName;
//    }

    public void populateHistoryGridPane(List<String> historyList) {
        while (numberOfRowsInHistory < historyList.size()) {
            String entry = historyList.get(numberOfRowsInHistory);
            Label label = new Label(entry);
            historyGridPane.add(label, 0, numberOfRowsInHistory++); // Add label in the first column of each row
        }
    }

    private List<String> fetchHistory() {
            Request request = new Request.Builder()
                    .url(GET_HISTORY_PATH) // Replace with your actual servlet URL
                    .build();

            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
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

    private Map<String, String> fetchRequests() throws IOException {
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

    private void startDataRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Fetch history
            FetchHistoryTask fetchHistoryTask = new FetchHistoryTask(currentSheetInformation);
            fetchHistoryTask.setOnSucceeded(e -> {
                List<String> historyList = fetchHistoryTask.getValue();
                populateHistoryGridPane(historyList);
            });
            fetchHistoryTask.setOnFailed(e -> {
                // Handle failure (e.g., log the error)
                fetchHistoryTask.getException().printStackTrace();
            });
            new Thread(fetchHistoryTask).start();

            // Fetch requests
            FetchRequestsTask fetchRequestsTask = new FetchRequestsTask(currentSheetInformation);
            fetchRequestsTask.setOnSucceeded(e -> {
                Map<String, String> requestsMap = fetchRequestsTask.getValue();
                populateRequestsGridPane(requestsMap);
            });
            fetchRequestsTask.setOnFailed(e -> {
                // Handle failure (e.g., log the error)
                fetchRequestsTask.getException().printStackTrace();
            });
            new Thread(fetchRequestsTask).start();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void checkNewRequests(){

    }

    public void checkHistory(){

    }

    public void populateRequestsGridPane(Map<String, String> requestsMap) {
        if(currentRequestsMap == null || currentRequestsMap == requestsMap) {
            requestsGridPane.getChildren().clear(); // Clear existing entries
            int rowIndex = 0;
            for (Map.Entry<String, String> entry : requestsMap.entrySet()) {
                String name = entry.getKey();
                String permission = entry.getValue();

                // Name column
                Label nameLabel = new Label(name);
                requestsGridPane.add(nameLabel, 0, rowIndex);

                // Permission column
                Label permissionLabel = new Label(permission);
                requestsGridPane.add(permissionLabel, 1, rowIndex);

                // Allow button
                Button allowButton = new Button("Allow");
                allowButton.setOnAction(event -> handlePermissionAction(name, permission, true));
                requestsGridPane.add(allowButton, 2, rowIndex);

                // Deny button
                Button denyButton = new Button("Deny");
                denyButton.setOnAction(event -> handlePermissionAction(name, permission, false));
                requestsGridPane.add(denyButton, 3, rowIndex);

                rowIndex++;
            }
        }
    }

    private void handlePermissionAction(String name, String permission, boolean isAllow) {
        String jsonData = "{\"name\":\"" + name + "\",\"sheetName\":\"" + currentSheetInformation + "\",\"permission\":\"" + permission + "\",\"isAllow\":" + isAllow + "}";
        // Build request body
        RequestBody body = RequestBody.create(jsonData, MediaType.get("application/json; charset=utf-8"));

        // Create the request
        Request request = new Request.Builder()
                .url(PERMISSION_GIVER_PATH)
                .post(body)
                .build();

        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong: " + e.getMessage());
                alert.showAndWait();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    checkNewRequests();
                    checkHistory();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong: " + response.message());
                    alert.showAndWait();
                }
            }
        });

    }



}
