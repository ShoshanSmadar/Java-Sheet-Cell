package appController;

import engine.Engine;
import engine.EngineImpl;
import fxml.dynamicSheet.DynamicSheetController;
import fxml.eventHandler.LoadFXMLHandler;
import fxml.headline.HeadlineController;
import fxml.rangeSettings.RangeSettingController;
import fxml.sheetSetting.SheetSettingsController;
import jakarta.xml.bind.JAXBException;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;

public class appController {
    Engine engine;

    @FXML private GridPane headline;
    @FXML private HeadlineController headlineController;
    @FXML private FlowPane dynamicSheet;
    @FXML private DynamicSheetController dynamicSheetController;
    @FXML private GridPane control;
    @FXML private Accordion rangeSettings;
    @FXML private RangeSettingController rangeSettingController;
    @FXML private GridPane sheetSettings;
    //@FXML private
    @FXML private SheetSettingsController sheetSettingsController;

    @FXML
    public void initialize() {
        engine = new EngineImpl();
        headlineController.setAppController(this);
        dynamicSheetController.setAppControler(this);
    }

    public void OpenFXMLFile(File file) throws JAXBException, FileNotFoundException {
        boolean bool = LoadFXMLHandler.loadXML(file, engine);
        if(bool){
            SheetDTO sheetDTO = engine.getSheetDTO();
            dynamicSheetController.initializeSheet(sheetDTO.getSizeOfRows(), sheetDTO.getHeightOfRow()
                    , sheetDTO.getSizeOfColumns(), sheetDTO.getLengthOfCol());
            showSheet(sheetDTO);
        }
    }

    public void clearDynamicSheet() {
        dynamicSheet.getChildren().clear();
    }

    public void showSheet(SheetDTO sheetDTO){
        dynamicSheetController.setSheetCells(sheetDTO);
    }

    public SheetDTO getSheetDTO(){
        return engine.getSheetDTO();
    }

}
