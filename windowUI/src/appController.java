import fxml.dynamicSheet.DynamicSheet;
import fxml.dynamicSheet.DynamicSheetController;
import fxml.headline.HeadlineController;
import fxml.rangeSettings.RangeSettingsController;
import fxml.sheetSetting.SheetSettingsController;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import java.awt.*;

public class appController {
    @FXML private GridPane headline;
    @FXML private HeadlineController headlineConroller;
    @FXML private FlowPane dynamicSheet;
    @FXML private DynamicSheetController dynamicSheetController;
    @FXML private GridPane conrol;
    @FXML private RangeSettingsController rangeSettingsController;
    @FXML private SheetSettingsController sheetSettingsController;

    @FXML
    public void initialize() {
//        Label lbl = new Label("Dynamic content");
//        dynamicSheet.getChildren().add(lbl);

    }
}
