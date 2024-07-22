package org.jumpa;

import hype.H;
import org.jumpa.effects.CenterSphere;
import org.jumpa.effects.ColorfulButterfly;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Wrapper;

import java.util.ArrayList;

public class Main extends Wrapper {
    Audio audio = new Audio(this);
    ArrayList<Effect> effects = new ArrayList<>();

    int clrBg = 0x00000;

    public void setup() {
        H.init(this);
        audio.setup();
        background(clrBg);

        effects.add(new CenterSphere(audio, this));
        effects.add(new ColorfulButterfly(audio, this));

        effects.forEach(Effect::setup);
    }

    public void draw() {
        background(clrBg);
        audio.update();

        // image(colors, 0, 0, width, 50);
        lights();

        effects.forEach(Effect::update);

        for (int i = 0; i < effects.size(); i++) {
            noLights();
            noTint();
            hint(DISABLE_DEPTH_TEST);
            hint(DISABLE_DEPTH_SORT);
            perspective();

            textSize(18);
            Effect effect = effects.get(i);
            text(effect.name + ": " + effect.getVariant(), 20, (i + 1) * 22);

            hint(ENABLE_DEPTH_TEST);
        }

        audio.visualizer();
    }

    public void keyPressed() {
        if (key == 'v') {
            audio.showAudioVisualizer = !audio.showAudioVisualizer;
        }
        if (key == ' ') {
            if (audio.player.isPlaying()) audio.player.pause();
            else audio.player.play();
        }
        if (key == 'm') {
            if (audio.player.isMuted()) audio.player.unmute();
            else audio.player.mute();
        }
        if (key == 'p') {
            for (int i = 0; i < audio.peakAudioData.length; i++) {
                println(audio.peakAudioData[i]);
            }
        }
        if (key == '0') {
            effects.getFirst().nextVariant();
        }
        if (key == '1') {
            effects.get(1).nextVariant();
        }
        if (key == '2') {
            effects.get(2).nextVariant();
        }
        if (key == '3') {
            effects.get(3).nextVariant();
        }
        if (key == '4') {
            effects.get(4).nextVariant();
        }
    }
}