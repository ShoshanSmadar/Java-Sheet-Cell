package program;


import engine.Engine;
import engine.EngineImpl;
import program.function.FunctionImpl;
import program.function.ProgramFunctions;
import program.outpot.Output;

import java.util.Scanner;

public class ProgramImpl implements Program{
    private Engine sheetProgram = new EngineImpl();
    private Scanner scanner = new Scanner(System.in);
    private ProgramFunctions functions = new FunctionImpl();

    @Override
    public void start(){
        Output.printWelcome();
        int userChoice;

        while(true)
        {
            Output.printMenu();
            userChoice = scanner.nextInt();
            try {
                optionSelect(userChoice);
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
            functions.showCell();
        else if (userChoice == ProgramOption.UPDATE_CELL.ordinal())
            functions.updateCell();
        else if(userChoice == ProgramOption.SHOW_VERSION.ordinal())
            functions.showVersion();
        else if (userChoice == ProgramOption.EXIT.ordinal())
            functions.exitProgram();
        else if (userChoice == ProgramOption.EXPLAIN_FNCTIONS.ordinal())
            functions.explainFunctions();
        else
            throw new IllegalArgumentException("Expected a number between 1 to " + ProgramOption.values().length + "but got" + userChoice);
    }

}
