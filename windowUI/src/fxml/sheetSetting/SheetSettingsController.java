package fxml.sheetSetting;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class SheetSettingsController {

    @FXML
    private ColorPicker BackroungColorPicker;

    @FXML
    private GridPane ColumnSettingBar;

    @FXML
    private Label ColumnSettingPane;

    @FXML
    private Button RevertCellSettingsBtn;

    @FXML
    private GridPane RowSettingBar;

    @FXML
    private ColorPicker TextColorPicker;

    @FXML
    private ColumnConstraints cellSetingsBar;

    @FXML
    private GridPane cellSettingsPane;

    @FXML
    private TextField columnLengthTextBar;

    @FXML
    private TextField rowLengthTextBar;

    @FXML
    private Label rowSettingBox;

    @FXML
    void reverseCellClick(MouseEvent event) {

    }

}