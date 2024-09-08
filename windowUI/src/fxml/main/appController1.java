package fxml.main;

import fxml.dynamicSheet.DynamicSheetController;
import fxml.dynamicSheet.DynamicSheet;
import fxml.headline.HeadlineController;
import fxml.rangeSettings.RangeSettingsController;
import fxml.sheetSetting.SheetSettingsController;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.awt.*;


public class appController1 {
    @FXML private ScrollPane app;
    @FXML private GridPane headline;
    @FXML private HeadlineController headlineConroller;
    @FXML private FlowPane dynamicSheet;
    @FXML private DynamicSheetController dynamicSheetController;
    @FXML private GridPane conrol;
    @FXML private RangeSettingsController rangeSettingsController;
    @FXML private SheetSettingsController sheetSettingsController;

    @FXML
    public void initialize() {
        //DynamicSheet newSheet = new DynamicSheet(10,0 ,5,0);
        dynamicSheetController.initialize();

    }

}
