import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/permissionManger/fxml/permissionManager.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sheet Manager");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("permissionManger/fxml/permissionManager.fxml");
//        fxmlLoader.setLocation(url);
//        Parent root = fxmlLoader.load(url.openStream());
//
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
}