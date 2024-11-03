package sheetShower.fxml.labelCreator;

import coordinate.CoordinateDTO;
import javafx.scene.control.Label;

public class CellLabel extends Label {
    private final CoordinateDTO coordinateDTO;
    private String backroundColorStyle;

    public CellLabel(CoordinateDTO coordinateDTO) {
        this.coordinateDTO = coordinateDTO;
    }

    public CoordinateDTO getCoordinateDTO() {
        return coordinateDTO;
    }

    public void setNewBackroundColorStyle(String backroundColorStyle) {
        this.backroundColorStyle = backroundColorStyle;
    }

    public void setBackroundColorStyle() {
        if (backroundColorStyle != null) {
            this.setStyle(backroundColorStyle);
        }
    }
}
