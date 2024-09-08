package fxml.dynamicSheet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DynamicSheet extends Application {

    private GridPane gridPane;
    public DynamicSheet(int rowSize, int rowHeight, int colSize, int colHeight) {
        gridPane = new GridPane();
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
        scene.getStylesheets().add(getClass().getResource("dynamic-sheet-style.css").toExternalForm()); // Add CSS
        primaryStage.setTitle("Dynamic Sheet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeSheet(GridPane gridPane, int rows,int rowHight, int columns, int columnWidth) {
        // Add column headers (A, B, C, ...)
        for (int col = 1; col <= columns; col++) {
            Label columnHeader = new Label(Character.toString((char) ('A' + col - 1)));
            columnHeader.getStyleClass().add("header-cell");
            gridPane.add(columnHeader, col, 0);
        }

        // Add row headers (1, 2, 3, ...)
        for (int row = 1; row <= rows; row++) {
            Label rowHeader = new Label(Integer.toString(row));
            rowHeader.getStyleClass().add("header-cell");
            gridPane.add(rowHeader, 0, row);
        }

        // Add interactive cells
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                Label cell = new Label();
                cell.setMinWidth(50); // Set minimum width
                cell.setMinHeight(30); // Set minimum height
                cell.getStyleClass().add("data-cell"); // Add CSS class

                // Apply alternating row colors
                if (row % 2 == 0) {
                    cell.getStyleClass().add("even-row");
                } else {
                    cell.getStyleClass().add("odd-row");
                }

                // Add mouse click event handler
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCellClick(cell));
                gridPane.add(cell, col, row);
            }
        }
    }



    // Method to handle cell click events
    private void handleCellClick(Label cell) {
        // Example: Toggle cell text between empty and "Clicked!" and apply a clicked style
        if (cell.getText().isEmpty()) {
            cell.setText("Clicked!");
            cell.getStyleClass().add("clicked-cell");
        } else {
            cell.setText("");
            cell.getStyleClass().remove("clicked-cell");
        }
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
}