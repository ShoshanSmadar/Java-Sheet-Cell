package sheetShower.fxml.labelCreator.header;

import sheetShower.fxml.labelCreator.CellLabel;

import java.util.LinkedList;

public class RowLabel extends HeaderLabel {

    public RowLabel(String rowLabelText){
        cellLabels = new LinkedList<CellLabel>();
        this.setText(rowLabelText);
    }

    public void changeRowWidth(double newHeight) {
        for(CellLabel label : cellLabels){
            label.setMinHeight(newHeight);
            label.setMaxHeight(newHeight);
        }
        this.setMinHeight(newHeight);
        this.setMaxHeight(newHeight);
    }
}
