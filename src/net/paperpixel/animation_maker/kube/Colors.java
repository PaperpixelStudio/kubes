package net.paperpixel.animation_maker.kube;

import java.awt.*;

public enum Colors {
    WHITE("white", new Color(255, 255, 255), 1),
    PURPLE("purple", new Color(213, 126, 222), 5),
    BLUE("blue", new Color(76, 182, 255), 2),
    TURQUOISE("turquoise", new Color(49, 240, 205), 4),
    GREEN("green", new Color(179, 227, 52), 6),
    YELLOW("yellow", new Color(238, 238, 14), 3),
    ORANGE("orange", new Color(241, 202, 22), 7),
    RED("red", new Color(241, 67, 70), 8);

    private final int id;
    String name;
    Color color;

    Colors(String theName, Color theColor, int theId) {
        name = theName;
        color = theColor;
        id = theId;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
