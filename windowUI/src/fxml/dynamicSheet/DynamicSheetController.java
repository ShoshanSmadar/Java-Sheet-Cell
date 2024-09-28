package fxml.dynamicSheet;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import fxml.labelCreator.CellLabel;
import fxml.headline.HeadlineController;
import fxml.labelCreator.header.ColumnLabel;
import fxml.labelCreator.header.RowLabel;
import fxml.sheetSetting.SheetSettingsController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DynamicSheetController {
    public FlowPane dynamicSheet;
    @FXML
    private FlowPane flowPane; // Ensure this matches the fx:id in the FXML
    private appController.appController mainController;
    private DynamicSheet dynamicSheetBuilder;
    private HeadlineController headlineController;
    private SheetSettingsController sheetSettingsController;

    public Boolean isDynamicSheetBuilderExist(){
        return (dynamicSheet != null);
    }


    public DynamicSheet getDynamicSheetBuilder() {
        return dynamicSheetBuilder;
    }

    public DynamicSheetController() {

    }

    @FXML
//    public void initialize() {
//        initializeSheet();
//    }

    public void initializeSheet(int rowSize, int rowHeight, int colSize, int colHeight) {
        if (dynamicSheet == null) {
            System.out.println("FlowPane is null!");
        } else {
            if(isDynamicSheetBuilderExist()){
                mainController.clearDynamicSheet();
            }
            dynamicSheetBuilder = new DynamicSheet(rowSize,  rowHeight,  colSize,  colHeight, this);
            dynamicSheet.getChildren().add((dynamicSheetBuilder.getGridPane()));
        }
        if(headlineController.makeAnimation()){
            makeGridPaneFirework();
        }

    }

    public GridPane getGridPane(){
        return dynamicSheetBuilder.getGridPane();
    }

    public Label getColumnLabel(int colNumber){
        return dynamicSheetBuilder.getColumnLabel((char) ('A' + (colNumber - 1)));
    }

    public void setSheetCells(SheetDTO sheet) {
        for(CellDTO cell : sheet.getCellMap().values()){
            setCell(cell);
        }
    }


    public CoordinateDTO getCurrentClickedCellCoordinateSTO(){
        return dynamicSheetBuilder.getCurrentClickedCell().getCoordinateDTO();
    }

    public CellLabel getCurrentClickedCellLabel(){
        return dynamicSheetBuilder.getCurrentClickedCell();
    }

    public void handleCellClick(CellLabel cell) {
        resetCurrentClickedLabels();
        dynamicSheetBuilder.setCurrentClickedCell(cell);
        CellDTO cellDTO = mainController.getSheetDTO().getCell(cell.getCoordinateDTO());
        dynamicSheetBuilder.setClickedLabel(cell ,cellDTO);
        headlineController.onCellLabelClicked(cellDTO, cell.getCoordinateDTO());
        sheetSettingsController.setColors();

    }

    public void setControllers(appController.appController controller,
                               HeadlineController headlineController,SheetSettingsController sheetSettingsController ){
        this.mainController = controller;
        this.headlineController = headlineController;
        this.sheetSettingsController = sheetSettingsController;
    }

    public void handleRangeClickedLabel(List<CoordinateDTO> range){
        resetCurrentClickedLabels();
        dynamicSheetBuilder.makeCellLabelRangeList(range);
    }

    private void setCell(CellDTO cell) {
        Node node = dynamicSheet.lookup("#cell"+ cell.getCoordinate());
        if (node != null && node instanceof Label) {
            CellLabel label = (CellLabel) node;
            label.setText(getStringValue(cell));
            label.setVisible(true);
            label.setStyle("-fx-font-size: 12px;");
        }
    }

    public void handleRowLabelPressed(RowLabel rowHeader) {
        resetCurrentClickedLabels();
        dynamicSheetBuilder.setCurrentClickedRowLabel(rowHeader);
        dynamicSheetBuilder.showRowLabeldClicked();
        rowHeader.getStyleClass().add("clicked-header");
        sheetSettingsController.setRowSpinnerCurValue((int) rowHeader.getHeight());
    }

    public void handleColumnLabelPressed(ColumnLabel columnHeader) {
        resetCurrentClickedLabels();
        dynamicSheetBuilder.setCurrentClickedColumnLabel(columnHeader);
        dynamicSheetBuilder.showColumnLabeldClicked();
        columnHeader.getStyleClass().add("clicked-header");
        sheetSettingsController.setColumnSpinnerCurValue((int) columnHeader.getWidth());
        sheetSettingsController.setAlignment();

    }


    private String getStringValue(CellDTO cell)
    {
        String cellValue;

        if (cell.getEffectiveValue() instanceof Double)
        {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

            DecimalFormat df = new DecimalFormat("#,###.##", symbols);
            cellValue = df.format(cell.getEffectiveValue());
        }
        else if (cell.getEffectiveValue() instanceof Boolean)
        {
            cellValue = cell.getEffectiveValue().toString().toUpperCase();
        }
        else
        {
            cellValue = (String) cell.getEffectiveValue();
        }

        return cellValue;
    }

    public ColumnLabel getCurrentClickedColumnLabel() {
        return dynamicSheetBuilder.getCurrentClickedColumnLabel();
    }

    public void setCurrentClickedColumnLabel(ColumnLabel currentClickedColumnLabel) {
        dynamicSheetBuilder.setCurrentClickedColumnLabel(currentClickedColumnLabel);
    }

    public RowLabel getCurrentClickedRowLabel() {
        return dynamicSheetBuilder.getCurrentClickedRowLabel();
    }

    public void setCurrentClickedRowLabel(RowLabel currentClickedRowLabel) {
        dynamicSheetBuilder.setCurrentClickedRowLabel(currentClickedRowLabel);
    }


    public void resetCurrentClickedLabels(){
        if(dynamicSheetBuilder.getCurrentClickedCell() != null){
            dynamicSheetBuilder.resetClickedLabel(dynamicSheetBuilder.getCurrentClickedCell(),
                    mainController.getSheetDTO().
                            getCell(dynamicSheetBuilder.getCurrentClickedCell().getCoordinateDTO()));
            sheetSettingsController.disableColorChange();
            headlineController.resetLabelClicked();
            setSheetCells(mainController.getSheetDTO());
        }
        if(dynamicSheetBuilder.getCurrentClickedRowLabel() != null){
            dynamicSheetBuilder.resetClickedRowLabel();
            sheetSettingsController.disableRowSpinner();
        }
        if(dynamicSheetBuilder.getCurrentClickedColumnLabel() != null){
            dynamicSheetBuilder.resetClickedColumnLabel();
            sheetSettingsController.disableColumnLengthSpinner();
            sheetSettingsController.anableAlignment();
        }
        if(dynamicSheetBuilder.getCurrentClickedRange() != null){
            dynamicSheetBuilder.resetCurrentClickedCellRange();
        }
    }

    public void updateLabelAlignment(String alignment) {
        switch (alignment) {
            case "Left":
                this.getCurrentClickedColumnLabel().changeColumnAlignment(TextAlignment.LEFT);
                break;
            case "Center":
                this.getCurrentClickedColumnLabel().changeColumnAlignment(TextAlignment.CENTER);
                break;
            case "Right":
                this.getCurrentClickedColumnLabel().changeColumnAlignment(TextAlignment.RIGHT);
                break;
        }
    }

    public void animateLabelPopOut() {
        // Move the label to the front so nothing overlaps it
        Label label = dynamicSheetBuilder.getCurrentClickedCell();
        label.toFront();

        // Create the scale transition for the "pop-out" effect
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), label);
        scaleTransition.setByX(1.2); // Grow 20% in width
        scaleTransition.setByY(1.2); // Grow 20% in height
        scaleTransition.setAutoReverse(true); // Shrink back to original size
        scaleTransition.setCycleCount(2); // Play forward and then backward (pop and return)

        // Create a Timeline to change the text color
        Timeline colorChangeTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> label.setTextFill(Color.RED)), // Change color to red at the start
                new KeyFrame(Duration.seconds(1), e -> label.setTextFill(Color.BLACK)) // Revert color to black after 1 second
        );
        colorChangeTimeline.setCycleCount(1); // Only one cycle

        // Play both transitions
        scaleTransition.play();
        colorChangeTimeline.play();

        // When the scale transition finishes, bring the label back to its original depth
        scaleTransition.setOnFinished(e -> {
            label.toBack();
        });
    }

    public void startLabelColorAnimation() {
        Timeline timeline = new Timeline();
        Random random = new Random();

        for (Node node : dynamicSheetBuilder.getSheetGridPane().getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                Paint originalColor = label.getTextFill(); // Store the original text color

                // Create a keyframe to change color
                KeyFrame changeColorFrame = new KeyFrame(Duration.seconds(0.5), e -> {
                    label.setTextFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                });

                // Create another keyframe to revert to the original color after 2 seconds
                KeyFrame revertColorFrame = new KeyFrame(Duration.seconds(2), e -> {
                    label.setTextFill(originalColor);
                });

                timeline.getKeyFrames().addAll(changeColorFrame, revertColorFrame);
            }
        }
        timeline.setCycleCount(1); // Run once
        timeline.play();
    }

    public void startGridPaneDance() {
        TranslateTransition dance = new TranslateTransition();
        dance.setDuration(Duration.millis(500)); // Speed of the dance
        dance.setNode(dynamicSheetBuilder.getSheetGridPane());
        dance.setByX(10); // Move by 10 pixels
        dance.setCycleCount(6); // Number of times to move back and forth
        dance.setAutoReverse(true); // Revert to the original position
        dance.play();
    }

    public void makeGridPaneFirework() {
        GridPane gridPane = dynamicSheetBuilder.getSheetGridPane();
        // Create a ScaleTransition to make the GridPane shrink (disappear)
        ScaleTransition shrinkTransition = new ScaleTransition(Duration.seconds(0.3), gridPane);
        shrinkTransition.setToX(0); // Shrink to 0 in X (disappear horizontally)
        shrinkTransition.setToY(0); // Shrink to 0 in Y (disappear vertically)

        // Create a ScaleTransition to make the GridPane grow (pop up)
        ScaleTransition popUpTransition = new ScaleTransition(Duration.seconds(0.3), gridPane);
        popUpTransition.setToX(1); // Grow back to its original width
        popUpTransition.setToY(1); // Grow back to its original height

        // Create a FadeTransition to fade out the GridPane
        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(0.2), gridPane);
        fadeOutTransition.setToValue(0); // Fully invisible

        // Create a FadeTransition to fade in the GridPane
        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.2), gridPane);
        fadeInTransition.setToValue(1); // Fully visible

        // Combine all transitions into a sequential order
        SequentialTransition sequentialTransition = new SequentialTransition(
                fadeOutTransition, shrinkTransition, popUpTransition, fadeInTransition
        );

        // Set the number of cycles for the animation (e.g., 3 times)
        sequentialTransition.setCycleCount(3); // You can adjust this to make the fireworks repeat

        // Start the animation
        sequentialTransition.play();
    }
}