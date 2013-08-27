package net.paperpixel.fk.gui.wall_controller;

import controlP5.ControllerView;
import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.KubeWall;
import processing.core.PApplet;

public class WallView<T> implements ControllerView<T> {

    protected FlashKubes p5;

    public WallView(FlashKubes theP5) {
        p5 = theP5;
    }

    public void display(PApplet theApplet, T theController) {
        theApplet.pushMatrix();
        try {
            WallController myController = (WallController) theController;
            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    int myAlpha = 100;
                    if (myController.getSelectedKubeLineCol().x == i && myController.getSelectedKubeLineCol().y == j) {
                        myAlpha = 255;
                    }
                    theApplet.fill(255, myAlpha);
                    drawKube(i, j, theApplet, myController);
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {

        }
        theApplet.popMatrix();
    }

    protected void drawKube(int theLine, int theCol, PApplet theApplet, WallController theController) {
        theApplet.rect((WallController.SQUARE_SIZE + WallController.PADDING) * KubeWall.getInvertedColumn(theCol),
                (WallController.SQUARE_SIZE + WallController.PADDING) * KubeWall.getInvertedLine(theLine),
                WallController.SQUARE_SIZE, WallController.SQUARE_SIZE);
    }
}
