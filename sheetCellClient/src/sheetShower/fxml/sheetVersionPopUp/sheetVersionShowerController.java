package sheetShower.fxml.sheetVersionPopUp;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import coordinate.CoordinateDTO;
import javafx.scene.control.Alert;
import okhttp3.Request;
import okhttp3.Response;
import range.RangeDTO;
import sheetShower.fxml.labelCreator.CellLabel;
import sheetShower.fxml.labelCreator.header.ColumnLabel;
import sheetShower.fxml.labelCreator.header.RowLabel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static constants.Constants.GET_OLD_SHEET_VERSION_PATH;
import static http.HttpClientUtil.HTTP_CLIENT;

public class sheetVersionShowerController {
    String sheetName;
    int numberOfVersions;

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

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    private void showVersion() {
        int selectedVersion = versionChooser.getValue();
        addSheet(getOldVersionSheetFromServer(selectedVersion));
        //addSheet(engine.getOldVersionSheet(selectedVersion));
        sheetVersionTxb.setText("Sheet Version "+selectedVersion);
    }

    private SheetDTO getOldVersionSheetFromServer(int version) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();
        String urlString = GET_OLD_SHEET_VERSION_PATH + "?sheetName=" + sheetName + "&versionNumber=" + version;
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                SheetDTO sheetDTO = gson.fromJson(jsonResponse, SheetDTO.class);
                return sheetDTO;
            } else {
                String errorResponse = response.body() != null ? response.body().string() : "No error message";
                throw new RuntimeException(errorResponse);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
    }

    public void addSheet(SheetDTO sheet){
        if (sheet.getSheetName() == null) {
            return;
        }
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

    public void setNumberOfVersions(int NumberOfVersions) {
        numberOfVersions = NumberOfVersions;
        for (int i = 0; i < numberOfVersions - 1; i++) {
            versionChooser.getItems().add(i + 1);
        }

        versionChooser.setDisable(false);
    }
}
