package appController;

import engine.Engine;
import engine.EngineImpl;
import fxml.dynamicSheet.DynamicSheetController;
import fxml.eventHandler.LoadFXMLHandler;
import fxml.headline.HeadlineController;
import fxml.rangeSettings.RangeSettingController;
import fxml.sheetSetting.SheetSettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import sheet.SheetDTO;

import java.io.File;

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
        headlineController.setAppControler(this);
        dynamicSheetController.setAppControler(this);
    }

    public void OpenFXMLFile(File file) {
        boolean bool = LoadFXMLHandler.loadXML(file, engine);
        if(bool){
            showSheet();
        }
    }

    public void showSheet(){
        dynamicSheetController.setSheetCells(engine.getSheetDTO());
    }

    public SheetDTO getSheetDTO(){
        return engine.getSheetDTO();
    }

}
