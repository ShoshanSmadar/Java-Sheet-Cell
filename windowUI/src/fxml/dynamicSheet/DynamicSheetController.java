package fxml.dynamicSheet;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import fxml.labelCreator.CellLabel;
import fxml.headline.HeadlineController;
import fxml.labelCreator.header.ColumnLabel;
import fxml.labelCreator.header.RowLabel;
import fxml.sheetSetting.SheetSettingsController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
    }

    public void setControllers(appController.appController controller, HeadlineController headlineController,SheetSettingsController sheetSettingsController ){
        this.mainController = controller;
        this.headlineController = headlineController;
        this.sheetSettingsController = sheetSettingsController;
    }

    private void setCell(CellDTO cell) {
        Node node = dynamicSheet.lookup("#cell"+ cell.getCoordinate());
        if (node != null && node instanceof Label) {
            Label label = (Label) node;
            label.setText(getStringValue(cell));
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

    public void handlerHeaderClick(RowLabel row) {
        setCurrentClickedRowLabel(row);
    }

    public void handlecolumnHeaderClick(ColumnLabel column) {
        setCurrentClickedColumnLabel(column);

    }

    public void resetCurrentClickedLabels(){
        if(dynamicSheetBuilder.getCurrentClickedCell() != null){
            dynamicSheetBuilder.resetClickedLabel(dynamicSheetBuilder.getCurrentClickedCell(),
                    mainController.getSheetDTO().
                            getCell(dynamicSheetBuilder.getCurrentClickedCell().getCoordinateDTO()));
        }
        if(dynamicSheetBuilder.getCurrentClickedRowLabel() != null){
            dynamicSheetBuilder.resetClickedRowLabel();
            sheetSettingsController.disableRowSpinner();
        }
        if(dynamicSheetBuilder.getCurrentClickedColumnLabel() != null){
            dynamicSheetBuilder.resetClickedColumnLabel();
            sheetSettingsController.disableColumnLengthSpinner();
        }
    }


}