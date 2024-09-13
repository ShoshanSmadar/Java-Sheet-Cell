package fxml.dynamicSheet;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import fxml.CellLabel;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

public class DynamicSheet extends Application {

    private GridPane gridPane;
    private int row, rowHeight, column, columnHeight;
    private CellLabel currentClickedCell;
    private DynamicSheetController controller;

    public DynamicSheet(int rowSize, int rowHeight, int colSize, int colHeight, DynamicSheetController controller) {
        this.gridPane = new GridPane();
        this.row = rowSize;
        this.rowHeight = rowHeight;
        this.column = colSize;
        this.columnHeight = colHeight;
        this.controller = controller;
        initializeSheet(gridPane, rowSize,rowHeight, colSize, colHeight);
    }

    public javafx.scene.layout.GridPane getGridPane() {
        return gridPane;
    }

    @Override
    public void start(Stage primaryStage) {
        gridPane = new GridPane();
        initializeSheet(gridPane, 10,0 ,5,0); // Initialize grid with 10 rows and 5 columns

        Scene scene = new Scene(gridPane, 600, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("dynamic-sheet-style.css")).toExternalForm()); // Add CSS
        primaryStage.setTitle("Dynamic Sheet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeSheet(GridPane gridPane, int rows,int rowHeight, int columns, int columnWidth) {
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
        Label columnHeader = new Label(Character.toString((char) ('A' + col - 1)));
        SetLabelExactSize(columnHeader, rowHight, columnWidth);
        columnHeader.getStyleClass().add("header-cell");
        columnHeader.setId("header-column" + (char) ('A' + col - 1));
        return columnHeader;
    }



    private Label createRowHeader(int row, int rowHight, int columnWidth) {
        Label rowHeader = new Label(Integer.toString(row));
        SetLabelExactSize(rowHeader, rowHight, columnWidth);
        rowHeader.getStyleClass().add("header-cell");
        rowHeader.setId("header-row" + row);
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
        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.handleCellClick(cell));
        return cell;
    }



    // Method to handle cell click events
    private void handleCellClick(CellLabel cell) {


    }

    public void resetClickedLabel(CellLabel currentClickedLabel,CellDTO cell) {
        currentClickedLabel.getStyleClass().remove("clicked-cell");
        if(cell != null){
            for(CoordinateDTO cellAffected : cell.getAffecting()){
                Node affectedCell = gridPane.lookup("#cell" + cellAffected);
                affectedCell.getStyleClass().remove("affected-cell");
            }
            for(CoordinateDTO cellDepending : cell.getAffectedBy()){
                Node DependingCell = gridPane.lookup("#cell" + cellDepending);
                DependingCell.getStyleClass().remove("depending-cell");
            }
        }
    }

    public void setClickedLabel(CellLabel currentClickedLabel, CellDTO cell) {
        currentClickedLabel.getStyleClass().add("clicked-cell");
        if(cell != null){
            for(CoordinateDTO cellAffected : cell.getAffecting()){
                Node affectedCell = gridPane.lookup("#cell" + cellAffected);
                affectedCell.getStyleClass().add("affected-cell");
            }
            for(CoordinateDTO cellDepending : cell.getAffectedBy()){
                Node DependingCell = gridPane.lookup("#cell" + cellDepending);
                DependingCell.getStyleClass().add("depending-cell");
            }
        }
    }



    private boolean hasStyleClass(Label label, String styleClass) {
        return label.getStyleClass().contains(styleClass);
    }

    // Method to programmatically update the content of a specific cell
    public void updateCellContent(int row, int column, String content) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                if (node instanceof Label) {
                    ((Label) node).setText(content);
                }
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public CellLabel getCurrentClickedCell() {
        return currentClickedCell;
    }

    public void setCurrentClickedCell(CellLabel currentClickedCell) {
        this.currentClickedCell = currentClickedCell;
    }
}