package org.jumpa.effects;

import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Wrapper;
import processing.core.PApplet;
import processing.opengl.PShader;

public class Edges extends Effect {
    PShader edge;
    int channel = 3;
    boolean enabled = false;
    float threshold = 0.5f;

    public Edges(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "Edges";
        edge = app.loadShader(
                Wrapper.dataPath + "shaders/Edge/frag.glsl"
        );
        edge.set("sketchSize", (float) app.width, (float) app.height);
    }

    public void update() {
        edge.set("sketchSize", (float) app.width, (float) app.height);

        if (audio.audioData[channel] > threshold) {
            enabled = true;
        } else {
            enabled = false;
        }

        if (enabled) {
            app.filter(edge);
        }
    }

    public void nextVariant() {
        channel = (channel + 1) % audio.audioRange;
    }

    public String getVariant() {
        return "Channel: " + channel;
    }
}
