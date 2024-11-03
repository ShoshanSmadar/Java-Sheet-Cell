package utils;

import engine.Engine;

public class EngineInformationImpl implements EngineInformation {
    String sheetOwner;
    Engine engine;
    long fileSize;

    public EngineInformationImpl(String sheetOwner, Engine engine, long fileSize) {
        this.sheetOwner = sheetOwner;
        this.engine = engine;
        this.fileSize = fileSize;
    }

    @Override
    public String getSheetOwner() {
        return sheetOwner;
    }

    @Override
    public Engine getEngine() {
        return engine;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }
}
