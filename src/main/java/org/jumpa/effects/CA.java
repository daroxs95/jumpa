package org.jumpa.effects;

import hype.H;
import hype.HBox;
import hype.extended.behavior.HOscillator;
import hype.extended.layout.HCircleLayout;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Wrapper;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.opengl.PShader;

public class CA extends Effect {
    PShader ca;
    int channel = 6;

    public CA(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "CA";
        ca = app.loadShader(
                Wrapper.dataPath + "shaders/CA/frag.glsl"
        );
        ca.set("sketchSize", (float) app.width, (float) app.height);
    }

    public void update() {
        ca.set("sketchSize", (float) app.width, (float) app.height);
        ca.set("barrelPower", audio.audioData[channel] * 30.0f);

        app.filter(ca);
    }

    public void nextVariant() {
        channel = (channel + 1) % audio.audioRange;
    }

    public String getVariant() {
        return "Channel: " + channel;
    }
}
