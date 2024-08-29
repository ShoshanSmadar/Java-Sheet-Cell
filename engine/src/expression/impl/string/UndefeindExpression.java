package expression.impl.string;
import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import expression.impl.BaseExpression;
import sheet.Sheet;

public class UndefeindExpression implements Expression {

    @Override
    public EffectiveValue eval(Sheet sheet) {
        return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.UNDEFINED;
    }
}
