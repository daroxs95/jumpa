package org.jumpa;

import hype.H;
import org.jumpa.effects.*;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Wrapper;

import java.util.ArrayList;

public class Main extends Wrapper {
    Audio audio = new Audio(this, false);
    ArrayList<Effect> effects = new ArrayList<>();

    int clrBg = 0x00000;
    int framesDelayed = 0;
    boolean showUi = false;
    boolean orbitControls = false;
    boolean hotSwap = true;

    boolean skipClearColor = false;
    boolean render = false;
    int renderNum = 0;
    int renderMax = 400;
    int renderMod = 20;
    String renderPath = "../renders_1/";

    public void settings() {
        super.settings();
        fullScreen();
    }

    public void setup() {
        H.init(this);
        audio.setup();
        background(clrBg);
        frameRate(60);

        effects.add(new CenterSphere(audio, this));
        effects.add(new CameraMovement(audio, this));
        effects.add(new Atmosphere(audio, this));
        effects.add(new CA(audio, this));
        effects.add(new Edges(audio, this));
        effects.add(new BlendVideo(audio, this));

        if (!hotSwap) {
            // enable all effects
            effects.forEach(effect -> effect.enabled = true);
        }

        effects.forEach(Effect::setup);

        audio.showAudioVisualizer = showUi;
    }

    public void draw() {
        if (hotSwap) {
            effects.forEach(effect -> effect.enabled = false);
//            effects.get(0).enabled = true; // CenterSphere
            effects.get(1).enabled = true; // CameraMovement
            effects.get(2).enabled = true; // Atmosphere
            effects.get(3).enabled = true; // CA
            effects.get(4).enabled = true; // Edges
//            effects.get(5).enabled = true; // BlendVideo
        }

        if (audio.easedAudioData[1] > 0.5) {
            skipClearColor = true;
            framesDelayed++;
        } else {
            skipClearColor = false;
            framesDelayed = 0;
        }

        if (!skipClearColor || framesDelayed >= 40) {
            background(clrBg);
        }

        audio.update();

        push();

        if (mousePressed && orbitControls) {
            translate(width / 2, height / 2, 0);
            rotateX(radians(mouseY));
            rotateY(radians(mouseX));
            translate(-width / 2, -height / 2, 0);
        }

        effects.forEach(Effect::conditionalUpdate);

        pop();

        if (render) {
            showUi = false;
            if (frameCount % renderMod == 0) {
                String formattedNum = String.format("%06d", renderNum);
                saveFrame(renderPath + "render" + formattedNum + ".png");
                renderNum++;
                if (renderNum >= renderMax) exit();
            }
        }

        if (showUi) {
            noLights();
            noTint();
            hint(DISABLE_DEPTH_TEST);
            hint(DISABLE_DEPTH_SORT);
            perspective();
            text("FPS: " + (int) frameRate, 20, 22);
            for (int i = 0; i < effects.size(); i++) {
                textSize(18);
                Effect effect = effects.get(i);
                text(effect.name + ": " + effect.getVariant() + " (Change variant by num" + i + ")", 20, (i + 2) * 22);
            }
            hint(ENABLE_DEPTH_TEST);
            audio.visualizer();
        }

    }

    public void keyPressed() {
        effects.forEach(Effect::keyPressed);

        switch (key) {
            case 'o':
                orbitControls = !orbitControls;
                break;
            case ' ':
                if (audio.player.isPlaying()) audio.player.pause();
//                else audio.player.play();
                else audio.player.loop();
                break;
            case 'm':
                if (audio.player.isMuted()) audio.player.unmute();
                else audio.player.mute();
                break;
            case 'v':
                audio.showAudioVisualizer = !audio.showAudioVisualizer;
                showUi = !showUi;
                break;
            case 'p':
                for (int i = 0; i < audio.peakAudioData.length; i++) {
                    println(audio.peakAudioData[i]);
                }
                break;
            case '0':
                effects.get(0).nextVariant();
                break;
            case '1':
                effects.get(1).nextVariant();
                break;
            case '2':
                effects.get(2).nextVariant();
                break;
            case '3':
                effects.get(3).nextVariant();
                break;
            case '4':
                effects.get(4).nextVariant();
                break;
            default:
                break;
        }
    }
}