package permissionManger.fxml.permissionRequest;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static constants.Constants.GET_HISTORY_PATH;
import static constants.Constants.GET_PERMISSION_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class PermissionRequestPopupController {
    @FXML
    private Label requestLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button readButton;
    @FXML
    private Button writeButton;
    @FXML
    private Button closeButton;

    private String sheetName;
    private String userName;

    public void initialize(String item, String userName) {
        this.sheetName = item;
        this.userName = userName;

        requestLabel.setText("Request permission for " + sheetName +":");
    }

    @FXML
    private void handleReadRequest() {
        doRequest("read");
    }

    @FXML
    private void handleWriteRequest() {
        doRequest("write");
    }

    private void doRequest(String requestType) {
        try{
        String urlWithParams = GET_PERMISSION_PATH + "?sheetName=" +
                URLEncoder.encode(sheetName, "UTF-8") +
                "&requestType=" + URLEncoder.encode(requestType, "UTF-8") +
                "&userName=" + URLEncoder.encode(userName, "UTF-8");
        RequestBody emptyBody = RequestBody.create(null, new byte[0]);
//        RequestBody formBody = new FormBody.Builder()
//                .add("sheetName", sheetName)
//                .add("requestType", requestType)
//                .add("userName", userName)
//                .build();

        // Build the PUT request
        Request request = new Request.Builder()
                .url(urlWithParams)
                .put(emptyBody)
                .build();

        // Execute the request
        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> updateStatus(requestType, "successful"));
                } else {
                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> updateStatus(requestType, "NOT successful!"));
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Update UI on the JavaFX Application Thread
                Platform.runLater(() -> updateStatus(requestType, "NOT successful!"));
            }
        });}
        catch (Exception e){
            Platform.runLater(() -> updateStatus(requestType, "NOT successful!"));
        }
    }

    private void updateStatus(String requestType, String status) {
        requestLabel.setVisible(false);
        readButton.setVisible(false);
        writeButton.setVisible(false);

        statusLabel.setText(requestType + " request for " + sheetName + " sheet\n was " + status);
        statusLabel.setVisible(true);
        closeButton.setVisible(true);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
