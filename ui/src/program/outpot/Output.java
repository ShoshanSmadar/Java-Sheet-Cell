package program.outpot;

import cell.Cell;
import cell.CellDTO;
import coordinate.CoordinateDTO;

public class Output {
    public static void printWelcome(){
        System.out.println("Welcome to Java Sheet Cell!\nPlease Enter XML file to start.");
    }

    public static void printMenu(){
        System.out.println("Please choose one of the following options:");
        System.out.println("*******************************************");
        System.out.println("1. upload new XML file");
        System.out.println("2. Show Sheet");
        System.out.println("3. Show a Cell");
        System.out.println("4. Update Cell value");
        System.out.println("5. Show specific Sheet Version");
        System.out.println("6. Exit");
        System.out.println("7. HELP - Function explantion");
    }

    public static void printChooseCell(int row, int col){
        System.out.println("Please enter wanted Cell, columns between A-"+String.valueOf('A'+ col)+"rows between 1-"+row+"\n(enter capital letter and then number for example A4):");
    }

    public static void printCellIsEmpty(CoordinateDTO coordinate)
    {
        System.out.println("Cell " + coordinate.toString() + " is empty.");
    }

    public static void printCell(CellDTO cell){
        System.out.println("showing cell information:");
        System.out.println("Cell coordinates: "+cell.getCoordinate().toString());
        System.out.println("Original value: "+cell.getOriginalValue());
        System.out.println("Effective value: " + cell.getEffectiveValue());
        System.out.println("Last version this cell was changes in " + cell.getLastVersionChanged());
        if (cell.getAffectedBy().size() == 0) {
            System.out.println("Cell is not depending on any other cells");
        }
        else{
            System.out.println("Cell is depending on this Cells: "+ cell.getAffectedBy().stream().toString());
        }
        if (cell.getAffecting().size() == 0) {
            System.out.println("Cell is not affecting any other cells\n");
        }
        else{
            System.out.println("Cell is affecting this Cells: "+ cell.getAffectedBy().stream().toString() + "\n");
        }
    }

    public static void printAskForCellFunction(){
        System.out.println("A function should be written in the following order: {<function name>,<first argument>,...,<last argument>}" +
                "\nFor example: {PLUS,1,2} or {MINUS,{PLUS,1,2},1}");
        System.out.println("following functions are posible:" +
                "\nMathematicl functions: PLUS, MINUS, TIMES, DIVIDE, MOD, POW, ABS. " +
                "\nSentence functions: CONCAT, SUB " +
                "\nRefrence another cell: REF");
        System.out.println("for more information on each function please choose option 7 on main menu.");
        System.out.println("\nPlease enter new Cell value:");
    }

    public static void printTryAgain(){
        System.out.println("Please try again.");
    }

    public static void printExeptionMessage(Exception e){
        System.out.println(e.getMessage());
    }

    public static void printSheetRow(String sheetRow){
        System.out.println(sheetRow);
    }

    public static void printSheetStatments(int currentVersion, String sheetName)
    {
        System.out.println("\n*******************************************\n");
        System.out.println("Current version: " + currentVersion);
        System.out.println("Sheet name: " + sheetName);
    }
}
