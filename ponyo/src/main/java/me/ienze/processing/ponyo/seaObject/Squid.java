package me.ienze.processing.ponyo.seaObject;

import me.ienze.processing.ponyo.Ponyo;
import me.ienze.processing.ponyo.Transformable;
import me.ienze.processing.ponyo.Wave;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class Squid extends Transformable implements SeaObject {

    public static final int SQUID_PART_SIZE = 10;
    public static final int SQUID_TENTACLE_COUNT = 5;

    private PVector position;
    private int headWidth = 64;
    private int tentacleWidth = 220;
    private int height = 64;
    private Color color;

    public Squid(Ponyo ponyo, Wave wave, PVector position) {
        super(ponyo, wave);
        this.position = position;
        this.setExpression("sin(pi*(x+t)/60.0)*3");
    }

    @Override
    public void render() {

        ponyo.pushMatrix();
        ponyo.translate(position.x, position.y);

        ponyo.fill(color.getRGB());
        ponyo.stroke(0, 0, 0);

        renderHead();

        PVector[] transforms = new PVector[tentacleWidth / SQUID_PART_SIZE];
        for(int i = 0; i<transforms.length; i++) {
            transforms[i] = new PVector((i+2) * SQUID_PART_SIZE - tentacleWidth, transform(position.x + (i+2) * SQUID_PART_SIZE - tentacleWidth));
        }

        renderTentacles(transforms);

        //eye
        ponyo.fill(255, 182);
        float eyeX = headWidth / 3;
        ponyo.ellipse(eyeX, transform( position.x + eyeX) - 20, 20, 20);
        ponyo.ellipse(eyeX, transform( position.x + eyeX) + 20, 20, 20);

        ponyo.popMatrix();

        this.position.add(getMoveSpeed());
    }

    private void renderHead() {
        ponyo.beginShape();
        ponyo.curveVertex(-headWidth, transform(position.x - headWidth) + height / 2);
        ponyo.curveVertex(0, transform(position.x) + height / 2);
        ponyo.curveVertex(headWidth / 2, transform(position.x + headWidth / 2) + height / 2 * 1.1f);
        ponyo.curveVertex(headWidth, transform(position.x + headWidth));
        ponyo.curveVertex(headWidth / 2, transform(position.x + headWidth / 2) - height / 2 * 1.1f);
        ponyo.curveVertex(0, transform(position.x) - height / 2);
        ponyo.curveVertex(-headWidth, transform(position.x - headWidth) - height / 2);
        ponyo.endShape(PConstants.CLOSE);
    }

    private void renderTentacles(PVector[] transforms) {

        float tw = height / SQUID_TENTACLE_COUNT;

        for(int t=0; t<SQUID_TENTACLE_COUNT; t++) {
            ponyo.beginShape();
            //bottom
            for (int i = transforms.length - 1; i >= 0; i--) {
                float obbMod = (float) (Math.sin(Math.PI/4*((float) i / SQUID_TENTACLE_COUNT) + Math.PI/8)) * 8.0f;
                PVector translatedVector = transforms[i].copy().add(0, -tw * t + height/2 - obbMod * (t-SQUID_TENTACLE_COUNT/2));
                ponyo.curveVertex(translatedVector.x, translatedVector.y);
            }
            //top
            for (int i = 0; i < transforms.length; i++) {
                float obbMod = (float) (Math.sin(Math.PI/4*((float) i / SQUID_TENTACLE_COUNT) + Math.PI/8)) * 8.0f;
                PVector translatedVector = transforms[i].copy().add(0, -tw * t + height/2 - tw - obbMod * (t-SQUID_TENTACLE_COUNT/2));
                ponyo.curveVertex(translatedVector.x, translatedVector.y);
            }
            ponyo.endShape(PConstants.CLOSE);
        }
    }

    @Override
    public int getRenderPriority() {
        return 1;
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
