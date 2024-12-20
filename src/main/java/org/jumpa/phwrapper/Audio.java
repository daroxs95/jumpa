package org.jumpa.phwrapper;

import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.*;

public class Audio {
    public AudioPlayer player;

    boolean autoplay = true;
    boolean useInput = false;
    boolean showPeakAudioData = false;
    boolean showReferenceAudioData = false;
    public boolean showAudioVisualizer = true;

    Minim minim;
    PApplet app;
    AudioInput in;
    FFT fft;

    public int audioRange = 32;
    int barWidth = 8;
    int barGap = 1;
    int barMinHeight = 2;
    int barMaxHeight = 100;
    int amplifyFactor = 40;
    float audioEaseFactor = 0.1f;
    // This is just based on experimental observation
    // look into https://www.nti-audio.com/en/support/know-how/fast-fourier-transform-fft
    int maxFreq = 100;
    public float[] audioData = new float[audioRange];
    public float[] audioData2 = new float[audioRange];
    public float[] easedAudioData = new float[audioRange];
    public float[] peakAudioData = new float[audioRange];

    public Audio(PApplet app, boolean autoplay) {
        this.app = app;
        this.autoplay = autoplay;
    }

    // This rescales frequencies in the unit square
    float rescale(float val) {
        return (float) (1 - (3 / Math.exp(5 * val + 1.2)));
    }

    float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public int getColor(int val) {
        if (val <= 2) return 0x666666; // no audio
        else if (val <= 10) return 0x2EA893; // 2 - 10
        else if (val <= 20) return 0x64BE7A; // 11 - 20
        else if (val <= 30) return 0x9AD561; // 21 - 30
        else if (val <= 40) return 0xCCEA4A; // 31 - 40
        else if (val <= 50) return 0xFFFF33; // 41 - 50
        else if (val <= 60) return 0xF8EF33; // 51 - 60
        else if (val <= 70) return 0xFFC725; // 61 - 70
        else if (val <= 80) return 0xFF9519; // 71 - 80
        else if (val <= 90) return 0xFF620C; // 81 - 90
        else return 0xff3300; // 91 - 100
    }

    public int[] hexToRgb(int hex) {
        int[] rgb = new int[3];
        rgb[0] = (hex >> 16) & 0xFF;
        rgb[1] = (hex >> 8) & 0xFF;
        rgb[2] = hex & 0xFF;
        return rgb;
    }

    // runs once for setup
    public void setup() {
        minim = new Minim(app);

        if (useInput) {
            // handle input
            in = minim.getLineIn(Minim.MONO);
            fft = new FFT(in.bufferSize(), in.sampleRate());
        } else {
            player = minim.loadFile(Wrapper.dataPath + "audio/GUTSxDOOM.mp3");
            fft = new FFT(player.bufferSize(), player.sampleRate());
        }

        fft.linAverages(audioRange);
        fft.window(FFT.GAUSS);

        for (int i = 0; i < audioRange; i++) {
            peakAudioData[i] = 0;
        }
    }

    // runs every frame / updates
    public void update() {
        if (app.frameCount == 1 && autoplay && !useInput) {
            player.loop();
        }

        if (useInput) {
            fft.forward(in.mix);
        } else {
            fft.forward(player.mix);
        }
        for (int i = 0; i < audioRange; i++) {
            audioData[i] = amplifyFactor * fft.getAvg(i) / maxFreq * rescale((float) i / audioRange);
            audioData[i] = clamp(audioData[i], 0, 1);
//            audioData2[i] = fft.getAvg(i) * (float) (((float) ((i + 1) / 1) / audioRange) * Math.exp(1.0)) * 40;
//            audioData2[i] = clamp(audioData2[i], 0, 100) / 100;
            easedAudioData[i] += (audioData[i] - easedAudioData[i]) * audioEaseFactor;
            if (showPeakAudioData && audioData[i] > peakAudioData[i]) {
                peakAudioData[i] = audioData[i];
            }
        }
    }

    public void visualizer() {
        if (!showAudioVisualizer) return;

        app.noLights();
        app.noTint();
        app.hint(app.DISABLE_DEPTH_TEST);
        app.hint(app.DISABLE_DEPTH_SORT);
        app.perspective();

        app.push();
        app.translate((app.width / 2), (app.height / 2), 0);
        app.translate(-((float) audioRange / 2 * (barWidth + barGap)), (float) app.height / 2 - 100);
        app.noStroke();

        app.fill(0, 0, 0, 245);
        app.rect(-20, -(barMaxHeight + 50) / 2, 40 + audioRange * (barWidth + barGap), barMaxHeight + 50);
        app.rectMode(app.CENTER);

        for (int i = 0; i < audioRange; i++) {
            if (showPeakAudioData) {
                app.fill(255, 0, 0, 50);
                app.rect(i * (barWidth + barGap), 0, barWidth, peakAudioData[i] * barMaxHeight);
            }

            app.fill(173, 183, 196, 50);
            app.rect(i * (barWidth + barGap), 0, barWidth, barMinHeight + easedAudioData[i] * barMaxHeight);

            int[] color = hexToRgb(getColor((int) (audioData[i] * 100)));
            app.fill(color[0], color[1], color[2]);
            app.rect(i * (barWidth + barGap), 0, barWidth, barMinHeight + audioData[i] * barMaxHeight);
            if (showReferenceAudioData) {
                app.rect(i * (barWidth + barGap), -200, barWidth, barMinHeight + audioData2[i] * barMaxHeight);
            }

            app.textAlign(app.CENTER, app.CENTER);
            app.textSize(8);
            app.text(i, i * (barWidth + barGap), 60);
        }
        app.pop();
        app.rectMode(app.CORNER);

        app.hint(app.ENABLE_DEPTH_TEST);
    }

    public void stop() {
        if (!useInput) player.close();
        if (useInput) in.close();
        minim.stop();
    }
}
