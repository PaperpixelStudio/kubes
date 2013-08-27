package net.paperpixel.fk.gui.wall_controller;

/**
 * View for the controller, calls display() on each draw()
 */

import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.KubeType;
import processing.core.PApplet;

public class SetupWallView extends WallView<SetupWallController> {

    private FlashKubes p5;

    public SetupWallView(FlashKubes p5) {
        super(p5);
        this.p5 = p5;
    }


    @Override
    public void display(PApplet theApplet, SetupWallController theController) {
        theApplet.pushMatrix();
        try {
            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    int myTypeIndex = theController.getKubeTypeIndexes()[i][j];
                    KubeType myType = KubeType.values()[myTypeIndex];
                    theApplet.fill(myType.getColor().getRGB());
                    
                    drawKube(i, j, theApplet, theController);
                }
            }
        } catch (NullPointerException e) {
            theController.createKubeTypeIndexes();
        } catch (ArrayIndexOutOfBoundsException e) {
            theController.createKubeTypeIndexes();
        }
        theApplet.popMatrix();
    }
}
