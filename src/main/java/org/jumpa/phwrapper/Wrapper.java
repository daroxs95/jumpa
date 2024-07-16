package org.jumpa.phwrapper;

import processing.core.PApplet;

public class Wrapper extends PApplet {
    int stageW = 900;
    int stageH = 900;

    public static String appName = "org.jumpa.Main";

    public String dataPath = "../data/";

    public void settings() {
        size(stageW, stageH, P3D);
    }

    static public void main(String[] passedArgs) {
        PApplet.main(appName);
    }
}
