package sheetShower.fxml.dynamicAnalysis;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import coordinate.CoordinateDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import okhttp3.*;
import range.RangeDTO;
import sheet.SheetDTO;
import sheetShower.fxml.dynamicSheet.DynamicSheetController;
import sheetShower.fxml.labelCreator.CellLabel;
import sheetShower.fxml.labelCreator.header.ColumnLabel;
import sheetShower.fxml.labelCreator.header.RowLabel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static constants.Constants.*;
import static http.HttpClientUtil.HTTP_CLIENT;

public class DynamicAnalysisController {
    @FXML
    private GridPane showCellOptionsGridPane;
    @FXML
    private ScrollPane sheetScrollPane;
    @FXML
    private VBox sheetVbox;
    @FXML
    private Button startFilterBtn;

    private GridPane gridPane;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
            .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
            .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
            .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
            .create();

    private List<CellDTO> cellDTOList;
    //private DynamicSheetController DynamicSheetController;
    private SheetDTO sheet;
    private String userName;

    @FXML
    void FilterRange(ActionEvent event) {
        String cellsJson = gson.toJson(cellDTOList);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("cells", cellsJson)
                .addFormDataPart("userName", userName)
                .addFormDataPart("sheetName", sheet.getSheetName())
                .build();

        Request request = new Request.Builder()
                .url(DO_DYNAMIC_ANALYSIS_PATH)
                .post(requestBody)
                .build();

        HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse the response JSON into SheetDTO
                    sheet = gson.fromJson(response.body().string(), SheetDTO.class);
                    Platform.runLater(() -> reEnterSheet());
                } else {
                    System.err.println("Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void reEnterSheet(){
        sheetVbox.getChildren().clear();
        addSheet();
    }

    public void initializeCellOptions(SheetDTO sheet, String userName) {
        this.sheet = sheet;
        this.userName = userName;
        cellDTOList = getCellsFromServer();
        if (cellDTOList != null) {
            populateGridPane();
            addSheet();
        }
        else{
            startFilterBtn.setDisable(true);
        }
    }

    private List<CellDTO> getCellsFromServer(){
        String fullUrl = START_DYNAMIC_ANALYSIS_PATH + "?sheetName=" + sheet.getSheetName() + "&userName=" + userName + "&currentVersion=" + sheet.getAcurateSheetVersion();
        String jsonData = "{\"userName\":\"" + userName + "\",\"sheetName\":\"" + sheet.getSheetName() + "\",\"currentVersion\":" + sheet.getAcurateSheetVersion() + "}";
        RequestBody body = RequestBody.create(jsonData, MediaType.get("application/json; charset=utf-8"));

        // Create the request
        Request request = new Request.Builder()
                .url(fullUrl)
                .post(body)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Type cellListType = new TypeToken<List<CellDTO>>() {}.getType();
                    return gson.fromJson(jsonResponse, cellListType);
                }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong");
                alert.showAndWait();
                return null;
            }
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
    }

    /*
    *  String sheetName = request.getParameter("sheetName");
        String username = request.getParameter("username");
        int currentVersion = Integer.parseInt(request.getParameter("currentVersion"));*/

    private void populateGridPane() {
        showCellOptionsGridPane.getChildren().clear(); // Clear existing cells if needed

        // Set row and column spacing
        showCellOptionsGridPane.setVgap(10);  // Adjust vertical gap between rows
        showCellOptionsGridPane.setPadding(new Insets(10, 0, 0, 0)); // Extra space at the top

        for (int i = 0; i < cellDTOList.size(); i++) {
            CellDTO cell = cellDTOList.get(i);

            // Create a label with the cell name, and make it bold
            Label cellLabel = new Label(cell.getCoordinate().toString());
            cellLabel.setStyle("-fx-font-weight: bold;");  // Make the coordinate bold
            GridPane.setConstraints(cellLabel, 0, i);

            // Create a slider with the cell value as the center
            double minValue = Integer.parseInt(cell.getOriginalValue()) - 100;
            double maxValue = Integer.parseInt(cell.getOriginalValue()) + 100;
            Slider cellSlider = new Slider(minValue, maxValue, Integer.parseInt(cell.getOriginalValue()));

            // Configure the slider properties
            cellSlider.setShowTickMarks(true);
            cellSlider.setShowTickLabels(true);
            cellSlider.setMajorTickUnit(50);
            cellSlider.setMinorTickCount(4);
            GridPane.setConstraints(cellSlider, 1, i);

            // Update CellDTO value when slider is moved
            cellSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
                cell.setOriginalValue(String.valueOf(newValue.doubleValue()));
                startFilterBtn.setDisable(false);
            });

            // Add label and slider to the grid pane
            showCellOptionsGridPane.getChildren().addAll(cellLabel, cellSlider);
        }
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

    public void addSheet(){
        gridPane = new GridPane();

        initializeSheet(gridPane, sheet.getSizeOfRows(), sheet.getHeightOfRow(),
                sheet.getSizeOfColumns(), sheet.getLengthOfCol());
        for(CellDTO cell : sheet.getCellMap().values()){
            setCell(cell);
        }
        sheetVbox.getChildren().add(gridPane);
    }

    private void setCell(CellDTO cell) {
        Node node = gridPane.lookup("#cell"+ cell.getCoordinate());
        setLabelText(node, getStringValue(cell));
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

