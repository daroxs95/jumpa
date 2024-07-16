package org.jumpa;

import hype.H;
import hype.HBox;
import hype.extended.behavior.HOscillator;
import hype.extended.layout.HCircleLayout;
import org.jumpa.phwrapper.Wrapper;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends Wrapper {

    int clrBg = 0xCCCCCC;

    int numItems = 70;

    PImage colors;
    HOscillator[] osc = new HOscillator[numItems];
    HOscillator[] oscR = new HOscillator[numItems];

    HCircleLayout layout;
    HBox[] obj = new HBox[numItems];// this is the obj where we store the grid behavior

    public void setup() {
        H.init(this);
        background(clrBg);

        colors = loadImage(dataPath + "colors/rainbow.png");

        layout = new HCircleLayout();
        layout.angleStep(360 / numItems).radius(30);

        for (int i = 0; i < numItems; ++i) {
            PVector _p = layout.getNextPoint();

            obj[i] = new HBox();
            obj[i].loc(_p.x, _p.y, _p.z);
            obj[i].size(20, 2000, 20);

            obj[i].rotate(layout.angleStep() * i);

            osc[i] = new HOscillator()
                    .range(0, colors.width - 1)
                    .speed(1).currentStep(i * 10)
                    .waveform(H.SINE);

            oscR[i] = new HOscillator()
                    .range(-180, 180)
                    .speed(1)
                    .currentStep(i);
        }
    }

    public void draw() {
        background(clrBg);

        // image(colors, 0, 0, width, 50);
        lights();

        push();
        translate((width / 2), (height / 2), 0);
        for (int i = 0; i < numItems; ++i) {
            osc[i].run();
            oscR[i].run();
            obj[i].noStroke();
            obj[i].fill(colors.get((int)(osc[i].curr()), 0));
            obj[i].rotationZ(oscR[i].curr());
            // obj[i].rotationX(oscR[i].curr());
            obj[i].draw(this.g);
        }
        pop();
    }
}