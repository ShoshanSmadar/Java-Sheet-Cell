package row;

import cell.cellType.EffectiveValue;

public interface Row {

    EffectiveValue getCell(int index);
    void addCell(EffectiveValue cell);
}
