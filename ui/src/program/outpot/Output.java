package program.outpot;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import program.function.HelpFunction;


public class Output {
    public static void printWelcome(){
        System.out.println("Welcome to Java Sheet Cell!\n");
    }

    public static void printAskForXMLFile(){
        System.out.println("Please Enter XML file path: ");
    }

    public static void printSavingFiles(){
        System.out.println("Saving files...");
    }

    public static void printFinishedSavingFiles(){
        System.out.println("Finished Saving files...");
    }

    public static void printMenu(){
        System.out.println("\n*******************************************\n");
        System.out.println("Please choose one of the following options:\n");
        System.out.println("1. upload new XML file");
        System.out.println("2. Show Sheet");
        System.out.println("3. Show a Cell");
        System.out.println("4. Update Cell value");
        System.out.println("5. Show Old Sheet Versions");
        System.out.println("6. Save project");
        System.out.println("7. HELP - Function explanation");
        System.out.println("8. Exit");
    }

    public static void printEmptyCellToChangeInformation(String coor){
        System.out.println("\nThe Cell chosen " + coor + " is Empty\n\n");
    }

    public static void printCurrentCellToChangeInformation(CellDTO cell){
        System.out.println("\nChosen Cell: " + cell.getCoordinate().toString());
        System.out.println("Current Original value: " + cell.getOriginalValue());
        System.out.println("Current Effective value: " + cell.getEffectiveValue()+"\n\n");
    }

    public static void printAskForVersionNumber(int lastVersionNumber){
        System.out.println("Latest sheet version is " + lastVersionNumber);
        System.out.println("Please choose a version to see between 1-" + lastVersionNumber +":");
    }

    public static void printChooseCell(int row, int col){
        System.out.println("Please enter wanted Cell, columns between A-"+(char)('A'+ col -1)+" rows between 1-"+row+"\n(enter capital letter and then number for example A4):");
    }

    public static void printCellUpdatedSucsesfully(){
        System.out.println("Cell has been updated successfully");
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
            System.out.println("Cell is depending on this Cells: " + String.join(", ", cell.getListOfStringCoordinates(cell.getAffectedBy())));
        }
        if (cell.getAffecting().size() == 0) {
            System.out.println("Cell is not affecting any other cells\n");
        }
        else{
            System.out.println("Cell is affecting this Cells: " + String.join(", ", cell.getListOfStringCoordinates(cell.getAffecting())) + "\n");
        }
    }

    public static void printAskForCellFunction(){
        System.out.println("Please enter new cell value:");
        System.out.println("You can add a value or a function to the Cell.");
        System.out.println("A function should be written in the following order: {<function name>,<first argument>,...,<last argument>}" +
                "\nFor example: {PLUS,1,2} or {MINUS,{PLUS,1,2},1}");
        System.out.println("following functions are posible:" +
                "\nMathematicl functions: PLUS, MINUS, TIMES, DIVIDE, MOD, POW, ABS. " +
                "\nSentence functions: CONCAT, SUB " +
                "\nRefrence another cell: REF" +
                "\nIf you want to clear the cell press Enter");
        System.out.println("For more information on each function please choose option 7 on main menu.");
        System.out.println("\nPlease enter new Cell value:");
    }

    public static void printTryAgain(){
        System.out.println("Please try again.");
    }

    public static void printExeptionMessage(Exception e){
        System.out.println(e.getMessage());
    }

    public static void printWrongInput()
    {
        System.out.println("Wrong Input! Please try again.");
    }

    public static void printInputMustBeANumber(){
        System.out.println("Wrong Input! Input must be a number, try again.");
    }

    public static void printWrongInputShowCorrect(int start, int end){
        System.out.println("Wrong Input! Number must be between "+start+"-"+end+" Please try again.");
    }

    public static void printSheetRow(String sheetRow){
        System.out.println(sheetRow);
    }

    public static void printSheetStatments(int currentVersion, String sheetName)
    {
        System.out.println("\n*******************************************\n");
        System.out.println("Currently Showing sheet version: " + currentVersion);
        System.out.println("Sheet name: " + sheetName);
    }

    public static void printShowTableVersion(String versionLine, String numberOfCellsChangedLine){
        System.out.println("Information for all sheet versions:");
        System.out.println(versionLine);
        System.out.println(numberOfCellsChangedLine);
    }

    public static void PrintAskIfShowSpecificOldVersion(){
        System.out.println("Would you like to see a specific version?(press number)\n1. Yes\n2. No");
    }

    public static void printLoadingFile(){
        System.out.println("Loading file...");
    }

    public static void printFinshLoadingFile(){
        System.out.println("File loaded successfully.\n");
    }

    public static void printExit(){
        System.out.println("Thank you for using the program.\nBye!");
    }

    public static void printAskWichFunctionToHelpWith(){
        System.out.println("Choose the function description you want to see:");
        for (int i = 0; i < HelpFunction.getString().length; i++) {
            System.out.println(HelpFunction.getString()[i].substring(0, HelpFunction.getString()[i].indexOf('\n')));
        }
        System.out.println((HelpFunction.getString().length + 1) + ". All of the above");
    }

    public static void printOneFunctionHelp(int input){
        System.out.println(HelpFunction.getString()[input - 1]);
    }

    public static void printAllHelp(){
        for (String description : HelpFunction.getString()) {
            System.out.println(description);
            System.out.println();
        }
    }
}
