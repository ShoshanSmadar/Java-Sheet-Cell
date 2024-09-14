package fxml.sheetSetting;

import fxml.dynamicSheet.DynamicSheetController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class SheetSettingsController {
    private appController.appController mainController;
    private DynamicSheetController dynamicSheetController;

    @FXML
    private ColorPicker BackgroundColorPicker;

    @FXML
    private GridPane ColumnSettingBar;

    @FXML
    private Label ColumnSettingPane;

    @FXML
    private Button RevertCellSettingsBtn;

    @FXML
    private GridPane RowSettingBar;

    @FXML
    private ColorPicker TextColorPicker;

    @FXML
    private ColumnConstraints cellSettingsBar;

    @FXML
    private GridPane cellSettingsPane;

    @FXML
    private Spinner<Integer> columnLengthSpinner;

    @FXML
    private Spinner<Integer> rowLengthSpinner;

    @FXML
    private Label rowSettingBox;

    @FXML
    private ComboBox<String> textAlignmentPicker;

    @FXML
    void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 500, 100);
        columnLengthSpinner.setValueFactory(valueFactory);
        columnLengthSpinner.setDisable(true);  // Spinner starts disabled

        // Add listener to the Spinner to listen for changes and adjust the label width
        columnLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                dynamicSheetController.getCurrentClickedColumnLabel().changeColumnLength(newValue);
        });
        rowLengthSpinner.setValueFactory(valueFactory);
        rowLengthSpinner.setDisable(true);  // Spinner starts disabled
        rowLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(dynamicSheetController.getCurrentClickedRowLabel() != null){
                rowLengthSpinner.isEditable();
                rowLengthSpinner.getValueFactory().setValue((int)dynamicSheetController.getCurrentClickedRowLabel().getHeight());
                dynamicSheetController.getCurrentClickedRowLabel().changeRowWidth(newValue);
            }
            else{
                rowLengthSpinner.setDisable(false);
            }

        });


    }

    public void setControllers(appController.appController controller, DynamicSheetController dynamicSheetController){
        this.mainController = controller;
        this.dynamicSheetController = dynamicSheetController;
    }

    public void setColumnSpinnerCurValue(int curValue){
        columnLengthSpinner.setDisable(false);
        columnLengthSpinner.getValueFactory().setValue((int)dynamicSheetController.getCurrentClickedColumnLabel().getWidth());
    }

    public void setRowSpinnerCurValue(int curValue){
        rowLengthSpinner.setDisable(false);
        rowLengthSpinner.getValueFactory().setValue(curValue);
    }

    public void disableRowSpinner(){
        rowLengthSpinner.setDisable(true);
    }

    public void disableColumnLengthSpinner(){
        columnLengthSpinner.setDisable(true);
    }
}
