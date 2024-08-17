package expression.impl;

import expression.Expression;

public class PlusExpression implements Expression {
    private Expression left;
    private Expression right;

    public PlusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }
}
