package fxml.sorter;

import fxml.popUpFiltter.FilterContoller;
import fxml.popUpLineSort.LineSortController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpSorterController {
    @FXML
    private Button filtterLineBtn;
    @FXML
    private Button sortLineBtn;
    @FXML
    private appController.appController mainController;




    @FXML
    public void initialize() {
        sortLineBtn.setDisable(true);
        filtterLineBtn.setDisable(true);
    }

    public void setMainController(appController.appController mainController) {
        this.mainController = mainController;
    }

    public void enableButtons(){
        sortLineBtn.setDisable(false);
        filtterLineBtn.setDisable(false);
    }

    @FXML
    void HandleFiltterLinePressed(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/popUpFiltter/Filtter.fxml"));
            Parent popupRoot = fxmlLoader.load();

            FilterContoller filterContoller = fxmlLoader.getController();
            filterContoller.setEngine(mainController.getEngine());
            mainController.getEngine().setLineFilter();

            // Create a new stage (popup window)
            Stage popupStage = new Stage();

            // Set the modality (optional) - makes the popup block interaction with the main window
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the title of the popup window
            popupStage.setTitle("filter Lines");


            // Set the scene for the popup
            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.showAndWait();  // Show and wait will block the parent window until the popup is closed

        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception properly in your app
        }
    }

    @FXML
    void handleSortLinePressed(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/popUpLineSort/LineSort.fxml"));
            Parent popupRoot = fxmlLoader.load();

            LineSortController lineSortController = fxmlLoader.getController();
            lineSortController.setEngine(mainController.getEngine());

            // Create a new stage (popup window)
            Stage popupStage = new Stage();

            // Set the modality (optional) - makes the popup block interaction with the main window
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the title of the popup window
            popupStage.setTitle("Sort Line");


            // Set the scene for the popup
            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.showAndWait();  // Show and wait will block the parent window until the popup is closed

        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception properly in your app
        }
    }
}
