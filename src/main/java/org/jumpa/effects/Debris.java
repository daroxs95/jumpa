package org.jumpa.effects;

import hype.H;
import hype.HSphere;
import hype.extended.behavior.HOrbiter3D;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import processing.core.PApplet;

public class Debris extends Effect {
    int numItems = 18;
    HSphere[] objs;
    HOrbiter3D[] orbs;

    int orbRadius;
    float tempRadius = 0;
    int framesTilFullRadius = 5;
    int framesTilGrow = 10;
    float orbZSpeed = 2.5f;
    float orbYSpeed = 1.5f;
    int[] bounceChannel;

    public Debris(Audio audio, PApplet app, int numItems, int orbRadius) {
        super(audio, app);
        this.numItems = numItems;
        this.orbRadius = orbRadius;
    }

    public void setup() {
        name = "Debris";
        objs = new HSphere[numItems];
        orbs = new HOrbiter3D[numItems];
        bounceChannel = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            objs[i] = new HSphere();
            objs[i].size(3);
            objs[i].sphereDetail(1);

            orbs[i] = new HOrbiter3D();
            orbs[i] = new HOrbiter3D();
            orbs[i].zSpeed(orbZSpeed);
            orbs[i].ySpeed(orbYSpeed);
            orbs[i].radius(orbRadius);
            orbs[i].zAngle((int) app.random(360));
            orbs[i].yAngle((int) app.random(360));

//            bounceChannel[i] = (int) app.random(0, audio.audioRange - 1);
            bounceChannel[i] = 5;
        }
    }

    public void update() {
        if (app.frameCount < framesTilGrow) return;
        app.push();

        if (app.frameCount - framesTilGrow < framesTilFullRadius) {
            tempRadius = app.map(app.frameCount - framesTilGrow, 0, framesTilFullRadius, 0, orbRadius);
        }
        for (int i = 0; i < numItems; i++) {
            float orBounce = app.map(audio.easedAudioData[bounceChannel[i]], 0, 1, tempRadius, tempRadius * 4);
            orbs[i].radius(orBounce);

            float _z = app.map(audio.easedAudioData[7], 0, 1, -0.5f, 3);
            orbs[i].zSpeed(_z);

            float _y = app.map(audio.easedAudioData[2], 0, 1, -0.7f, 5);
            orbs[i].ySpeed(_y);

            orbs[i].run();

            objs[i].x(orbs[i].x());
            objs[i].y(orbs[i].y());
            objs[i].z(orbs[i].z());
            objs[i].noStroke();
            objs[i].anchorAt(H.CENTER);
            objs[i].draw(app.g);
        }
        app.pop();
    }
}
