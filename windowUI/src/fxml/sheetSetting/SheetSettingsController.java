package fxml.sheetSetting;

import fxml.dynamicSheet.DynamicSheetController;
import fxml.labelCreator.CellLabel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


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
        SpinnerValueFactory<Integer> columnValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 100);
        columnLengthSpinner.setValueFactory(columnValueFactory);
        columnLengthSpinner.setDisable(true);  // Spinner starts disabled
        columnLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                dynamicSheetController.getCurrentClickedColumnLabel().changeColumnLength(newValue);
        });

        SpinnerValueFactory<Integer> rowValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 100);
        rowLengthSpinner.setValueFactory(rowValueFactory);
        rowLengthSpinner.setDisable(true);  // Spinner starts disabled
        rowLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            dynamicSheetController.getCurrentClickedRowLabel().changeRowWidth(newValue);
        });

        textAlignmentPicker.setOnAction(event -> {
            String selectedAlignment = textAlignmentPicker.getValue();
            dynamicSheetController.updateLabelAlignment(selectedAlignment);
        });

        RevertCellSettingsBtn.setOnAction(event -> resetColors());

        // Handle color picker actions
        TextColorPicker.setOnAction(event -> updateLabelTextColor());
        BackgroundColorPicker.setOnAction(event -> updateLabelBackgroundColor());

    }

    public void setControllers(appController.appController controller, DynamicSheetController dynamicSheetController){
        this.mainController = controller;
        this.dynamicSheetController = dynamicSheetController;
    }

    public void setColumnSpinnerCurValue(int curValue){
        columnLengthSpinner.setDisable(false);
        columnLengthSpinner.getValueFactory().setValue((int)dynamicSheetController.getCurrentClickedColumnLabel().getWidth());
    }

    public void setAlignment(){
        textAlignmentPicker.setDisable(false);
    }

    public void setRowSpinnerCurValue(int curValue){
        rowLengthSpinner.setDisable(false);
        rowLengthSpinner.getValueFactory().setValue((int)dynamicSheetController.getCurrentClickedRowLabel().getHeight());
    }

    public void disableRowSpinner(){
        rowLengthSpinner.setDisable(true);
    }

    public void disableColumnLengthSpinner(){
        columnLengthSpinner.setDisable(true);
    }

    public void anableAlignment() {
        textAlignmentPicker.setDisable(true);
    }

    private void updateLabelTextColor() {
        // Change only the text color
        dynamicSheetController.getCurrentClickedCellLabel().setTextFill(TextColorPicker.getValue());
        enableRevertButton();
    }

    private void updateLabelBackgroundColor() {
        // Get the current label
        CellLabel label = dynamicSheetController.getCurrentClickedCellLabel();

        // Remove any previous background color styles if needed
        label.getStyleClass().remove("custom-background");

        // Get the selected color from the BackgroundColorPicker
        Color color = BackgroundColorPicker.getValue();

        // Convert the Color to a CSS-compatible string
        String colorAsCss = String.format("rgba(%d, %d, %d, %f);",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                color.getOpacity());

        // Apply the new background color
        label.setStyle("-fx-background-color: " + colorAsCss);
        label.setNewBackroundColorStyle("-fx-background-color: " + colorAsCss);
        label.setBackroundColorStyle();

        // Add a custom class for unchangeable style
        label.getStyleClass().add("custom-background");
//        Label clickedCell = dynamicSheetController.getCurrentClickedCellLabel();
//        String currentStyle = clickedCell.getStyle(); // Get current style if it exists
//        String newStyle = currentStyle + "; -fx-background-color: " + toRgbString(BackgroundColorPicker.getValue()) + ";";
//        clickedCell.setStyle(newStyle);
        enableRevertButton();
    }

    private Color getLabelTextFillColor(Label label) {
        Paint paint = label.getTextFill();
        if (paint instanceof Color) {
            return (Color) paint;
        } else {
            // Handle cases where paint is not a Color, if necessary
            // For example, return a default color or throw an exception
            return Color.BLACK; // Default color
        }
    }

    private void enableRevertButton() {
        if (RevertCellSettingsBtn.isDisabled()) {
            RevertCellSettingsBtn.setDisable(false);
        }
    }

    private void resetColors() {
        // Remove any inline styles and reapply default CSS class
        CellLabel label = dynamicSheetController.getCurrentClickedCellLabel();
        if(label.getStyleClass().contains("even-row")){
            label.setStyle("-fx-background-color: #f0f8ff;");
            label.setNewBackroundColorStyle(null);
        }
        else{
            label.setStyle("-fx-background-color: white;");
        }
        label.setTextFill(Color.BLACK);

        RevertCellSettingsBtn.setDisable(true);
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)",
                Math.round(color.getRed() * 255),
                Math.round(color.getGreen() * 255),
                Math.round(color.getBlue() * 255));
    }

    public void setColors() {
        BackgroundColorPicker.setDisable(false);
        TextColorPicker.setDisable(false);
    }

    public void disableColorChange() {
        BackgroundColorPicker.setDisable(true);
        TextColorPicker.setDisable(true);
        RevertCellSettingsBtn.setDisable(true);
    }
}
