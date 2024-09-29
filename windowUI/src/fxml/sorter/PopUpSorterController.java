package fxml.sorter;

import fxml.dynamicSheet.DynamicSheetController;
import fxml.popUpFiltter.FilterContoller;
import fxml.popUpLineSort.LineSortController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PopUpSorterController {
    @FXML
    private Button filtterLineBtn;
    @FXML
    private Button sortLineBtn;
    @FXML
    private Button makeGraphBtn;
    @FXML
    private appController.appController mainController;
    private DynamicSheetController dynamicSheetController;


    @FXML
    void HandleMakeGraphBtnPressed(ActionEvent event) {
        showGraphDialog();
    }

    public void addDynamicSheetController(DynamicSheetController dynamicSheetController) {
        this.dynamicSheetController = dynamicSheetController;
    }

    @FXML
    public void initialize() {
        sortLineBtn.setDisable(true);
        filtterLineBtn.setDisable(true);
        makeGraphBtn.setDisable(true);
    }

    public void setMainController(appController.appController mainController) {
        this.mainController = mainController;
    }

    public void enableButtons(){
        sortLineBtn.setDisable(false);
        filtterLineBtn.setDisable(false);
        makeGraphBtn.setDisable(false);
    }

    @FXML
    void HandleFiltterLinePressed(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/popUpFiltter/Filtter.fxml"));
            Parent popupRoot = fxmlLoader.load();

            FilterContoller filterContoller = fxmlLoader.getController();
            filterContoller.setEngine(mainController.getEngine());
            mainController.getEngine().setLineFilter();

            // Create a new stage (popup window)
            Stage popupStage = new Stage();

            // Set the modality (optional) - makes the popup block interaction with the main window
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the title of the popup window
            popupStage.setTitle("filter Lines");


            // Set the scene for the popup
            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.showAndWait();  // Show and wait will block the parent window until the popup is closed

        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception properly in your app
        }
    }

    @FXML
    void handleSortLinePressed(ActionEvent event) {
        try {
            // Load the FXML file for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/popUpLineSort/LineSort.fxml"));
            Parent popupRoot = fxmlLoader.load();

            LineSortController lineSortController = fxmlLoader.getController();
            lineSortController.setEngine(mainController.getEngine());

            // Create a new stage (popup window)
            Stage popupStage = new Stage();

            // Set the modality (optional) - makes the popup block interaction with the main window
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Set the title of the popup window
            popupStage.setTitle("Sort Line");


            // Set the scene for the popup
            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.showAndWait();  // Show and wait will block the parent window until the popup is closed

        } catch (IOException e) {
            e.printStackTrace();  // Handle the exception properly in your app
        }
    }

    //Graphs

    private String getColumnLabel(int columnIndex) {
        // Convert column index to a letter label (A, B, C, etc.)
        return String.valueOf((char) ('A' + columnIndex));
    }

    private void showGraphDialog() {
        // Create a dialog to let the user select columns and graph type
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Graph Selection");

        // Create combo boxes for X (columns) and Y (rows)
        ComboBox<String> xColumnComboBox = new ComboBox<>();
        ComboBox<String> yRowComboBox = new ComboBox<>();

        // Populate the xColumnComboBox with column headers (assuming you have column labels)
        for (int col = 0; col < dynamicSheetController.getGridPane().getColumnCount() - 1; col++) {
            String columnLabel = getColumnLabel(col);  // Your method to get column label
            xColumnComboBox.getItems().add(columnLabel);
        }

        // Populate the yRowComboBox with row numbers
        for (int row = 1; row < dynamicSheetController.getGridPane().getRowCount(); row++) {  // Assuming the first row is a header
            yRowComboBox.getItems().add("Row " + row);  // Optionally format row labels
        }

        // Create a ComboBox for selecting the graph type
        ComboBox<String> graphTypeComboBox = new ComboBox<>();
        graphTypeComboBox.getItems().addAll("Line Chart", "Bar Chart", "Scatter Chart");

        // Set up the dialog layout
        GridPane dialogGrid = new GridPane();
        dialogGrid.add(new Label("Select X Column:"), 0, 0);
        dialogGrid.add(xColumnComboBox, 1, 0);
        dialogGrid.add(new Label("Select Y Row:"), 0, 1);
        dialogGrid.add(yRowComboBox, 1, 1);
        dialogGrid.add(new Label("Select Graph Type:"), 0, 2);
        dialogGrid.add(graphTypeComboBox, 1, 2);

        dialog.getDialogPane().setContent(dialogGrid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String xColumn = xColumnComboBox.getValue();
                String yRow = yRowComboBox.getValue();
                String graphType = graphTypeComboBox.getValue();
                createGraph(xColumn, yRow, graphType);
            }
        });
    }

    private void createGraph(String xColumn, String yRow, String graphType) {
        if (graphType.equals("Line Chart")) {
            createLineChart(xColumn, yRow);
        } else if (graphType.equals("Bar Chart")) {
            createBarChart(xColumn, yRow);
        } else if (graphType.equals("Scatter Chart")) {
            createScatterChart(xColumn, yRow);
        }
    }

    private void createBarChart(String xColumn, String yRow) {
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setTitle("Bar Chart");

        // Set axis labels
        barChart.getXAxis().setLabel(xColumn);
        barChart.getYAxis().setLabel(yRow);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        for (int row = 1; row < dynamicSheetController.getGridPane().getRowCount(); row++) {
            String xValue = getCellValue(xColumn, row);  // Retrieve the x value based on the column label
            double yValue = getRowValue(row, getRowIndexFromLabel(yRow)); // Get y value from row

            if (!Double.isNaN(yValue) && !xValue.isEmpty()) {
                series.getData().add(new XYChart.Data<>(xValue, yValue));
            }
        }

        barChart.getData().add(series);
        showGraphInNewWindow(barChart);
    }

    private String getCellValue(String columnLabel, int row) {
        int columnIndex = getColumnIndexFromLabel(columnLabel);
        return getCellText(columnIndex, row + 1);  // Adjust for header offset
    }

    private String getCellText(int columnIndex, int rowIndex) {
        Label label = (Label) getNodeFromGridPane(dynamicSheetController.getGridPane(), columnIndex + 1, rowIndex);
        return (label != null) ? label.getText() : "";
    }

    private void createLineChart(String xColumn, String yRow) {
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setTitle("Line Chart");

        lineChart.getXAxis().setLabel(xColumn);
        lineChart.getYAxis().setLabel(yRow);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        for (int row = 1; row < dynamicSheetController.getGridPane().getRowCount(); row++) {
            double xValue = getColumnValue(xColumn, row);
            double yValue = getRowValue(row, getRowIndexFromLabel(yRow));

            if (!Double.isNaN(xValue) && !Double.isNaN(yValue)) {
                series.getData().add(new XYChart.Data<>(xValue, yValue));
            }
        }

        lineChart.getData().add(series);
        showGraphInNewWindow(lineChart);
    }

    private void createScatterChart(String xColumn, String yRow) {
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
        scatterChart.setTitle("Scatter Chart");

        scatterChart.getXAxis().setLabel(xColumn);
        scatterChart.getYAxis().setLabel(yRow);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        for (int row = 1; row < dynamicSheetController.getGridPane().getRowCount(); row++) {
            double xValue = getColumnValue(xColumn, row);
            double yValue = getRowValue(row, getRowIndexFromLabel(yRow));

            if (!Double.isNaN(xValue) && !Double.isNaN(yValue)) {
                series.getData().add(new XYChart.Data<>(xValue, yValue));
            }
        }

        scatterChart.getData().add(series);
        showGraphInNewWindow(scatterChart);
    }

    private double getColumnValue(String columnLabel, int row) {
        int columnIndex = getColumnIndexFromLabel(columnLabel);
        Label label = (Label) getNodeFromGridPane(dynamicSheetController.getGridPane(), columnIndex + 1, row + 1);

        if (label != null) {
            String value = label.getText();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return Double.NaN;  // Return NaN if the value is not numeric
            }
        }
        return Double.NaN;  // Return NaN if the label is not found
    }

    private double getRowValue(int row, int columnIndex) {
        Label label = (Label) getNodeFromGridPane(dynamicSheetController.getGridPane(), columnIndex + 1, row + 1);

        if (label != null) {
            String value = label.getText();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return Double.NaN;  // Return NaN if the value is not numeric
            }
        }
        return Double.NaN;  // Return NaN if the label is not found
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;  // Return null if no node is found at the specified position
    }

    private int getColumnIndexFromLabel(String columnLabel) {
        return columnLabel.charAt(0) - 'A';  // Map the column label (e.g., "A") to the column index
    }

    private int getRowIndexFromLabel(String rowLabel) {
        for (int i = 1; i < dynamicSheetController.getGridPane().getRowCount(); i++) {
            if (("Row " + i).equals(rowLabel)) {
                return i;
            }
        }
        return -1;  // Return -1 if not found
    }

    private void showGraphInNewWindow(Chart chart) {
        Stage stage = new Stage();
        VBox vbox = new VBox(chart);
        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


}
