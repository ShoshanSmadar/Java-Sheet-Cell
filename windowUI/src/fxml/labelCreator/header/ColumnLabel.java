package fxml.labelCreator.header;

import fxml.labelCreator.CellLabel;
import fxml.sheetSetting.AlignmentOption;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.util.LinkedList;

public class ColumnLabel extends HeaderLabel {

    public ColumnLabel(String rowLabelText){
        cellLabels = new LinkedList<CellLabel>();
        this.setText(rowLabelText);
    }

    public void changeColumnLength(double newWidth) {
        for (CellLabel label : cellLabels) {
            label.setMinWidth(newWidth);
            label.setMaxWidth(newWidth);
        }
        this.setMinWidth(newWidth);
        this.setMaxWidth(newWidth);
    }

    public void changeColumnAlignment(TextAlignment newAlignment) {
        for (CellLabel label : cellLabels) {
            label.setTextAlignment(newAlignment);
        }
        this.setTextAlignment(newAlignment);
    }

}
