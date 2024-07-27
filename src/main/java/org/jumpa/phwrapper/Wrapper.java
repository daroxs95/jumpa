package org.jumpa.phwrapper;

import processing.core.PApplet;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Wrapper extends PApplet {
    public int stageW = 1600;
    public int stageH = 900;

    public static String appName = "org.jumpa.Main";

    public static String dataPath = "../data/";

    public void settings() {
        size(stageW, stageH, P3D);
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        String relativePath = "data";
        Path absolutePath = currentWorkingDir.resolve(relativePath);
        dataPath = absolutePath + "/";
    }

    static public void main(String[] passedArgs) {
        PApplet.main(appName);
    }
}
