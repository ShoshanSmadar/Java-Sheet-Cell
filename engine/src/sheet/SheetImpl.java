package sheet;
import cell.Cell;
import cell.CellImpl;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import coordinate.CoordinateFactory;
import graph.DependencyGraph;
import graph.DependencyGraphImpl;
import cell.CellDTO;


import java.util.*;
import java.util.stream.Collectors;


public class SheetImpl implements Sheet, Cloneable {
    private String sheetName;
    private Map<Coordinate, Cell> cellMap;
    private int version;
    private final int sizeOfColumns;
    private final int lengthOfColumns;
    private final int hightOfRows;
    private final int sizeOfRows;
    private DependencyGraph coordinateGraph;
    private int numberOfCellsChangedInVersion = 0;

    public SheetImpl(String sheetName, int sizeOfColumns, int sizeOfRows,
                     int hightOfRows, int lengthOfColumns) {
        this.sheetName = sheetName;
        this.cellMap = new HashMap<>();
        this.version = 0;
        this.sizeOfColumns = sizeOfColumns;
        this.sizeOfRows = sizeOfRows;
        this.coordinateGraph = new DependencyGraphImpl();
        this.hightOfRows = hightOfRows;
        this.lengthOfColumns = lengthOfColumns;
    }

    @Override
    public int getNumberOfSheetsChangedInVersion() {
        return numberOfCellsChangedInVersion;
    }

    @Override
    public SheetImpl clone(){
        try{
            SheetImpl newSheet = (SheetImpl) super.clone();
            newSheet.cellMap = new HashMap<>();
            for(Map.Entry<Coordinate, Cell> entry : this.cellMap.entrySet()){
                Coordinate coordinate = entry.getKey();
                Cell cell = entry.getValue().clone();
                newSheet.cellMap.put(coordinate, cell);
            }
            newSheet.coordinateGraph = this.coordinateGraph.clone();

            return newSheet;
        }
        catch(CloneNotSupportedException e){
            return null;
        }
    }

    @Override
    public int getVersion(){
        return version;}

    @Override
    public Cell getCell(int row, int col) {
        return cellMap.get(CoordinateFactory.createCoordinate(row, col));
    }

    @Override
    public Cell getCell(Coordinate coordinate) {
        return cellMap.get(coordinate);
    }

    @Override
    public Sheet UpdateCellValueAndSheet(int row, int col, String value) throws Exception {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, col);

        SheetImpl newSheetVersion = (SheetImpl) this.clone();
        Cell newCell = new CellImpl(newSheetVersion, row, col, value, newSheetVersion.getVersion() + 1);
        for(Coordinate corr : newCell.getdependingOn()){
            if(!this.cellMap.containsKey(corr)){
                throw new IllegalArgumentException("The referenced coordinate " + coordinate.toString() + " is empty, command failed.");
            }
        }
        newSheetVersion.cellMap.put(coordinate, newCell);
        for(Cell cell : newSheetVersion.cellMap.values()){
            cell.setFatherSheet(newSheetVersion);
        }

        List<Cell> cellsThatHaveChanged = newSheetVersion.calculateAllSheetAffectiveValue();

        numberOfCellsChangedInVersion = cellsThatHaveChanged.size();
        newSheetVersion.increaseVersion();
        int newVersion = newSheetVersion.getVersion();
        cellsThatHaveChanged.forEach(cell -> cell.updateVersion(newVersion));

        return newSheetVersion;
    }

    @Override
    public List<Cell> calculateAllSheetAffectiveValue() throws Exception {
        List<Cell> cellsThatHaveChanged =
                this
                        .getCellsByCalculationOrder()
                        .stream()
                        .filter(Cell::calculateEffectiveValue)
                        .collect(Collectors.toList());

        return cellsThatHaveChanged;
    }

    @Override
    public SheetDTO convertToSheetDTO() {
        Map<CoordinateDTO, CellDTO> cellDTOMap = new HashMap<>();
        for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
            CoordinateDTO coord = entry.getKey().convertToDTO();
            Cell cell = entry.getValue();
            CellDTO cellDTO = cell.getConvertToCellDTO();
            cellDTOMap.put(coord, cellDTO);
        }

        return new SheetDTO(this.sheetName, this.version, cellDTOMap, this.sizeOfColumns, this.lengthOfColumns, this.sizeOfRows, this.hightOfRows);
    }

    @Override
    public List<CoordinateDTO> getCellDependingCoordinatesDTO(Coordinate cellCoordinate) {
        return this.coordinateGraph.getIncomingEdgesDTO(cellCoordinate);
    }

    @Override
    public List<CoordinateDTO> getCellAfctingCoordinates(Coordinate cellCoordinate) {
        return this.coordinateGraph.getOutgoingEdgesDTO(cellCoordinate);
    }


    @Override
    public List<Coordinate> getCellDependingCoordinates(Coordinate cellCoordinate) {
        return this.coordinateGraph.getIncomingEdges(cellCoordinate);
    }

    @Override
    public void enterCoordinateAndDependenciesToGraph(Coordinate cellCoordinate, List<Coordinate> coordinateDependencies) {
        this.coordinateGraph.ExpandGraph(cellCoordinate);
        for(Coordinate coordinateThatPointsTo : coordinateDependencies){
            this.coordinateGraph.ExpandGraph(coordinateThatPointsTo, cellCoordinate);
        }
    }

    @Override
    public void increaseVersion() {
        this.version++;
    }

    @Override
    public void enterCellFromXML(Cell cell) {
        cellMap.put(cell.getCoordinate(), cell);
        for(Coordinate coordinate : cell.getdependingOn()){
            this.coordinateGraph.ExpandGraph(coordinate, cell.getCoordinate());
        }
    }

    @Override
    public void setCell(int row, int column, String value) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        Optional
                .ofNullable(cellMap.get(coordinate))
                .or(() -> {
                    Cell newCell = new CellImpl(this, row, column, value, 1);
                    cellMap.put(coordinate, newCell);
                    return Optional.of(newCell);
                })
                .ifPresent(cell -> cell.setCellOriginalValue(value));
    }

    private SheetImpl copySheet() {
        return this.clone();
    }

    @Override
    public int getSizeOfColumns()
    {
        return sizeOfColumns;
    }

    @Override
    public int getSizeOfRows()
    {
        return sizeOfRows;
    }

    private List<Coordinate> getCalculationOrder() throws Exception{
        return this.coordinateGraph.topologicalOrder();
    }

    private List<Cell> getCellsByCalculationOrder() throws Exception{
        List<Coordinate> coordinatesList = this.getCalculationOrder();
        List<Cell> cellsList = new ArrayList<>();
        for(Coordinate coordinate : coordinatesList) {
            cellsList.add(this.getCell(coordinate));
        }
        return cellsList;
    }
}
