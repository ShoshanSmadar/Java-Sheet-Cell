package fxml.headline;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

//import java.awt.*;

public class HeadlineController {
    private appController.appController mainController;

    @FXML
    private TextField ActionLineLbl;

    @FXML
    private Button LoadFileBtn;

    @FXML
    private javafx.scene.control.Label OriginalValueLbl;

    @FXML
    private javafx.scene.control.Label SheetVersionLbl;

    @FXML
    private Button UpdateBtn;

    @FXML
    private javafx.scene.control.Label cellVersionLbl;

    @FXML
    private javafx.scene.control.Label filePathLbl;

    @FXML
    private javafx.scene.control.Label nameLal;

    @FXML
    private Button selectVersionBtn;

    @FXML
    private javafx.scene.control.Label selectedCellLbl;

    @FXML
    void openFileBtnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter xmlFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(xmlFilter);
        fileChooser.setTitle("Open File");
        Stage stage = (Stage) LoadFileBtn.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);


        try{
            mainController.OpenFXMLFile(file);
            filePathLbl.setText(file.getAbsolutePath());
        }
        catch (Exception e){

        }

    }

    @FXML
    void selectVersionClicked(MouseEvent event) {

    }

    @FXML
    void updateValueClicked(MouseEvent event) {

    }

    public void setAppControler(appController.appController controller){
        this.mainController = controller;
    }
}
