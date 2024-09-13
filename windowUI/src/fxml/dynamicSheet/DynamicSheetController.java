package fxml.dynamicSheet;

import cell.CellDTO;
import fxml.CellLabel;
import fxml.headline.HeadlineController;
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

    public void handleCellClick(CellLabel cell) {
        if(dynamicSheetBuilder.getCurrentClickedCell() != null){
            dynamicSheetBuilder.resetClickedLabel(dynamicSheetBuilder.getCurrentClickedCell(),
                    mainController.getSheetDTO().getCell(dynamicSheetBuilder.getCurrentClickedCell().getCoordinateDTO()));
        }

        CellDTO cellDTO = mainController.getSheetDTO().getCell(cell.getCoordinateDTO());

        dynamicSheetBuilder.setClickedLabel(cell ,cellDTO);
        dynamicSheetBuilder.setCurrentClickedCell(cell);
        headlineController.onCellLabelClicked(cellDTO);
    }

    public void setControllers(appController.appController controller, HeadlineController headlineController ){
        this.mainController = controller;
        this.headlineController = headlineController;
    }

    private void setCell(CellDTO cell) {
        Node node = dynamicSheet.lookup("#cell"+ cell.getCoordinate());
        if (node != null && node instanceof Label) {
            Label label = (Label) node;
            label.setText(getStringValue(cell));
        }
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
}