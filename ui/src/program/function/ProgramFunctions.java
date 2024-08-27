package program.function;

import engine.Engine;
import sheet.SheetDTO;

public interface ProgramFunctions {
    public boolean addFile(Engine sheetProgram);
    public void showSheet(SheetDTO sheet);
    public void showCell(SheetDTO sheet);
    public void updateCell(Engine sheetProgram);
    public void showVersion(Engine sheetProgram);
    public void exitProgram();
    public void explainFunctions();
}
