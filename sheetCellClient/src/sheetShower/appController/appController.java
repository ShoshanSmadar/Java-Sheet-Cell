package sheetShower.appController;

import adapter.CellDTOAdapter;
import adapter.CoordinateDTOAdapter;
import adapter.RangeDTOAdapter;
import adapter.SheetDTOAdapter;
import cell.CellDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import coordinate.CoordinateDTO;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import range.RangeDTO;
import sheetShower.fxml.dynamicSheet.DynamicSheetController;
import sheetShower.fxml.headline.HeadlineController;
import sheetShower.fxml.rangeSettings.RangeSettingController;
import sheetShower.fxml.sheetSetting.SheetSettingsController;
import sheetShower.fxml.sorter.PopUpSorterController;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static constants.Constants.*;
import static http.HttpClientUtil.HTTP_CLIENT;

public class appController {
    SheetDTO currentSheet;
    String sheetName;
    boolean writingPermission;
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane headline;
    @FXML private HeadlineController headlineController;
    @FXML private FlowPane dynamicSheet;
    @FXML private DynamicSheetController dynamicSheetController;
    @FXML private GridPane control;
    @FXML private Accordion rangeSettings;
    @FXML private RangeSettingController rangeSettingsController;
    @FXML private GridPane sheetSettings;
    @FXML private SheetSettingsController sheetSettingsController;
    @FXML private AnchorPane popUpSorter;
    @FXML private PopUpSorterController popUpSorterController;

    public void setData(String newSheetName, boolean writingPermission) {
        sheetName = newSheetName;
        this.writingPermission = writingPermission;

        if (!writingPermission) {
            disableAllWritingActions();
        }
        //update the sheet
        getSheetDTOFromServer();
    }

    @FXML
    public void initialize() {
        // Ensure the GridPane resizes with the BorderPane and ScrollPane
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);


        //engine = new EngineImpl();
        headlineController.setControllers(this, dynamicSheetController);
        dynamicSheetController.setControllers(this, headlineController, sheetSettingsController);
        sheetSettingsController.setControllers(this, dynamicSheetController);
        rangeSettingsController.setControllers(this, dynamicSheetController);
        popUpSorterController.setMainController(this);
        popUpSorterController.addDynamicSheetController(dynamicSheetController);


    }

    private void disableAllWritingActions() {
        headlineController.disableWritingActions();
        rangeSettingsController.disableWritingActions();
    }

    public void buildSheet() {
            SheetDTO sheetDTO = currentSheet;
            if(sheetDTO != null) {
                dynamicSheetController.initializeSheet(sheetDTO.getSizeOfRows(), sheetDTO.getHeightOfRow()
                        , sheetDTO.getSizeOfColumns(), sheetDTO.getLengthOfCol());
                showSheet(sheetDTO);
            }
    }

    private void getSheetDTOFromServer(){
        String encodedParam = URLEncoder.encode(sheetName, StandardCharsets.UTF_8);
        String fullUrl = GET_SHEET_DTO_PATH + "?sheetName=" + encodedParam;
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
//                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
//                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
//                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
//                .create();
       // Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();

        Request request = new Request.Builder()
                .url(fullUrl)
                .get()
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                String jsonResponse = response.body().string();

                try {
                    currentSheet = gson.fromJson(jsonResponse, SheetDTO.class);
                } catch (JsonSyntaxException e) {
                    System.err.println("Failed to parse JSON: " + jsonResponse);
                    e.printStackTrace();
                }

                //currentSheet = gson.fromJson(jsonResponse, SheetDTO.class);
                //buildSheet();
                headlineController.setStartSheet();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(response.message());
                alert.showAndWait();
            }
        }
        catch (IOException e) {
            showErrorPopup(e);
        }
    }

    public void updateCellValue(CoordinateDTO coordinateDTO, String value) throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();

        CellDTO cell = new CellDTO(coordinateDTO, value, sheetName);

        RequestBody body = RequestBody.create(gson.toJson(cell), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(UPDATE_CELL_PATH)
                .post(body)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                String jsonResponse = response.body().string();
                currentSheet = gson.fromJson(jsonResponse, SheetDTO.class);
                updateSheet();
            }
            else{
                String errorResponse = response.body() != null ? response.body().string() : "No error message";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(errorResponse);
                alert.showAndWait();
            }
        }
        catch (IOException e) {
            showErrorPopup(e);
        }
        //engine.changeCell(coordinateDTO, value);
    }

    public void makeRangesEnabled(){
        rangeSettingsController.makeRangeEnabled();
    }

    public void makePopUpsEnabled(){
        popUpSorterController.enableButtons();
    }

    public void clearDynamicSheet() {
        dynamicSheet.getChildren().clear();
    }

    public void updateSheet() {
        showSheet(currentSheet);
    }

    public void showSheet(SheetDTO sheetDTO){
        dynamicSheetController.setSheetCells(sheetDTO);
        headlineController.setSheetVersionLblText(sheetDTO.getSheetVersion());
    }

    public void showErrorPopup(Exception ex) {
        // Create an alert of type ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An Error Occurred");
        alert.setContentText("Something went wrong!\n" +
                ex.getMessage()+
                "\nPlease try again.");

        alert.showAndWait();
    }

    public void deleteRange(String rangeName){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();

        RangeDTO range = new RangeDTO(rangeName, sheetName);

        RequestBody body = RequestBody.create(gson.toJson(range), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(DELETE_RANGE_PATH)
                .delete(body)
                .build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                String jsonResponse = response.body().string();
                currentSheet = gson.fromJson(jsonResponse, SheetDTO.class);
                updateSheet();
            }
            else{
                String errorResponse = response.body() != null ? response.body().string() : "No error message";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(errorResponse);
                alert.showAndWait();
            }
        }
        catch (IOException e) {
            showErrorPopup(e);
        }
//        try{
//            engine.deleteRange(rangeName);
//        }
//        catch(Exception e){
//            showErrorPopup(e);
//        }
    }

    public void addRange(String rangeName, String rangeValue){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CoordinateDTO.class, new CoordinateDTOAdapter())
                .registerTypeAdapter(CellDTO.class, new CellDTOAdapter())
                .registerTypeAdapter(RangeDTO.class, new RangeDTOAdapter())
                .registerTypeAdapter(SheetDTO.class, new SheetDTOAdapter())
                .create();


            RangeDTO range = new RangeDTO(rangeName, sheetName, rangeValue);

            RequestBody body = RequestBody.create(gson.toJson(range), MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url(ADD_RANGE_PATH)
                    .put(body)
                    .build();
            try (Response response = HTTP_CLIENT.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();
                    currentSheet = gson.fromJson(jsonResponse, SheetDTO.class);
                    updateSheet();
                } else {
                    String errorResponse = response.body() != null ? response.body().string() : "No error message";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(errorResponse);
                    alert.showAndWait();
                }
            } catch (IOException e) {
                showErrorPopup(e);
            }
    }

    public SheetDTO getSheetDTO(){
        return currentSheet;
    }
}
