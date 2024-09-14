package fxml.labelCreator;

import coordinate.CoordinateDTO;
import javafx.scene.control.Label;

public class CellLabel extends Label {
    private final CoordinateDTO coordinateDTO;

    public CellLabel(CoordinateDTO coordinateDTO) {
        this.coordinateDTO = coordinateDTO;
    }

    public CoordinateDTO getCoordinateDTO() {
        return coordinateDTO;
    }
}