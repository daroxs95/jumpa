package org.jumpa.effects;

import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Wrapper;
import processing.core.PApplet;
import processing.core.PShape;

public class Slayer extends Effect {
    PShape slayer;

    public Slayer(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "Slayer";
        slayer = app.loadShape(Wrapper.dataPath + "models/slayer.obj");
    }

    public void update() {
        app.push();
        app.translate((app.width / 2), (app.height / 2), -200);
        app.lights();
//        app.rotateZ(app.PI);
        app.scale(1, -1);
        app.scale(1200f);
        app.shape(slayer);
        app.pop();
    }
}
