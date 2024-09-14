package fxml.labelCreator.header;

import fxml.labelCreator.CellLabel;
import javafx.scene.control.Label;

import java.util.List;


public abstract class HeaderLabel extends Label {
    List<CellLabel> cellLabels;

    public void addCellLabel(CellLabel cellLabel) {
        cellLabels.add(cellLabel);
    }

    public void showPressedLabelChildren(){
        for (CellLabel cellLabel : cellLabels) {
            cellLabel.getStyleClass().add("show-pressed-label-children");
        }
    }

    public void stopPressedLabelChildren(){
        for (CellLabel cellLabel : cellLabels) {
            cellLabel.getStyleClass().remove("show-pressed-label-children");
        }
    }
}
