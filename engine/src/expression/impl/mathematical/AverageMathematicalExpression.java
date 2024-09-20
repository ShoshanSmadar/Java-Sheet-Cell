package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import coordinate.Coordinate;
import expression.Expression;
import sheet.Sheet;

import java.util.Iterator;
import java.util.List;

public class AverageMathematicalExpression implements Expression {
    String rangeName;

    public AverageMathematicalExpression(String rangeName) {
        this.rangeName = rangeName;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        if(!sheet.isNameInRange(rangeName)){
            throw new IllegalArgumentException("Range name given does not exist in sheet");
        }
        List<Coordinate> cellsInRange = sheet.getRangeCellList(rangeName);
        double sum = 0.0;
        int count = 0;
        for(int i = 0; i < cellsInRange.size(); i++) {
            Coordinate coordinate = cellsInRange.get(i);
            EffectiveValue result = sheet.getCell(coordinate).getEffectiveValue();
            if (result.getCellType() == CellType.NUMERIC
                    || (result.getCellType() == CellType.UNKNOWN && result.getValue() instanceof Double)) {
                sum += (double) result.getValue();
                count++;
            }

        }
        if(count == 0){
            throw new IllegalArgumentException("There are no numbers in range given, using function AVERAGE on range is not legal");
        }
        return new EffectiveValueImpl(CellType.NUMERIC, sum/count);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}
