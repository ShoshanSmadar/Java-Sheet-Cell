package program.function;

import engine.Engine;
import sheet.SheetDTO;

public interface ProgramFunctions {
    public boolean addFile();
    public void showSheet(SheetDTO sheet);
    public void showCell(SheetDTO sheet);
    public boolean updateCell(Engine sheetProgram);
    public void showVersion();
    public void exitProgram();
    public void explainFunctions();
}
