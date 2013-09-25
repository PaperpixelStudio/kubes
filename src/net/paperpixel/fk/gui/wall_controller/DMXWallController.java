package net.paperpixel.fk.gui.wall_controller;

import controlP5.ControlP5;
import controlP5.Textlabel;
import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.util.Pair;

import java.util.ArrayList;

public class DMXWallController extends WallController<DMXWallController> {

    private int[][] dmxChannels;

    public DMXWallController(ControlP5 theControlP5, FlashKubes theP5, String theName) {
        super(theControlP5, theP5, theName);

//        WallView view = new WallView(theP5);
//        setView(new view);
        registerProperty("kubeTypeIndexes");
    }

    public void setDmxChannelProperty() {
        try {
            dmxChannels = new int[p5.getTotalLines()][p5.getTotalColumns()];

            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol(i, j);
                    dmxChannels[i][j] = myKube.getDmxChannel();
                }
            }
        } catch(NullPointerException e) {

        }
    }

    public int[][] getDmxChannels() {
        return dmxChannels;
    }
}
