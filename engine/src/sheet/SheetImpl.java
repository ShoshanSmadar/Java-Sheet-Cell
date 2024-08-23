package sheet;
import cell.Cell;
import cell.CellImpl;
import coordinate.Coordinate;
import coordinate.CoordinateFactory;
import graph.DependencyGraph;
import graph.DependencyGraphImpl;

import java.util.*;
import java.util.stream.Collectors;


public class SheetImpl implements Sheet {
    private Map<Coordinate, Cell> cellMap;
    private int version;
    private final int sizeOfColumns;
    private final int sizeOfRows;
    private DependencyGraph coordinateGraph;

    public SheetImpl(int sizeOfColumns, int sizeOfRows) {
        cellMap = new HashMap<>();
        version = 0;
        this.sizeOfColumns = sizeOfColumns;
        this.sizeOfRows = sizeOfRows;
        coordinateGraph = new DependencyGraphImpl();
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
