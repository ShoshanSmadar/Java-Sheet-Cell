package program;


import cell.CellDTO;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import engine.Engine;
import engine.EngineImpl;
import program.function.FunctionImpl;
import program.function.ProgramFunctions;
import program.outpot.Output;
import sheet.SheetDTO;

import java.util.*;

public class ProgramImpl implements Program{
    private Engine sheetProgram;
    private Scanner scanner = new Scanner(System.in);
    private ProgramFunctions functions = new FunctionImpl();

    public ProgramImpl() {
        sheetProgram = new EngineImpl(new SheetDTO("first try", 0, new HashMap<>(), 10, 5, 15, 2 ), "first try");
    }

    @Override
    public void start(){
        Output.printWelcome();
        int userChoice;

        while(true)
        {
            Output.printMenu();
            userChoice = scanner.nextInt();
            try {
                optionSelect(userChoice - 1);
            }
            catch (Exception e)
            {
                Output.printExeptionMessage(e);
                Output.printTryAgain();
            }
        }
    }
    
    private void optionSelect(int userChoice) throws Exception {
        if(userChoice == ProgramOption.ADD_FILE.ordinal())
            functions.addFile();
        else if (userChoice == ProgramOption.SHOW_SHEET.ordinal())
            functions.showSheet(sheetProgram.getSheetDTO());
        else if (userChoice == ProgramOption.SHOW_CELL.ordinal())
            functions.showCell(sheetProgram.getSheetDTO());
        else if (userChoice == ProgramOption.UPDATE_CELL.ordinal())
            functions.updateCell(sheetProgram);
        else if(userChoice == ProgramOption.SHOW_VERSION.ordinal())
            functions.showVersion();
        else if (userChoice == ProgramOption.EXIT.ordinal())
            functions.exitProgram();
        else if (userChoice == ProgramOption.EXPLAIN_FUNCTIONS.ordinal())
            functions.explainFunctions();
        else
            throw new IllegalArgumentException("Expected a number between 1 to " + ProgramOption.values().length + ". but got" + userChoice);
    }


}

