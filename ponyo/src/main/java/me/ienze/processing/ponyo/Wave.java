package me.ienze.processing.ponyo;

import me.ienze.processing.ponyo.seaObject.*;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Wave extends Transformable {

    public static final int WAVE_LINE_WIDTH = 24;
    public static final int WAVE_PART_SIZE = 60;
    public static final int MIN_RESPAWN_TIME = 120;
    public static final int OPTIMAL_OBJECTS_COUNT = 5;
    public static final int OBJECT_COLOR_RANGE = 30;

    private PVector position;
    private float rotation;
    private int waveWidth;
    private Color color;
    private Color objectColor;
    private Set<SeaObject> floatingObjects = new HashSet<>();
    private int nextSpawnFrame = 0;
    private int lifeIndex21;

    public Wave(Ponyo ponyo, Sea sea, int waveWidth, PVector position) {
        super(ponyo, sea);
        this.waveWidth = waveWidth;
        this.position = position;

        lifeIndex21 = (int) Math.floor(ponyo.random(3));
        this.objectColor = new Color(ponyo.random(0.1f, 0.9f), ponyo.random(0.1f, 0.9f), ponyo.random(0.1f, 0.9f));

        while(floatingObjects.size() < OPTIMAL_OBJECTS_COUNT) {
            spawnObjects(1.0f);
        }

        while(ponyo.random(1) > 0.5) {
            this.floatingObjects.add(new Ship(ponyo, this, new PVector(ponyo.random(ponyo.getMinFillSize()), 0)));
        }

    }

    public void render() {

        ponyo.pushMatrix();
        ponyo.translate(position.x, position.y);
        ponyo.rotate(rotation);

        PVector[] transforms = new PVector[ponyo.getMinFillSize() / WAVE_PART_SIZE];
        for(int i = 0; i<transforms.length; i++) {
            transforms[i] = new PVector(i * WAVE_PART_SIZE, transform(i * WAVE_PART_SIZE));
        }

        for(SeaObject obj : floatingObjects) {
            if(obj.getRenderPriority() <= 0) {
                obj.render();
            }
        }

        renderWaveBackground(transforms);

        for(SeaObject obj : floatingObjects) {
            if(obj.getRenderPriority() > 0 && obj.getRenderPriority() <= 100) {
                obj.render();
            }
        }

        renderWaveForeground(transforms);

        for(SeaObject obj : floatingObjects) {
            if(obj.getRenderPriority() > 100) {
                obj.render();
            }
        }

        ponyo.popMatrix();

        if(nextSpawnFrame < ponyo.frameCount) {
            spawnObjects(0);
            nextSpawnFrame = ponyo.frameCount + MIN_RESPAWN_TIME;
        }
    }

    private void spawnObjects(float widthMod) {

        this.floatingObjects.removeIf(seaObject -> seaObject.getPosition().x > ponyo.getMinFillSize() || seaObject.getPosition().x < 0);

        if(this.floatingObjects.size() < OPTIMAL_OBJECTS_COUNT) {
            switch (lifeIndex21) {
                case 0:
                    SmallFish smallFish = new SmallFish(ponyo, this, new PVector(ponyo.random(ponyo.getMinFillSize()) * widthMod, ponyo.random(waveWidth * WAVE_LINE_WIDTH)));
                    this.floatingObjects.add(smallFish);
                    break;
                case 1:
                    Eel eel = new Eel(ponyo, this, new PVector(ponyo.random(ponyo.getMinFillSize()) * widthMod, ponyo.random(WAVE_LINE_WIDTH / 2, waveWidth * WAVE_LINE_WIDTH - WAVE_LINE_WIDTH / 2)));
                    this.floatingObjects.add(eel);

                    objectColor = ponyo.randomMoveColor(objectColor, OBJECT_COLOR_RANGE);
                    eel.setColor(objectColor);

                    break;
                case 2:
                    Squid squid = new Squid(ponyo, this, new PVector(ponyo.random(ponyo.getMinFillSize()) * widthMod, ponyo.random(WAVE_LINE_WIDTH / 2, waveWidth * WAVE_LINE_WIDTH - WAVE_LINE_WIDTH / 2)));
                    this.floatingObjects.add(squid);

                    objectColor = ponyo.randomMoveColor(objectColor, OBJECT_COLOR_RANGE);
                    squid.setColor(objectColor);
                default:
                    break;
            }
        }
    }

    private void renderWaveForeground(PVector[] transforms) {
        ponyo.noFill();
        ponyo.stroke(10);

        for(int i=0; i<=waveWidth; i++) {
            ponyo.beginShape();
            for (PVector vector : transforms) {
                PVector translatedVector = vector.copy().add(0, i * WAVE_PART_SIZE);
                ponyo.curveVertex(translatedVector.x, translatedVector.y);
            }
            ponyo.endShape();
        }
    }

    private void renderWaveBackground(PVector[] transforms) {

        if(color == null) {
            return;
        }

        ponyo.beginShape();

        ponyo.fill(color.getRGB());
        ponyo.noStroke();

        //top
        for(int i = 0; i<transforms.length; i++) {
            ponyo.curveVertex(transforms[i].x, transforms[i].y);
        }
        //bottom
        for(int i = transforms.length - 1; i>=0; i--) {
            PVector translatedVector = transforms[i].copy().add(0, waveWidth * WAVE_PART_SIZE);
            ponyo.curveVertex(translatedVector.x, translatedVector.y);
        }

        ponyo.endShape(PConstants.CLOSE);
    }

    public PVector getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public Color getColor() {
        return color;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<SeaObject> getFloatingObjects() {
        return floatingObjects;
    }
}
