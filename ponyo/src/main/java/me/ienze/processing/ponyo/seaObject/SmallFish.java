package me.ienze.processing.ponyo.seaObject;

import me.ienze.processing.ponyo.Ponyo;
import me.ienze.processing.ponyo.Wave;
import processing.core.PShape;
import processing.core.PVector;

import java.awt.*;

public class SmallFish extends ShapeObject {

    public SmallFish(Ponyo ponyo, Wave wave, PVector position) {
        super(ponyo, wave, position);
        setScale(0.8f);
        setTransformOrigin(new PVector(0.5f, 0.5f));
        getShape().setFill(Color.magenta.getRGB());
        getShape().setStroke(Color.magenta.getRGB());
    }

    @Override
    protected PShape loadShape() {
        return ponyo.loadShape("smallFish.svg");
    }

    @Override
    protected PVector getMoveSpeed() {
        return new PVector(1.7f, 0);
    }
}
