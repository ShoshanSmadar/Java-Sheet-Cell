package expression;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import sheet.Sheet;

public interface Expression {
    EffectiveValue eval(Sheet sheet);
    CellType getFunctionResultType();
}
