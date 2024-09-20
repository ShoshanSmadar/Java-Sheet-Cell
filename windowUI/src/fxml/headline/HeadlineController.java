package fxml.headline;

import javafx.application.Platform;
import cell.CellDTO;
import coordinate.CoordinateDTO;
import fxml.dynamicSheet.DynamicSheetController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;

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
                    });

                    // Simulate additional work to reach 100% progress
                    for (int i = 90; i <= 100; i += 2) {
                        updateProgress(i, 100);
                        Thread.sleep(50); // Slow down progress to simulate more work
                    }

                } catch (FileNotFoundException e) {
                    Platform.runLater(() -> mainController.showErrorPopup(
                            new RuntimeException("No file given, please try again.")));
                } catch (Exception e) {
                    updateProgress(50, 100); // In case of error, set progress to 50%
                    Platform.runLater(() -> mainController.showErrorPopup(e));
                } finally {
                    updateProgress(100, 100); // Ensure progress finishes even if there's an error
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


//    private void processFileInBackground(File file) {
//        // Create a Task to process the file in the background
//        Task<Void> task = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                try {
//                    fileLoadProgressBar.setVisible(true);
//                    mainController.OpenFXMLFile(file);
//                    Thread.sleep(1000); // Simulate processing time
//                    updateProgress(100, 100); // Fill progress to 90%
//
//
//                    Platform.runLater(() -> {
//                        mainController.buildSheet();
//                        filePathLbl.setText(file.getAbsolutePath());
//                        setHeaderInformationFromSheet();
//                        filePathLbl.setVisible(true);
//                        mainController.makeRangesEnabled();
//                    });
//
//                    // Simulate additional work to reach 100% progress
//                    //Thread.sleep(500); // Simulate delay for reaching 100%
//                    //updateProgress(100, 100); // Fill progress to 100%
//
//                }
//                catch (FileNotFoundException e) {
//                    Platform.runLater(() -> mainController.showErrorPopup(new RuntimeException("No file given, please try again.")));
//                }
//                catch (Exception e) {
//                    Thread.sleep(1000); // Simulate processing time
//                    updateProgress(50, 100);
//                    Platform.runLater(() -> mainController.showErrorPopup(e));
//                }
//                return null;
//            }
//        };
//
//        fileLoadProgressBar.progressProperty().bind(task.progressProperty());
//
//        // Handle task completion
//        task.setOnSucceeded(e -> {
//            // Unbind progress property and hide progress bar after delay
//            Platform.runLater(() -> {
//                // Run another task to introduce a delay before hiding the progress bar
//                new Thread(() -> {
//                    try {
//                        Thread.sleep(500); // Wait for 500 milliseconds
//                    } catch (InterruptedException interruptedException) {
//                        Thread.currentThread().interrupt();
//                    }
//                    Platform.runLater(() -> fileLoadProgressBar.setVisible(false));
//                }).start();
//            });
//        });
//
//        task.setOnFailed(e -> {
//            fileLoadProgressBar.progressProperty().unbind();
//        });
//
//        // Run the task in a new Thread
//        new Thread(task).start();
//    }

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
    void updateValueClicked(MouseEvent event) {
        updateValue();
    }

    private void updateValue(){
        try{
            mainController.updateCellValue(dynamicSheetController.getCurrentClickedCellCoordinateSTO(), ActionLineLbl.getText());
            mainController.updateSheet();
            dynamicSheetController.handleCellClick(dynamicSheetController.getCurrentClickedCellLabel());
        }
        catch (Exception e){
            mainController.showErrorPopup(e);
        }
        ActionLineLbl.setText(null);
    }

    public void setControllers(appController.appController controller, DynamicSheetController dynamicSheetController){
        this.mainController = controller;
        this.dynamicSheetController = dynamicSheetController;
    }
}
