package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;
import coordinate.CoordinateImpl;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell{
    private Coordinate coordinate;
    private CellType cellType;
    private EffectiveValue value;
    private int lastVersionChanged;
    private String originalValue;
    private List<Cell> dependsOn;
    private List<Cell> affecting;

    public CellImpl(int row, int column, String originalValue, int version)  {
        this.coordinate = new CoordinateImpl(row, column);
        this.originalValue = originalValue;
        this.lastVersionChanged = version;
        this.dependsOn = new ArrayList<>();
        this.affecting = new ArrayList<>();
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public CellType getCellType() {
        return cellType;
    }

    @Override
    public EffectiveValue getEffectiveValue() {
        return value;
    }

    @Override
    public int getLastVersionChanged(){
        return lastVersionChanged;
    }

    @Override
    public void calculateEffectiveValue()
    {
        //TO DO!!!
    }

    @Override
    public List<Cell> getDependsOn() {
        return dependsOn;
    }

    @Override
    public List<Cell> getAffecting() {
        return affecting;
    }

    @Override
    public void setCellOriginalValue(String value)
    {
        this.originalValue = value;
    }

}
