package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;

import java.util.List;

abstract class CellImpl implements Cell{
    private Coordinate coordinate;
    private CellType cellType;
    private EffectiveValue value;
    private int lastVersionChanged;
    private String originalValue;
    private List<Cell> dependsOn;
    private List<Cell> affecting;

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
}
