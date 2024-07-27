package org.jumpa.effects;

import hype.H;
import hype.HBox;
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
        c.sphereDetail(2);
    }

    public void update() {
        app.push();
        app.translate((app.width / 2), (app.height / 2), 0);

        c.stroke(255, 255, 255, 200);
        c.fill(50, 50, 50, 255);
//        c.noStroke();
        c.rotateX(app.PI / 2);
        c.rotateY(app.PI / 2);
        c.anchorAt(H.CENTER);
        if (audio.audioData[channel] > 0.4f) {
            c.size(app.map(audio.audioData[channel], 0, 1, 0, 600));
        } else {
            c.size(0);
        }
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
