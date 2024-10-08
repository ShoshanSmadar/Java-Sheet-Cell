package fxml.headline;

import fxml.popUpFiltter.FilterContoller;
import fxml.sheetVersionPopUp.sheetVersionShowerController;
import javafx.application.Platform;
import cell.CellDTO;
import coordinate.CoordinateDTO;
import fxml.dynamicSheet.DynamicSheetController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static UIconstant.TextConstants.*;

//import java.awt.*;

public class HeadlineController {
    private appController.appController mainController;
    private DynamicSheetController dynamicSheetController;

    @FXML
    private TextField ActionLineLbl;

    @FXML
    private Button LoadFileBtn;

    @FXML
    private javafx.scene.control.Label OriginalValueLbl;

    @FXML
    private javafx.scene.control.Label SheetVersionLbl;

    @FXML
    private Button UpdateBtn;

    @FXML
    private ProgressBar fileLoadProgressBar;

    @FXML
    private javafx.scene.control.Label cellVersionLbl;

    @FXML
    private javafx.scene.control.Label filePathLbl;

    @FXML
    private javafx.scene.control.Label nameLbl;

    @FXML
    private Button selectVersionBtn;

    @FXML
    private javafx.scene.control.Label selectedCellLbl;

    @FXML
    private Tooltip actionLineExplenation;

    @FXML
    private Label actionLineQuestionMark;

    @FXML
    private CheckBox animationCheckBox;

    @FXML
    void initialize() {
        UpdateBtn.disableProperty().bind(ActionLineLbl.textProperty().isEmpty());
        selectVersionBtn.disableProperty().bind(filePathLbl.textProperty().isEmpty());
        ActionLineLbl.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                updateValue();            }
        });

        actionLineExplenation.setText(ACTION_LINE_EXPLANATION);
        actionLineExplenation.setStyle("-fx-font-size: 14px;");
        actionLineExplenation.setShowDuration(Duration.INDEFINITE);

        actionLineQuestionMark.setOnMouseEntered((MouseEvent event) -> {
            Tooltip.install(actionLineQuestionMark, actionLineExplenation);  // Show the tooltip
        });

        // Remove the tooltip when the mouse exits
        actionLineQuestionMark.setOnMouseExited((MouseEvent event) -> {
            Tooltip.uninstall(actionLineQuestionMark, actionLineExplenation);  // Hide the tooltip
        });
    }

    @FXML
    void showVersion(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sheetVersionPopUp/sheetVersionPopUp.fxml"));
            Parent popupRoot = fxmlLoader.load();

            sheetVersionShowerController sheetVersionController = fxmlLoader.getController();
            sheetVersionController.setEngine(mainController.getEngine());

            // Create a new stage (popup window)
            Stage popupStage = new Stage();

            // Set the modality (optional) - makes the popup block interaction with the main window
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the title of the popup window
            popupStage.setTitle("Sheet versions");


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
    void openFileBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.setTitle("Open File");
        Stage stage = (Stage) LoadFileBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        processFileInBackground(file);
        mainController.buildSheet();



//        try{
//            mainController.OpenFXMLFile(file);
//            filePathLbl.setText(file.getAbsolutePath());
//            filePathLbl.setVisible(true);
//            setHeaderInformationFromSheet();
//        }
//        catch (Exception e){
//            showErrorPopup(e);
//        }
    }
    private void processFileInBackground(File file) {
        // Create a Task to process the file in the background
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Platform.runLater(() -> fileLoadProgressBar.setVisible(true));
                    mainController.OpenFXMLFile(file);

                    // Simulate processing time
                    for (int i = 0; i <= 90; i += 10) {
                        updateProgress(i, 100);
                        Thread.sleep(100); // Simulate gradual file loading progress
                    }

                    // File loaded, now set progress to 90%
                    updateProgress(90, 100);

                    // Run UI updates on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        mainController.buildSheet();
                        filePathLbl.setText(file.getAbsolutePath());
                        setHeaderInformationFromSheet();
                        filePathLbl.setVisible(true);
                        mainController.makeRangesEnabled();
                        mainController.makePopUpsEnabled();
                    });

                    // Simulate additional work to reach 100% progress
                    for (int i = 90; i <= 100; i += 2) {
                        updateProgress(i, 100);
                        Thread.sleep(50); // Slow down progress to simulate more work
                    }

                } catch (FileNotFoundException e) {
                    Platform.runLater(() -> mainController.showErrorPopup(
                            new RuntimeException("No file given.")));
                } catch (Exception e) {
                    updateProgress(50, 100); // In case of error, set progress to 50%
                    Platform.runLater(() -> mainController.showErrorPopup(e));
                }
                return null;
            }
        };

        // Bind the ProgressBar to the task's progress property
        fileLoadProgressBar.progressProperty().bind(task.progressProperty());

        // Handle task completion
        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                // Add a short delay before hiding the progress bar
                new Thread(() -> {
                    try {
                        Thread.sleep(500); // 500ms delay after completion
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    } finally {
                        Platform.runLater(() -> fileLoadProgressBar.setVisible(false));
                    }
                }).start();
            });
        });

        // Start the task in a background thread
        new Thread(task).start();
    }

    private void setHeaderInformationFromSheet(){
        SheetDTO sheet = mainController.getSheetDTO();
        nameLbl.setText(sheet.getSheetName());
        setSheetVersionLblText(sheet.getSheetVersion());
        SheetVersionLbl.setVisible(true);
    }

    public void setSheetVersionLblText(int sheetVersion){
        SheetVersionLbl.setText(VERSION_LABEL + sheetVersion);
    }



    public void onCellLabelClicked(CellDTO cell, CoordinateDTO coordinateDTO) {
        selectedCellLbl.setText(SELECTED_CELL + coordinateDTO);
        ActionLineLbl.setDisable(false);
        if(cell != null){
            OriginalValueLbl.setText(ORIGINAL_VALUE + cell.getOriginalValue());
            cellVersionLbl.setText(LAST_UPDATED_VERSION + cell.getLastVersionChanged());
        }
        if(animationCheckBox.isSelected()){
            dynamicSheetController.animateLabelPopOut();
        }
    }

    public void resetLabelClicked(){
        selectedCellLbl.setText(SELECTED_CELL);
        ActionLineLbl.setDisable(true);
        OriginalValueLbl.setText(ORIGINAL_VALUE);
        cellVersionLbl.setText(LAST_UPDATED_VERSION);
    }

    @FXML
    void selectVersionClicked(MouseEvent event) {

    }

    @FXML
    void updateValueClicked() {
        updateValue();
    }

    private void updateValue(){
        try{
            mainController.updateCellValue(dynamicSheetController.getCurrentClickedCellCoordinateSTO(), ActionLineLbl.getText());
            mainController.updateSheet();
            //dynamicSheetController.handleCellClick(dynamicSheetController.getCurrentClickedCellLabel());
            dynamicSheetController.resetCurrentClickedLabels();
            if(animationCheckBox.isSelected()){
                dynamicSheetController.startGridPaneDance();
            }
        }
        catch (Exception e){
            mainController.showErrorPopup(e);
        }
        ActionLineLbl.setText(null);
        dynamicSheetController.setBackroundColors();
    }

    public void setControllers(appController.appController controller, DynamicSheetController dynamicSheetController){
        this.mainController = controller;
        this.dynamicSheetController = dynamicSheetController;
    }

    public boolean makeAnimation() {
        return animationCheckBox.isSelected();
    }
}
