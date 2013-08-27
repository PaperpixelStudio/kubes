package net.paperpixel.fk.kube;

import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;

import java.awt.*;

public enum Colors {
    BLUE("blue", new Color(18, 84, 255)),
    YELLOW("yellow", new Color(233, 238, 25)),
    TURQUOISE("turquoise", new Color(32, 240, 61)),
    PURPLE("purple", new Color(195, 49, 222)),
    GREEN("green", new Color(50, 227, 0)),
    ORANGE("orange", new Color(143, 67 , 0)),
//    WHITE("white", new Color(255, 255, 255)),
    RED("red", new Color(255, 0, 0));

    String name;
    Color color;

    Colors(String theName, Color theColor) {
        name = theName;
        color = theColor;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
