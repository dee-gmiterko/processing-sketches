package me.ienze.processing.ponyo.seaObject;

import me.ienze.processing.ponyo.Ponyo;
import me.ienze.processing.ponyo.Transformable;
import me.ienze.processing.ponyo.Wave;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class Eel extends Transformable implements SeaObject {

    public static final int EEL_PART_SIZE = 10;

    private PVector position;
    private int width = 256;
    private int height = 20;
    private Color color;

    public Eel(Ponyo ponyo, Wave wave, PVector position) {
        super(ponyo, wave);
        this.position = position;
        this.color = new Color(ponyo.random(0.1f, 0.9f), ponyo.random(0.1f, 0.9f), ponyo.random(0.1f, 0.9f));
        this.setExpression("sin(pi*(x+t)/60.0)*6");
    }

    @Override
    public void render() {

        ponyo.pushMatrix();
        ponyo.translate(position.x, position.y);

        PVector[] transforms = new PVector[width / EEL_PART_SIZE];
        for(int i = 0; i<transforms.length; i++) {
            transforms[i] = new PVector(i * EEL_PART_SIZE, transform(position.x + i * EEL_PART_SIZE));
        }

        renderEel(transforms);

        ponyo.popMatrix();

        this.position.add(getMoveSpeed());
    }

    private void renderEel(PVector[] transforms) {
        ponyo.beginShape();

        ponyo.fill(color.getRGB());
        ponyo.stroke(0, 0, 0);

        //top
        for(int i = 0; i<transforms.length; i++) {
            ponyo.curveVertex(transforms[i].x, transforms[i].y);
        }
        //bottom
        for(int i = transforms.length - 1; i>=0; i--) {
            float eelWidth = (float) (height * (1-Math.cos(Math.min(Math.PI/2, Math.PI*i*EEL_PART_SIZE/width))));
            PVector translatedVector = transforms[i].copy().add(0, -eelWidth);
            ponyo.curveVertex(translatedVector.x, translatedVector.y);
        }

        ponyo.endShape(PConstants.CLOSE);

        //eye
        ponyo.fill(255, 182);
        float eyeX = width - EEL_PART_SIZE * 3;
        ponyo.ellipse(eyeX, transform( position.x + eyeX) - height/2, 15, 15);
    }

    @Override
    public int getRenderPriority() {
        return 1;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected PVector getMoveSpeed() {
        return new PVector(0.8f, 0);
    }

    @Override
    public PVector getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
