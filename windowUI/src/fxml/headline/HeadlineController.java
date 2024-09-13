package fxml.headline;

import UIconstant.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sheet.SheetDTO;

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
    private javafx.scene.control.Label nameLbl;

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
            filePathLbl.setVisible(true);
            setHeaderInformationFromSheet();
        }
        catch (Exception e){
            showErrorPopup(e);
        }

    }

    private void setHeaderInformationFromSheet(){
        SheetDTO sheet = mainController.getSheetDTO();
        nameLbl.setText(sheet.getSheetName());
        SheetVersionLbl.setText(Constants.VESION_LABEL + sheet.getSheetVersion());
        SheetVersionLbl.setVisible(true);
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

    @FXML
    void selectVersionClicked(MouseEvent event) {

    }

    @FXML
    void updateValueClicked(MouseEvent event) {

    }

    public void setAppController(appController.appController controller){
        this.mainController = controller;
    }
}
