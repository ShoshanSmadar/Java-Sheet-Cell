package sheetShower.fxml.rangeSettings;

import sheetShower.appController.appController;
import sheetShower.fxml.dynamicSheet.DynamicSheetController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


import java.util.List;

public class RangeSettingController {
    @FXML private DynamicSheetController dynamicSheetController;
    @FXML  private appController mainController;
    @FXML
    private Button addRangeButton;

    @FXML
    private VBox deletLabelContainer;

    @FXML
    private ScrollPane deleteRangeSrollPane;

    @FXML
    private TitledPane deleteRangesPane;

    @FXML
    private TextField rangeNameTextBar;

    @FXML
    private TextField rangeValueTextBar;

    @FXML
    private VBox showLabelContainer;

    @FXML
    private TitledPane showRangesPane;

    @FXML
    private TitledPane addRangesPane;

    @FXML
    private ScrollPane showRangesScrollPane;

    @FXML
    public void initialize() {
        // Set up the TitledPane click event
        showRangesPane.setOnMouseClicked(event -> handleShowRangeClicked());
        deleteRangesPane.setOnMouseClicked(event -> handleDeleteRangeClicked());
        // Add listeners to both text fields
        rangeNameTextBar.textProperty().addListener((observable, oldValue, newValue) -> checkTextFields());
        rangeValueTextBar.textProperty().addListener((observable, oldValue, newValue) -> checkTextFields());

    }

    public void makeRangeEnabled(){
        deleteRangesPane.setDisable(false);
        showRangesPane.setDisable(false);
        addRangesPane.setDisable(false);
    }

    // Method to check if both fields have text
    private void checkTextFields() {
        String rangeNameText = rangeNameTextBar.getText();
        String rangeValueText = rangeValueTextBar.getText();

        // Enable the button if both text fields are not empty
        addRangeButton.setDisable(rangeNameText.isEmpty() || rangeValueText.isEmpty());
    }

    @FXML
    private void handleAddRangeButtonClick() {
        String rangeName = rangeNameTextBar.getText();
        String rangeValue = rangeValueTextBar.getText();

        mainController.addRange(rangeName, rangeValue);

        // Clear text fields after button is clicked (optional)
        rangeNameTextBar.clear();
        rangeValueTextBar.clear();
    }

    public void setControllers(appController appController, DynamicSheetController dynamicSheetController) {
        this.mainController = appController;
        this.dynamicSheetController = dynamicSheetController;
    }

    private void handleDeleteRangeClicked() {
        List<String> stringList = getListOfStrings();

        // Clear previous labels
        deletLabelContainer.getChildren().clear();

        for (String item : stringList) {
            Label label = new Label(item);

            // Set up a click event for each label
            label.setOnMouseClicked(e -> handelDeleteLabelClick(label.getText()));

            // Add the label to the VBox
            deletLabelContainer.getChildren().add(label);
        }

        // Adjust scroll bar visibility (if necessary)
        deleteRangeSrollPane.setFitToWidth(true);
    }

    // Method to handle TitledPane click
    private void handleShowRangeClicked() {
        List<String> stringList = getListOfStrings();

        // Clear previous labels
        showLabelContainer.getChildren().clear();

        for (String item : stringList) {
            Label label = new Label(item);

            // Set up a click event for each label
            label.setOnMouseClicked(e -> handelShowLabelClick(label.getText()));
            //label.setOnMouseClicked(e -> handelShowLabelClick(label.getText(), false));

            // Add the label to the VBox
            showLabelContainer.getChildren().add(label);
        }

        // Adjust scroll bar visibility (if necessary)
        showRangesScrollPane.setFitToWidth(true);
    }

    // Dummy method to simulate getting a list of strings
    private List<String> getListOfStrings() {
        return mainController.getSheetDTO().getRangeNames();
    }

    // Event handler for label click
    private void handelShowLabelClick(String rangeName) {
        dynamicSheetController.handleRangeClickedLabel(mainController.getSheetDTO().getRangeCoordinates(rangeName));
    }

    private void handelDeleteLabelClick(String labelName) {
        mainController.deleteRange(labelName);
    }


    public void disableWritingActions() {
        //deleteRangesPane.setDisable(true);
        //addRangesPane.setDisable(true);
        deleteRangesPane.setVisible(false);
        addRangesPane.setVisible(false);
    }
}
