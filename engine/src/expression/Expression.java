package expression;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;

public interface Expression {
    EffectiveValue eval();
    CellType getFunctionResultType();
}
