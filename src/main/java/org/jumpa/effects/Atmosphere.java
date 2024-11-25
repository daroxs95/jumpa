package org.jumpa.effects;

import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

import java.util.ArrayList;

public class Atmosphere extends Effect {
    ArrayList<Debris> objs = new ArrayList<>();

    CoordsLines lines;

    public Atmosphere(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "Atmosphere";
        lines = new CoordsLines(audio, app);
        objs.add(new Debris(audio, app, 80, 50));
        objs.add(new Debris(audio, app, 180, 100));
        objs.add(new Debris(audio, app, 80, 150));
        objs.add(new Debris(audio, app, 380, 500));
        objs.add(new Debris(audio, app, 200, 570));
        objs.add(new Debris(audio, app, 250, 670));

        objs.forEach(Effect::setup);
        lines.setup();
    }

    public void update() {
        app.push();
        app.translate(app.width / 2, app.height / 2, 0);
        lines.update();
        objs.forEach(Effect::update);

        app.pop();
    }
}
