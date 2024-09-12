import fxml.dynamicSheet.DynamicSheet;
import fxml.dynamicSheet.DynamicSheetController;
import fxml.headline.HeadlineController;
import fxml.rangeSettings.RangeSettingController;
import fxml.sheetSetting.SheetSettingsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;

public class appController {
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
    public void initialize() {}

}
