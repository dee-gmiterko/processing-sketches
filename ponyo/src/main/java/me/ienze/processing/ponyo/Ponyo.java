package me.ienze.processing.ponyo;

import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.*;

public class Ponyo extends PApplet {

    public static final int CAPTURE_SPEED = 4;

    private Sea sea;
    private boolean capture;

    public void settings() {
//        size(800, 800);
        fullScreen();
    }

    @Override
    public void setup() {
        sea = new Sea(this);
    }

    public void draw() {
        background(255);

        sea.render();

        if(capture && frameCount % CAPTURE_SPEED == 0) {
            saveFrame("otuput/####.png");
        }
    }

    public int getMinFillSize() {
        return (int) (dist(0, 0, width, height) * 1.1);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKey() == ' ') {
            sea = new Sea(this);
        }
        if(event.getKey() == 'c') {
            capture = !capture;
        }
    }

    public Color randomMoveColor(Color color, int colorRange) {
        return new Color(inColorRange(color.getRed() + random(-colorRange, colorRange)), inColorRange(color.getGreen() + random(-colorRange, colorRange)), inColorRange(color.getBlue() + random(-colorRange, colorRange)));
    }

    private int inColorRange(float i) {
        if(i < 0) {
            return 0;
        }
        if(i > 255) {
            return 255;
        }
        return (int) i;
    }
}
