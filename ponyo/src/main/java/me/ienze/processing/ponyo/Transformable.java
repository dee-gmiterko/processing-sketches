package me.ienze.processing.ponyo;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Transformable {

    private Expression expression;
    protected Ponyo ponyo;
    protected Transformable parent;


    public Transformable(Ponyo ponyo, Transformable parent) {
        this.ponyo = ponyo;
        this.parent = parent;
    }

    public float transform(double x) {
        double result = 0.0;
        if(expression != null) {
            result = expression
                .setVariable("x", x)
                .setVariable("t", ponyo.frameCount)
                .evaluate();
        }
        if(parent != null) {
            result += parent.transform(x);
        }
        return (float) result;
    }

    public float transformAngle(double x) {
        return (float) Math.atan(transform(x)-transform(x-1));
    }

    public void setExpression(String expression) {
        this.expression = new ExpressionBuilder(expression)
            .variables("x", "t")
            .build();
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
