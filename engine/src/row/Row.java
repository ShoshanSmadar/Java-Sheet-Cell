package row;

import cell.cellType.EffectiveValue;

public interface Row {

    Object getCell(int index);
    void addCell(EffectiveValue cell);
}
