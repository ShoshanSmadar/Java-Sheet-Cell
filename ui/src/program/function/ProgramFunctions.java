package program.function;

import engine.Engine;
import sheet.SheetDTO;

public interface ProgramFunctions {
    boolean addFile(Engine sheetProgram);
    void showSheet(SheetDTO sheet);
    void showCell(SheetDTO sheet);
    void updateCell(Engine sheetProgram);
    void ShowOldVersionTable(Engine sheetProgram);
    void exitProgram();
    void explainFunctions();

    void saveFiles(Engine engine);
}
