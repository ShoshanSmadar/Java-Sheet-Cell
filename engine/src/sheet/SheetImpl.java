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


public class SheetImpl implements Sheet {
    private String sheetName;
    private Map<Coordinate, Cell> cellMap;
    private int version;
    private final int sizeOfColumns;
    private final int lengthOfColumns;
    private final int hightOfRows;
    private final int sizeOfRows;
    private DependencyGraph coordinateGraph;

    public SheetImpl(String sheetName, int sizeOfColumns, int sizeOfRows, int hightOfRows, int lengthOfColumns) {
        sheetName = sheetName;
        cellMap = new HashMap<>();
        version = 0;
        this.sizeOfColumns = sizeOfColumns;
        this.sizeOfRows = sizeOfRows;
        coordinateGraph = new DependencyGraphImpl();
        this.hightOfRows = hightOfRows;
        this.lengthOfColumns = lengthOfColumns;
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
        newSheetVersion.cellMap.remove(coordinate);
        newSheetVersion.cellMap.put(coordinate, newCell);

        List<Cell> cellsThatHaveChanged =
                newSheetVersion
                        .getCellsByCalculationOrder()
                        .stream()
                        .filter(Cell::calculateEffectiveValue)
                        .collect(Collectors.toList());

        newSheetVersion.increaseVersion();
        int newVersion = newSheetVersion.getVersion();
        cellsThatHaveChanged.forEach(cell -> cell.updateVersion(newVersion));

        return newSheetVersion;
    }

    @Override
    public SheetDTO convertToSheetDTO() {
        Map<Coordinate, CellDTO> cellDTOMap = new HashMap<>();
        for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
            Coordinate coord = entry.getKey();
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
        return this.coordinateGraph.getIncomingEdgesDTO(cellCoordinate);
    }

    @Override
    public List<Coordinate> getCellDependingCoordinates(Coordinate cellCoordinate) {
        return this.coordinateGraph.getIncomingEdges(cellCoordinate);
    }

    @Override
    public void increaseVersion()
    {
        this.version++;
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
        //TODO!!
        return null;
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
        return this.coordinateGraph.topologicalSort();
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
