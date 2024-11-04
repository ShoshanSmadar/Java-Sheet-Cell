package sheetShower.fxml.popUpFiltter;

import sheetShower.UIconstant.TextConstants;
import cell.CellDTO;
import coordinate.CoordinateDTO;
import sheetShower.fxml.labelCreator.CellLabel;
import sheetShower.fxml.labelCreator.header.ColumnLabel;
import sheetShower.fxml.labelCreator.header.RowLabel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class FilterContoller {
    //private Engine engine;
    private String valueToFilterBy;
    private GridPane gridPane;
    @FXML
    private Label choosenValuesShower;

    @FXML
    private ChoiceBox<Character> columnFilterChoser;

    @FXML
    private TextField filterEreaChooser;

    @FXML
    private Tooltip filterHelp;

    @FXML
    private Label filtterEreaExplenetion;

    @FXML
    private Button startFilterBtn;

    @FXML
    private Button submitFillterEreaBtn;

    @FXML
    private ChoiceBox<String> valueToFillterBy;

    @FXML
    private AnchorPane sheetAnchorPane;

    @FXML
    void FilterRange(ActionEvent event) {
        //engine.filter();
        //addSheet(engine.getNewFilterdSheet());
        valueToFillterBy.setDisable(true);
        startFilterBtn.setDisable(true);
    }

    @FXML
    void popHelpMessege(MouseEvent event) {

    }

    @FXML
    void stopHelpMessege(MouseEvent event) {

    }

    @FXML
    void initialize() {
        filterHelp.setText(TextConstants.EREA_CHOOSING_EXPLANATION);
        columnFilterChoser.setOnAction(event -> columnFilterChosen());
        valueToFillterBy.setOnAction(event -> filtterValueChosen());
    }

    private void filtterValueChosen() {
        String value = valueToFillterBy.getSelectionModel().getSelectedItem();
        if(choosenValuesShower.getText().isEmpty()){
            choosenValuesShower.setText(value);
        }
        else {
            choosenValuesShower.setText(choosenValuesShower.getText() + ", " + value);
        }
        valueToFillterBy.getItems().remove(value);

        //engine.setFilterBy(value);
        startFilterBtn.setDisable(false);
    }

    @FXML
    void submitBtnPressed(ActionEvent event) {
        /*String range = filterEreaChooser.getText();
        try{
            engine.setRowsToFilter(range);
            List<Character> possibleColumns = engine.getColumnsToFiltter();
            if(possibleColumns == null || possibleColumns.size() == 0) {
                filterEreaChooser.setText("Range is empty, try again");
            }
            else{
                for(Character column : possibleColumns) {
                    columnFilterChoser.getItems().add(column);
                }
                columnFilterChoser.setDisable(false);
            }
            filterEreaChooser.setDisable(true);
            submitFillterEreaBtn.setDisable(true);
        }
        catch(Exception e){
            filterEreaChooser.setText("Range given is incorrect, try again");
        }*/
    }

    private void columnFilterChosen(){
        /*Character selectedOption = columnFilterChoser.getSelectionModel().getSelectedItem();
        engine.setColToFilterBy(selectedOption);
        List<String> possibleValues = engine.getValueTypesPossibleInColumn();
        for(String possibleValue : possibleValues) {
            valueToFillterBy.getItems().add(possibleValue);
        }
        valueToFillterBy.setDisable(false);
        columnFilterChoser.setDisable(true);*/
    }

    /*public void setEngine(Engine engine) {
        this.engine = engine;
        addSheet(engine.getSheetDTO());
    }*/

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
}
