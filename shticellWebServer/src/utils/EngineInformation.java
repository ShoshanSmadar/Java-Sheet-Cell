package utils;

import engine.Engine;

public interface EngineInformation {

    String getSheetOwner();

    Engine getEngine();

    long getFileSize();
}
