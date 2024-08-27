package program;

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
        /*sheetProgram = new EngineImpl(new SheetDTO("first try", 0
                , new HashMap<>(), 10, 5, 15, 2)
                , "first try");*/
        sheetProgram = new EngineImpl();
    }

    @Override
    public void start(){
        Output.printWelcome();
        int userChoice;

        while(!functions.addFile(sheetProgram));

        while(true)
        {
            Output.printMenu();
            try {
                userChoice = scanner.nextInt();
                optionSelect(userChoice - 1);
            }
            catch (InputMismatchException e) {
                Output.printWrongInput();
                scanner.nextLine(); // clear wrong input
            }
            catch (Exception e)
            {
                Output.printExeptionMessage(e);
                Output.printTryAgain();
            }
        }
    }

    /*public void start(){
        Output.printWelcome();
        int userChoice;

        while(true)
        {
            Output.printMenu();
            try {
                userChoice = scanner.nextInt();
                optionSelect(userChoice - 1);
            }
            catch (InputMismatchException e) {
                Output.printWrongInput();
                scanner.nextLine(); // clear wrong input
            }
            catch (Exception e)
            {
                Output.printExeptionMessage(e);
                Output.printTryAgain();
            }
        }
    }*/
    
    private void optionSelect(int userChoice) throws Exception {
        if(userChoice == ProgramOption.ADD_FILE.ordinal())
            functions.addFile(sheetProgram);
        else if (userChoice == ProgramOption.SHOW_SHEET.ordinal())
            functions.showSheet(sheetProgram.getSheetDTO());
        else if (userChoice == ProgramOption.SHOW_CELL.ordinal())
            functions.showCell(sheetProgram.getSheetDTO());
        else if (userChoice == ProgramOption.UPDATE_CELL.ordinal())
            functions.updateCell(sheetProgram);
        else if(userChoice == ProgramOption.SHOW_VERSION.ordinal())
            functions.ShowOldVersionTable(sheetProgram);
        else if (userChoice == ProgramOption.EXIT.ordinal())
            functions.exitProgram();
        else if (userChoice == ProgramOption.EXPLAIN_FUNCTIONS.ordinal())
            functions.explainFunctions();
        else
            throw new IllegalArgumentException("Expected a number between 1 to "
                    + ProgramOption.values().length + ". but got " + (userChoice + 1));
    }


}

