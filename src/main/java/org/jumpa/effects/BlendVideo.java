package org.jumpa.effects;

import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Video;
import org.jumpa.phwrapper.Wrapper;
import processing.core.PApplet;
import processing.opengl.PShader;

public class BlendVideo extends Effect {
    PShader s;

    Video video;
    String videoFilePath;

    public BlendVideo(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "BlendVideo";
        video = new Video(audio, app, false);
        videoFilePath = Wrapper.dataPath + "videos/1.mp4";

        app.println(videoFilePath);

        video.setup(videoFilePath);

        s = app.loadShader(
                Wrapper.dataPath + "shaders/Blend/frag.glsl"
        );
        s.set("sketchSize", (float) app.width, (float) app.height);
    }

    public void update() {
        video.update();
        s.set("sketchSize", (float) app.width, (float) app.height);
        if (video.img != null) {
            s.set("texture2", video.img);
        }
        app.filter(s);
    }

    public void keyPressed() {
        video.keyPressed();
    }
}
