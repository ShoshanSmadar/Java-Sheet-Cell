package row;

import cell.cellType.EffectiveValue;
import range.RangeImpl;

import java.util.ArrayList;
import java.util.List;

public class RowImpl implements Row {
    private List<EffectiveValue> cells;


    public RowImpl(List<EffectiveValue> cells) {
        this.cells = cells;
    }

    public List<EffectiveValue> getCells() {
        return cells;
    }

    public RowImpl(){
        cells = new ArrayList<>();
    }



    @Override
    public EffectiveValue getCell(int index) {
        return cells.get(index);
    }

    @Override
    public void addCell(EffectiveValue cell) {
        cells.add(cell);
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
