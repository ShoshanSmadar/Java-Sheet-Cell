package fxml.dynamicSheet;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class DynamicSheetController {
    public FlowPane dynamicSheet;
    @FXML
    private FlowPane flowPane; // Ensure this matches the fx:id in the FXML

    public DynamicSheetController() {
        // Constructor logic, if any
    }

    @FXML
    public void initialize() {
        initializeSheet();
    }

    public void initializeSheet() {
        if (dynamicSheet == null) {
            System.out.println("FlowPane is null!");
        } else {
            System.out.println("FlowPane is initialized.");
            DynamicSheet newSheet = new DynamicSheet(10,0 ,5,0);
            dynamicSheet.getChildren().add((newSheet.getGridPane())); // Replace this with your specific logic
        }
    }
}