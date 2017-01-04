package me.ienze.processing.ponyo.seaObject;

import me.ienze.processing.ponyo.Ponyo;
import me.ienze.processing.ponyo.Transformable;
import me.ienze.processing.ponyo.Wave;
import processing.core.PShape;
import processing.core.PVector;

public abstract class ShapeObject extends Transformable implements SeaObject {

    private PVector position;
    private PShape shape;
    private float scale;
    private PVector transformOrigin;

    public ShapeObject(Ponyo ponyo, Wave wave, PVector position) {
        super(ponyo, wave);
        this.position = position;
        this.scale = 1.0f;
        this.transformOrigin = new PVector(0.5f, 0.5f);
        shape = loadShape();
    }

    @Override
    public void render() {

        ponyo.pushMatrix();

        float tx = shape.width*transformOrigin.x;
        float ty = shape.height*transformOrigin.y;
        ponyo.translate(position.x, transform(position.x)+position.y);

        ponyo.rotate(transformAngle(position.x));
        ponyo.scale(scale);
        ponyo.shape(shape, -tx, -ty);

        ponyo.popMatrix();

        this.position.add(getMoveSpeed());
    }

    @Override
    public int getRenderPriority() {
        return 1;
    }

    @Override
    public PVector getPosition() {
        return position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public void setTransformOrigin(PVector transformOrigin) {
        this.transformOrigin = transformOrigin;
    }

    public PVector getTransformOrigin() {
        return transformOrigin;
    }

    protected abstract PShape loadShape();

    protected abstract PVector getMoveSpeed();

    public PShape getShape() {
        return shape;
    }
}
