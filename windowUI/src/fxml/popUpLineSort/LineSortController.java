package fxml.popUpLineSort;

import UIconstant.TextConstants;
import cell.CellDTO;
import constant.Constants;
import coordinate.CoordinateDTO;
import engine.Engine;
import fxml.labelCreator.CellLabel;
import fxml.labelCreator.header.ColumnLabel;
import fxml.labelCreator.header.RowLabel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class LineSortController {
    private Engine engine;
    private GridPane gridPane;

    @FXML
    private Label chosenColumnsShower;

    @FXML
    private ChoiceBox<Character> columnFilterChooser;

    @FXML
    private TextField filterEreaChooser;

    @FXML
    private Tooltip filterHelp;

    @FXML
    private Label filtterEreaExplenetion;

    @FXML
    private Button startSortBtn;

    @FXML
    private Button submitFillterEreaBtn;

    @FXML
    private AnchorPane sheetAnchorPane;



    @FXML
    void popHelpMessege(MouseEvent event) {

    }

    @FXML
    void stopHelpMessege(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        filterHelp.setText(TextConstants.EREA_CHOOSING_EXPLANATION);
    }

    @FXML
    void submitBtnPressed(ActionEvent event) {
        String range = filterEreaChooser.getText();
        engine.setSortingRange(range);
        List<Character> possibleColumns = engine.getPossibleSortingColumns();
        if(possibleColumns == null || possibleColumns.size() == 0) {
            filterEreaChooser.setText("Range is empty, try again");
        }
        else{
            for(Character column : possibleColumns) {
                columnFilterChooser.getItems().add(column);
            }
            columnFilterChooser.setDisable(false);
        }
    }




    public void setEngine(Engine engine) {
        this.engine = engine;
        addSheet();
    }

    public void addSheet(){
        SheetDTO sheet = engine.getSheetDTO();
        engine.startLineSorter();
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
        cell.getStyleClass().add("data-cell");// Add CSS cell class
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

}

