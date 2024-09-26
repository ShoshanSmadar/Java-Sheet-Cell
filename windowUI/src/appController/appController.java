package appController;

import coordinate.CoordinateDTO;
import engine.Engine;
import engine.EngineImpl;
import fxml.dynamicSheet.DynamicSheetController;
import fxml.eventHandler.LoadFXMLHandler;
import fxml.headline.HeadlineController;
import fxml.popUpLineSort.LineSortController;
import fxml.rangeSettings.RangeSettingController;
import fxml.sheetSetting.SheetSettingsController;
import fxml.sorter.PopUpSorterController;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;

public class appController {
    Engine engine;
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


    @FXML
    public void initialize() {
        // Ensure the GridPane resizes with the BorderPane and ScrollPane
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        engine = new EngineImpl();
        headlineController.setControllers(this, dynamicSheetController);
        dynamicSheetController.setControllers(this, headlineController, sheetSettingsController);
        sheetSettingsController.setControllers(this, dynamicSheetController);
        rangeSettingsController.setControllers(this, dynamicSheetController);
        popUpSorterController.setMainController(this);

    }

    public void OpenFXMLFile(File file) throws JAXBException, FileNotFoundException {
        if(file == null){
            throw new FileNotFoundException();
        }
        boolean bool = LoadFXMLHandler.loadXML(file, engine);
    }

    public void buildSheet() {
            SheetDTO sheetDTO = engine.getSheetDTO();
            if(sheetDTO != null) {
                dynamicSheetController.initializeSheet(sheetDTO.getSizeOfRows(), sheetDTO.getHeightOfRow()
                        , sheetDTO.getSizeOfColumns(), sheetDTO.getLengthOfCol());
                showSheet(sheetDTO);
            }

    }

    public Engine getEngine(){
        return engine;
    }

    public void updateCellValue(CoordinateDTO coordinateDTO, String value) throws Exception {
        engine.changeCell(coordinateDTO, value);
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
        showSheet(engine.getSheetDTO());
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
        try{
            engine.deleteRange(rangeName);
        }
        catch(Exception e){
            showErrorPopup(e);
        }
    }

    public void addRange(String rangeName, String rangeValue){
        try {
            engine.addRange(rangeName, rangeValue);
        }
        catch (Exception e) {
            showErrorPopup(e);
        }
    }

    public SheetDTO getSheetDTO(){
        return engine.getSheetDTO();
    }
}
