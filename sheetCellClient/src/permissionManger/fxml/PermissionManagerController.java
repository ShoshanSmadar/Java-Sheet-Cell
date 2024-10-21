package permissionManger.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.Closeable;
import java.io.IOException;

public class PermissionManagerController implements Closeable, HttpStatusUpdate {

    @FXML
    private Button DnyAckPermissionBtn;

    @FXML
    private Button LoadSheetBtn;

    @FXML
    private Text NameHeader;

    @FXML
    private GridPane PermissionTableGrid;

    @FXML
    private Button RequestPermissionBtn;

    @FXML
    private Button ViewSheetBtn;

    @FXML
    void DnyAckPermissionAction(ActionEvent event) {

    }

    @FXML
    void RequestPermissionAction(ActionEvent event) {

    }

    @FXML
    void ViesSheetBtn(ActionEvent event) {

    }

    @FXML
    void loadSheetAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.setTitle("Open File");
        Stage stage = (Stage) LoadSheetBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                // Send the file to the server
                String serverUrl = "http://localhost:8080/yourapp/upload"; // Your server upload endpoint
                sendFileToServer(file, serverUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void sendFileToServer(File file, String serverUrl) throws Exception {
        String boundary = "------WebKitFormBoundary" + System.currentTimeMillis();

        URL url = new URL(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream outputStream = connection.getOutputStream()) {
            // Write the file part
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
            outputStream.write(("Content-Type: " + "application/octet-stream" + "\r\n\r\n").getBytes());

            // Write the file content
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("File uploaded successfully.");
        } else {
            System.out.println("File upload failed. Response code: " + responseCode);
        }
    }

    @Override
    public void close() throws IOException {

    }
}