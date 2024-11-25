package org.jumpa.effects;

import hype.H;
import hype.extended.behavior.HOscillator;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

import java.util.ArrayList;

public class CameraMovement extends Effect {
    HOscillator rX, rY, rZ, oscP;

    public CameraMovement(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "CameraMovement";

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

        app.translate(app.width / 2, app.height / 2, 0);

        app.pop();
    }
}
