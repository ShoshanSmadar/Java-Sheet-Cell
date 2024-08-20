package expression.impl.mathematical;

import expression.MathematicalExpression;

public class PlusMathematicalExpression implements MathematicalExpression {
    private MathematicalExpression left;
    private MathematicalExpression right;

    public PlusMathematicalExpression(MathematicalExpression left, MathematicalExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
