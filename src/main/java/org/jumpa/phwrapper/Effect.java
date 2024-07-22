package org.jumpa.phwrapper;

import processing.core.PApplet;

public class Effect {
    public Audio audio;
    public PApplet app;
    public String name = "";

    public Effect(Audio audio, PApplet app) {
        this.audio = audio;
        this.app = app;
    }

    // runs once for setup
    public void setup() {
        name = "Effect";
    }

    // runs every frame / updates
    public void update() {
    }

    // use it for changing audio channel used for interactivity for example
    public void nextVariant() {
    }

    public String getVariant() {
        return "default";
    }
}
