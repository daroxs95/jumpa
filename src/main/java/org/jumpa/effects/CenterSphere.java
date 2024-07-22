package org.jumpa.effects;

import hype.H;
import hype.HSphere;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

public class CenterSphere extends Effect {
    HSphere c = new HSphere();
    int channel = 0;

    public CenterSphere(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "CenterSphere";
    }

    public void update() {
        app.push();
        app.translate((app.width / 2), (app.height / 2), 0);
        c.noStroke();
        c.anchorAt(H.CENTER);
        c.size(audio.audioData[channel] * 1000);
        c.draw(app.g);
        app.pop();
    }

    public void nextVariant() {
        channel = (channel + 1) % audio.audioRange;
    }

    public String getVariant() {
        return "Channel: " + channel;
    }
}
