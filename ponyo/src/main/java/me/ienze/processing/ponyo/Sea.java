package me.ienze.processing.ponyo;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sea extends Transformable {

    public static final int WAVE_COLOR_RANGE = 32;

    private List<Wave> waves = new ArrayList<>();
    private static List<String> expressions = new ArrayList<String>();

    static {
        expressions.add("sin(pi*(x+t*2)/300.0)*50.0");
//        expressions.add("t");
        expressions.add("sin(pi*(x+t*5)/100.0)*15.0");
        expressions.add("sin(pi*(x+t*2.7)/300.0)*50.0 - x/200");
        expressions.add("sin(pi*(x+t)/600.0)*200.0");
        expressions.add("(x/1920)*(x/1920)");
    }

    public Sea(Ponyo ponyo) {
        super(ponyo, null);

        generateWaves();
    }

    public void render() {

        ponyo.strokeWeight(1.3f);

        for (Wave wave : waves) {
            wave.render();
        }
    }

    private void generateWaves() {
        waves.clear();
        Color color = new Color(100, 100, 240);

        Wave wave = new Wave(ponyo, this, (ponyo.height / Wave.WAVE_LINE_WIDTH) + 5, new PVector(-Wave.WAVE_PART_SIZE * 4, -100));
        wave.setExpression("sin(pi*(x+t)/300.0)*10.0");
        wave.setColor(color);

        waves.add(wave);

        for(int i=0; i<(3+(int)(Math.round(Math.random() * 5))); i++) {

            color = ponyo.randomMoveColor(color, WAVE_COLOR_RANGE);

            PVector position = new PVector(-Wave.WAVE_PART_SIZE * 4, ponyo.random(0, ponyo.height));

            wave = new Wave(ponyo, this, (int) ponyo.random(6, 12), position);
            wave.setExpression(expressions.get((int)Math.round(Math.random() * (expressions.size() - 1) )));
            wave.setColor(color);

            waves.add(wave);
        }
    }
}
