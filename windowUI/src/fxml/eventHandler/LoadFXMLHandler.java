package fxml.eventHandler;

import engine.Engine;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileNotFoundException;

public class LoadFXMLHandler {
    public static boolean loadXML(File fxml, Engine sheetProgram){
        try {
            sheetProgram.enterNewSheetFromXML(fxml);

            return true;
        }
        catch (JAXBException e){
            return false;
        }
        catch(Exception e) {
            return false;
        }
    }
}
