package expression.impl.bool;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class NotExpression implements Expression {
    Expression expr;
    public NotExpression(Expression expr) {
        this.expr = expr;
    }
    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue exprValue = expr.eval(sheet);

        if(exprValue.getCellType() == CellType.UNDEFINED){
            return new EffectiveValueImpl(CellType.UNDEFINED, CellType.UNDEFINED);
        }
        if(exprValue.getCellType() == CellType.UNKNOWN && !(exprValue.getValue() instanceof Boolean)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }

        return new EffectiveValueImpl(CellType.BOOLEAN, ! (Boolean) exprValue.getValue());
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }
}
