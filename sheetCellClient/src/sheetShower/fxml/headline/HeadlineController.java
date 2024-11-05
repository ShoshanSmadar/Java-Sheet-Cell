package sheetShower.fxml.headline;

import sheetShower.fxml.popUpFiltter.FilterContoller;
import sheetShower.fxml.sheetVersionPopUp.sheetVersionShowerController;
import javafx.application.Platform;
import cell.CellDTO;
import coordinate.CoordinateDTO;
import sheetShower.fxml.dynamicSheet.DynamicSheetController;
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

import static sheetShower.UIconstant.TextConstants.*;

//import java.awt.*;

public class HeadlineController {
    private sheetShower.appController.appController mainController;
    private DynamicSheetController dynamicSheetController;


    @FXML
    private TextField ActionLineLbl;

    @FXML
    private Label noWritingPermissioonLbl;

    @FXML
    private Label OriginalValueLbl;

    @FXML
    private Label SheetVersionLbl;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Tooltip actionLineExplenation;

    @FXML
    private Label actionLineQuestionMark;

    @FXML
    private CheckBox animationCheckBox;

    @FXML
    private Label cellVersionLbl;

    @FXML
    private ProgressBar fileLoadProgressBar;

    @FXML
    private Label filePathLbl;

    @FXML
    private Label isSheetUpdatedLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Button selectVersionBtn;

    @FXML
    private Label selectedCellLbl;

    @FXML
    private Button updateSheetBtn;

    @FXML
    private Label userUpdatingName;

    public void disableWritingActions() {
        UpdateBtn.setDisable(true);
        ActionLineLbl.setDisable(true);
        noWritingPermissioonLbl.setVisible(true);
    }


    @FXML
    void initialize() {
        UpdateBtn.disableProperty().bind(ActionLineLbl.textProperty().isEmpty());
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sheetShower/fxml/sheetVersionPopUp/sheetVersionPopUp.fxml"));
            Parent popupRoot = fxmlLoader.load();

            sheetVersionShowerController sheetVersionController = fxmlLoader.getController();
            sheetVersionController.setSheetName(mainController.getSheetDTO().getSheetName());
            sheetVersionController.setNumberOfVersions(mainController.getSheetDTO().getSheetVersion());

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
        if(mainController.hasWritingPermission()){
            ActionLineLbl.setDisable(false);
        }
        else {
            ActionLineLbl.clear();
            ActionLineLbl.setDisable(true);
        }
        if(cell != null){
            OriginalValueLbl.setText(ORIGINAL_VALUE + cell.getOriginalValue());
            cellVersionLbl.setText(LAST_UPDATED_VERSION + cell.getLastVersionChanged());
        }
        if(animationCheckBox.isSelected()){
            dynamicSheetController.animateLabelPopOut();
        }
        if (!mainController.hasWritingPermission()){
            disableWritingActions();
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
        if(mainController.hasWritingPermission()){
            updateValue();
        }
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

    public void setControllers(sheetShower.appController.appController controller, DynamicSheetController dynamicSheetController){
        this.mainController = controller;
        this.dynamicSheetController = dynamicSheetController;
    }

    public boolean makeAnimation() {
        return animationCheckBox.isSelected();
    }

    public void setStartSheet() {
        mainController.buildSheet();
        setHeaderInformationFromSheet();
        mainController.makeRangesEnabled();
        mainController.makePopUpsEnabled();
        selectVersionBtn.setDisable(false);
    }
}
