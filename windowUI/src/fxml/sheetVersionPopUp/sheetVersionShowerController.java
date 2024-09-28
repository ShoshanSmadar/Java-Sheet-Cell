package fxml.sheetVersionPopUp;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import engine.Engine;
import fxml.labelCreator.CellLabel;
import fxml.labelCreator.header.ColumnLabel;
import fxml.labelCreator.header.RowLabel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class sheetVersionShowerController {
    Engine engine;
    @FXML
    private AnchorPane sheetAnchorPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label sheetVersionTxb;

    @FXML
    private ChoiceBox<Integer> versionChooser;

    @FXML
    public void initialize() {
        versionChooser.setOnAction(event -> showVersion());
    }

    private void showVersion() {
        int selectedVersion = versionChooser.getValue();
        addSheet(engine.getOldVersionSheet(selectedVersion));
        sheetVersionTxb.setText("Sheet Version "+selectedVersion);
    }

    public void addSheet(SheetDTO sheet){
        if(gridPane != null){
            sheetAnchorPane.getChildren().clear();
        }

        gridPane = new GridPane();

        initializeSheet(gridPane, sheet.getSizeOfRows(), sheet.getHeightOfRow(),
                sheet.getSizeOfColumns(), sheet.getLengthOfCol());
        for(CellDTO cell : sheet.getCellMap().values()){
            setCell(cell);
        }
        sheetAnchorPane.getChildren().add(gridPane);
    }

    private void setCell(CellDTO cell) {
        Node node = gridPane.lookup("#cell"+ cell.getCoordinate());
        setLabelText(node, getStringValue(cell));
    }

    private void setLabelText(Node node, String text) {
        if (node != null && node instanceof Label) {
            Label label = (Label) node;
            label.setText(text);
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

    private void initializeSheet(GridPane gridPane, int rows, int rowHeight, int columns, int columnWidth) {
        // Add column headers
        for (int col = 1; col <= columns; col++) {
            gridPane.add(createColumnHeader(col, rowHeight,columnWidth ), col, 0);
        }

        // Add row headers
        for (int row = 1; row <= rows; row++) {
            gridPane.add(createRowHeader(row, rowHeight, columnWidth), 0, row);
        }

        // Add cells
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                gridPane.add(createCellLabel(row, rowHeight, col, columnWidth), col, row);
            }
        }
    }

    private Label createColumnHeader(int col, int rowHight, int columnWidth) {
        ColumnLabel columnHeader = new ColumnLabel(Character.toString((char) ('A' + col - 1)));
        SetLabelExactSize(columnHeader, rowHight, columnWidth);
        columnHeader.getStyleClass().add("header-cell");

        return columnHeader;
    }

    private Label createRowHeader(int row, int rowHight, int columnWidth) {
        RowLabel rowHeader = new RowLabel(Integer.toString(row));
        SetLabelExactSize(rowHeader, rowHight, columnWidth);
        rowHeader.getStyleClass().add("header-cell");

        return rowHeader;
    }

    private void SetLabelExactSize(Label label, int rowHeight, int columnWidth) {
        label.setMinWidth(columnWidth);
        label.setMinHeight(rowHeight);
        label.setMaxWidth(columnWidth);
        label.setMaxHeight(rowHeight);

    }

    private Label createCellLabel( int row,int rowHight, int col, int columnWidth){
        CellLabel cell = new CellLabel(new CoordinateDTO(row -1, col -1));
        SetLabelExactSize(cell, rowHight, columnWidth);
        cell.getStyleClass().add("data-cell");//// Add CSS cell class
        cell.setId("cell" + (char) ('A' + col - 1) + Integer.toString(row)); //
        cell.getStyleClass().add("column" + (char) ('A' + col - 1)); // add CSS column class
        cell.getStyleClass().add("row" + row);

        // Apply alternating row colors
        if (row % 2 == 0) {
            cell.getStyleClass().add("even-row");
        } else {
            cell.getStyleClass().add("odd-row");
        }
        return cell;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        Integer numberOfVersions = engine.getSheetCurrentVersion();
        for (int i = 0; i < numberOfVersions; i++) {
            versionChooser.getItems().add(i + 1);
        }

        versionChooser.setDisable(false);
    }
}
