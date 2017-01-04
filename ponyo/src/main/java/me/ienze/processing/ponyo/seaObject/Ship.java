package me.ienze.processing.ponyo.seaObject;

import me.ienze.processing.ponyo.Ponyo;
import me.ienze.processing.ponyo.Wave;
import processing.core.PShape;
import processing.core.PVector;

public class Ship extends ShapeObject {

    public Ship(Ponyo ponyo, Wave wave, PVector position) {
        super(ponyo, wave, position);
        setScale(1.0f);
        setTransformOrigin(new PVector(0.5f, 0.9f));
    }

    @Override
    protected PShape loadShape() {
        return ponyo.loadShape("ship.svg");
    }

    @Override
    public int getRenderPriority() {
        return -10;
    }

    @Override
    protected PVector getMoveSpeed() {
        return new PVector(0.2f, 0);
    }
}
