package expression.impl;

import expression.Expression;

public class PowExpression implements Expression {
    private Expression left;
    private Expression right;

    public PowExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return Math.pow(left.evaluate(), right.evaluate());
    }
}
