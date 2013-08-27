package net.paperpixel.animation_maker.kube;

import net.paperpixel.animation_maker.core.AMProcessing;
import net.paperpixel.fk.kube.Colors;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AMKubeWall extends AMProcessing {

    private AMKube[][] kubes;
    private static HashMap<Integer, Color> colors;
    private HashMap<Integer, Integer> states;

    public AMKubeWall(int totalLines, int totalColumns) {
        if(colors == null) {
            createKubeColors();
        }

        init(totalLines, totalColumns);
    }

    private void init(int totalLines, int totalColumns) {
        kubes = new AMKube[totalLines][totalColumns];
        for (int i = 0; i < totalLines; i++) {
            for(int j = 0; j < totalColumns; j++) {
                kubes[i][j] = new AMKube(i, j);
                kubes[i][j].setColor(colors.get(kubes[i][j].getId()));;;
                kubes[i][j].setPosition(
                        (int) (p5.getKubeWallPosition().x + (p5.getKubeSize() * j) + (p5.getKubeMargin() * j)),
                        (int) (p5.getKubeWallPosition().y + (p5.getKubeSize() * i) + (p5.getKubeMargin() * i))
                );
            }
        }
    }


    public void draw() {
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for(int j = 0; j < p5.getTotalColumns(); j++) {
                kubes[i][j].draw();
            }
        }
    }

    public void drawOnionSkin() {
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for(int j = 0; j < p5.getTotalColumns(); j++) {
                kubes[i][j].drawOnionSkin();
            }
        }
    }

    public AMKube getKubeFromMousePosition(PVector mouse) {
        int line = -1;
        int column = -1;

        for (int j = 0; j < kubes.length; j++) {
            if (mouse.y >= (p5.getKubeSize() * j) + (p5.getKubeMargin() * j) + p5.getKubeWallPosition().y
                    && mouse.y <= (p5.getKubeSize() * (j + 1)) + (p5.getKubeMargin() * (j + 1)) + p5.getKubeWallPosition().y) {
                line = j;
            }
        }

        for (int i = 0; i < kubes[0].length; i++) {
            int myLeftZoneX = (p5.getKubeSize() * i) + (p5.getKubeMargin() * i) + (int) p5.getKubeWallPosition().x;
            int myRightZoneX = (p5.getKubeSize() * (i + 1)) + (p5.getKubeMargin() * (i + 1) + (int) p5.getKubeWallPosition().x);

            if (mouse.x >= myLeftZoneX
                    && mouse.x <= myRightZoneX) {
                column = i;
            }
        }

        if (line > -1 && column > -1) {
            return getKubeFromLineCol(line, column);
        } else {
            return null;
        }
    }

    public AMKube getKubeById(int index) {
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for(int j = 0; j < p5.getTotalColumns(); j++) {
                if(kubes[i][j].getId() == index) {
                    return kubes[i][j];
                }
            }
        }
        return null;
    }



    private AMKube getKubeFromLineCol(int theLine, int theCol) {
        try {
            return kubes[theLine][theCol];
        } catch(ArrayIndexOutOfBoundsException e) {
            PApplet.println("Kube doesn't exist at line " + theLine + " and col " + theCol);
            return null;
        }
    }


    public void setAllKubesActive(boolean active) {
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for(int j = 0; j < p5.getTotalColumns(); j++) {
                kubes[i][j].setActive(active);
            }
        }
    }

    /*Static methods*/


    public static Color getRandomColor() {
        int randomIndex = (int) p5.random(0, Colors.values().length - 1);
        return Colors.values()[randomIndex].getColor();
    }

    public static Color getRandomColor(Color thePreviousColor) {
        int randomIndex = (int) p5.random(0, Colors.values().length);
        Color newColor = Colors.values()[randomIndex].getColor();

        if (newColor.equals(thePreviousColor)) {
            return getRandomColor(thePreviousColor);
        }

        return newColor;
    }

    private static void createKubeColors() {
        colors = new HashMap<Integer, Color>();

        for (int i = 0; i < p5.getTotalLines(); i++) {
            for (int j = 0; j < p5.getTotalColumns(); j++) {
                int id = createId(i, j);

                colors.put(id, getRandomColor());
            }
        }
    }

    public static int createId(int theLine, int theColumn) {
        return (p5.getTotalColumns() * p5.getTotalLines()) - ((theLine * p5.getTotalColumns()) + theColumn) - 1;
    }


    public HashMap<Integer, Boolean> getStates() {
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for (int j = 0; j < p5.getTotalColumns(); j++) {
                map.put(kubes[i][j].getId(), kubes[i][j].isActive());
            }
        }
        return map;
    }

    public void setStates(HashMap<Integer, Boolean> states) {
        for(Map.Entry<Integer, Boolean> entry : states.entrySet()) {
            getKubeById(entry.getKey()).setActive(entry.getValue());
        }
    }
}