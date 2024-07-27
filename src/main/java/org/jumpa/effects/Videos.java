package org.jumpa.effects;
import hype.HBox;
import hype.HSphere;
import org.jumpa.phwrapper.Audio;
import org.jumpa.phwrapper.Effect;
import org.jumpa.phwrapper.Video;
import processing.core.*;

public class Videos extends Effect {
    Video video;
    String videoFilePath = "F:\\work\\mine\\harbour.space\\painting.sound\\jumpa\\data\\videos\\1h.mp4";

    HBox canvas;

    public Videos(Audio audio, PApplet app) {
        super(audio, app);
    }

    public void setup() {
        name = "Videos";
        video = new Video(audio, app);
        video.setup(videoFilePath);
        canvas = new HBox();
    }

    public void update() {
        app.push();
        video.update();
        canvas.texture(video.img);
        app.lights();
        app.noStroke();
        app.translate((app.width / 2), (app.height / 2), 0);
        canvas.size(app.height * video.aspectRatio, app.height);
        canvas.draw(app.g);
        app.pop();
    }
}
