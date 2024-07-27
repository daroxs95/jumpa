package org.jumpa.effects;

import hype.H;
import hype.HSphere;
import hype.extended.behavior.HOrbiter3D;
import hype.extended.behavior.HOscillator;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

import java.util.ArrayList;

public class Atmosphere extends Effect {

    int orbRadius;
    float orbZSpeed = 1.5f;
    float orbYSpeed = 0.5f;

    HOscillator rX, rY, rZ, oscP;

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
        objs.add(new Debris(audio, app, 150, 670));

        objs.forEach(Effect::setup);
        lines.setup();

        rX = new HOscillator().range(-180, 180).speed(1);
        rY = new HOscillator().range(-180, 180).speed(1);
        rZ = new HOscillator().range(-180, 180).speed(1);

        oscP = new HOscillator().range(1.5f, 4.0f).speed(0.25f).freq(1.0f).waveform(H.SINE);
    }

    public void update() {
        float _rX = app.map(audio.audioData[3], 0, 1, -0.05f, 2.0f);
        rX.speed(_rX);
        rX.run();

        float _rY = app.map(audio.audioData[6], 0, 1, -0.05f, 2.0f);
        rY.speed(_rY);
        rY.run();

        float _rZ = app.map(audio.audioData[0], 0, 1, -0.05f, 2.0f);
        rZ.speed(_rZ);
        rZ.run();

        float _oscP = app.map(audio.audioData[0], 0, 1, -0.05f, 3.0f);
        oscP.speed(_oscP);
        oscP.run();
        app.push();
        app.perspective(app.PI / oscP.cur(), (float) (app.width * 2) / (app.height * 2), 0.1f, 1000000);
//        app.perspective(app.map(audio.audioData[0], 0, 1, -0.5f, 10.0f), (float) (app.width * 2) / (app.height * 2), 0.1f, 1000000);
//        app.perspective(app.map(audio.easedAudioData[0], 0, 1, -0.5f, 10.0f), (float) (app.width * 2) / (app.height * 2), 0.1f, 1000000);

        app.translate(app.width / 2, app.height / 2, 0);
        app.rotateX(app.radians(rX.cur()));
        app.rotateY(app.radians(rY.cur()));
        app.rotateZ(app.radians(rZ.cur()));

        lines.update();
        objs.forEach(Effect::update);

        app.pop();
    }
}
