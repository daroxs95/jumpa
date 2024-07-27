package org.jumpa.phwrapper;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.image.BufferedImage;

public class Video extends Effect {
    FFmpegFrameGrabber grabber;
    Java2DFrameConverter converter;
    public PImage img;

    Frame frame;
    BufferedImage bufferedImage;

    double videoFrameRate;
    double frameDuration; // Duration of each video frame in milliseconds
    double lastFrameTime = 0;
    double currentTime = 0;
    double videoTime = 0;
    double deltaTime = 0;

    public int aspectRatio;
    int frameCount = 0;
    int appFps = 60;

    public boolean isPlaying = false;
    boolean tieToFrameRate = false;

    public Video(Audio audio, PApplet app) {
        super(audio, app);
    }

    public Video(Audio audio, PApplet app, boolean tieToFrameRate) {
        super(audio, app);
        this.tieToFrameRate = tieToFrameRate;
    }

    double getMillis() {
        if (tieToFrameRate) {
            return app.frameCount * 1000.0 / appFps;
        }
        return app.millis();
    }

    public void setup(String filePath) {
        name = "Video";
        grabber = new FFmpegFrameGrabber(filePath);
        converter = new Java2DFrameConverter();
        try {
            grabber.start();
            videoFrameRate = grabber.getFrameRate(); // Get the video's frame rate
            frameDuration = 1000.0 / videoFrameRate;
            aspectRatio = grabber.getImageWidth() / grabber.getImageHeight();
            app.println("Video frame rate: " + videoFrameRate);
            frame = grabber.grabImage();
            if (frame != null) {
                bufferedImage = converter.convert(frame);
                if (bufferedImage != null) {
                    if (img == null || img.width != bufferedImage.getWidth() || img.height != bufferedImage.getHeight()) {
                        app.println("Creating new PImage");
                        img = new PImage(bufferedImage.getWidth(), bufferedImage.getHeight(), PConstants.ARGB);
                    }
                    bufferedImage.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
                    img.updatePixels();
                }
                frameCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastFrameTime = getMillis();
    }

    public void update() {
        currentTime = getMillis();
        double deltaTime = currentTime - lastFrameTime;
        lastFrameTime = currentTime;
        if (grabber == null) return;
        if (!isPlaying) return;
        videoTime += deltaTime;
        double shouldRenderFrame = videoTime / frameDuration;
        if (frameCount >= shouldRenderFrame) return;
        try {
            frame = grabber.grabImage();
            if (frame != null) {
                bufferedImage = converter.convert(frame);
                if (bufferedImage != null) {
                    if (img == null || img.width != bufferedImage.getWidth() || img.height != bufferedImage.getHeight()) {
                        app.println("Creating new PImage");
                        img = new PImage(bufferedImage.getWidth(), bufferedImage.getHeight(), PConstants.ARGB);
                    }
                    bufferedImage.getRGB(0, 0, img.width, img.height, img.pixels, 0, img.width);
                    img.updatePixels();
                }
                frameCount++;
            } else {
                grabber.restart(); // Loop the video
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPressed() {
        if (app.key == ' ') {
            isPlaying = !isPlaying;
        }
    }
}
