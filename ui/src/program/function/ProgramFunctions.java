package program.function;

import sheet.SheetDTO;

public interface ProgramFunctions {
    public boolean addFile();
    public void showSheet(SheetDTO sheet);
    public void showCell();
    public boolean updateCell();
    public void showVersion();
    public void exitProgram();
    public void explainFunctions();
}
