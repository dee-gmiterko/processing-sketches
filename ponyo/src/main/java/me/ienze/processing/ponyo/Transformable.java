package me.ienze.processing.ponyo;

import com.sk89q.worldedit.internal.expression.Expression;
import com.sk89q.worldedit.internal.expression.ExpressionException;
import com.sk89q.worldedit.internal.expression.runtime.EvaluationException;

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
            try {
                result = expression.evaluate(x, ponyo.frameCount);
            } catch (EvaluationException e) {
                e.printStackTrace();
            }
        }
        if(parent != null) {
            result += parent.transform(x);
        }
//        result += ponyo.random(-1, 1);
        return (float) result;
    }

    public float transformAngle(double x) {
        return (float) Math.atan(transform(x)-transform(x-1));
    }

    public void setExpression(String expression) {
        try {
            this.expression = Expression.compile(expression, "x", "t");
        } catch (ExpressionException e) {
            e.printStackTrace();
        }
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
