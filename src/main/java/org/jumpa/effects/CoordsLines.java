package org.jumpa.effects;

import hype.H;
import hype.HSphere;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

public class CoordsLines extends Effect {
    float longLine = 9000;

    public CoordsLines(Audio audio, PApplet app) {
        super(audio, app);
    }

    void randLength() {
        longLine = audio.audioData[0] * (app.random(500, 2000));
    }

    public void setup() {
        name = "CoordsLines";
    }

    public void update() {
        app.push();
        app.strokeWeight(audio.audioData[2] * 10);
        app.stroke(19, 20, 21, 250);
        app.noFill();

        randLength();
        app.line(-longLine, 0, 0, longLine, 0, 0);
        randLength();
        app.line(0, -longLine, 0, 0, longLine, 0);
        randLength();
        app.line(0, 0, -longLine, 0, 0, longLine);

        float midLine = longLine / 2;

        // Midpoints on X-axis
        randLength();
        app.line(0, 0, 0, midLine, midLine, 0);
        randLength();
        app.line(0, 0, 0, -midLine, midLine, 0);
        randLength();
        app.line(0, 0, 0, midLine, -midLine, 0);
        randLength();
        app.line(0, 0, 0, -midLine, -midLine, 0);

        // Midpoints on Y-axis
        randLength();
        app.line(0, 0, 0, midLine, 0, midLine);
        randLength();
        app.line(0, 0, 0, -midLine, 0, midLine);
        randLength();
        app.line(0, 0, 0, midLine, 0, -midLine);
        randLength();
        app.line(0, 0, 0, -midLine, 0, -midLine);

        // Midpoints on Z-axis

        randLength();
        app.line(0, 0, 0, 0, midLine, midLine);
        randLength();
        app.line(0, 0, 0, 0, -midLine, midLine);
        randLength();
        app.line(0, 0, 0, 0, midLine, -midLine);
        randLength();
        app.line(0, 0, 0, 0, -midLine, -midLine);
        app.pop();
    }
}
