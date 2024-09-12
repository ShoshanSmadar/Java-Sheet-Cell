package fxml.dynamicSheet;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import sheet.SheetDTO;

public class DynamicSheetController {
    public FlowPane dynamicSheet;
    @FXML
    private FlowPane flowPane; // Ensure this matches the fx:id in the FXML
    private appController.appController mainController;

    public DynamicSheetController() {
        // Constructor logic, if any
    }

    @FXML
    public void initialize() {
        initializeSheet();
    }

    public void initializeSheet() {
        if (dynamicSheet == null) {
            System.out.println("FlowPane is null!");
        } else {
            DynamicSheet newSheet = new DynamicSheet(10,30 ,5,50);
            dynamicSheet.getChildren().add((newSheet.getGridPane()));
        }
    }

    public void setSheetCells(SheetDTO sheet) {
        for(CellDTO cell : sheet.getCellMap().values()){
            setCell(cell);
        }
    }

    public void handleCellClick(Label cellLabl) {
        SheetDTO sheet = mainController.getSheetDTO();

    }

    public void setAppControler(appController.appController controller){
        this.mainController = controller;
    }

    private void setCell(CellDTO cell) {
        Node node = dynamicSheet.lookup("#cell"+ cell.getCoordinate());
        node.
        if (node != null && node instanceof Label) {
            Label label = (Label) node;
            label.setText(cell.getOriginalValue());
            for(CoordinateDTO cellAfected : cell.getAffecting()){
                Node nodeCellAfected = dynamicSheet.lookup("#cell"+ cellAfected);
                nodeCellAfected.getStyleClass().add("cell" + cell.getCoordinate() + "Affected");
            }
            for(CoordinateDTO cellDepending : cell.getAffectedBy()){
                Node nodeCellDepending = dynamicSheet.lookup("#cell"+ cellDepending);
                nodeCellDepending.getStyleClass().add("cell" + cell.getCoordinate() + "Depending");
            }
        }
    }
}