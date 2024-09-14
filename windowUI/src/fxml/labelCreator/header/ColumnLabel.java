package fxml.labelCreator.header;

import fxml.labelCreator.CellLabel;
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
        String alignmentStyle = switch (newAlignment) {
            case LEFT -> "CENTER_LEFT";
            case CENTER -> "CENTER";
            case RIGHT -> "CENTER_RIGHT";
            default -> "CENTER"; // Default fallback
        };

        for (CellLabel label : cellLabels) {
            label.setTextAlignment(newAlignment);
            label.setStyle("-fx-alignment: " + alignmentStyle + ";"); // Align the label within its container
        }

        this.setTextAlignment(newAlignment);
        this.setStyle("-fx-alignment: " + alignmentStyle + ";");
    }

}
