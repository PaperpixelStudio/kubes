package net.paperpixel.fk.gui.wall_controller;

import controlP5.ControlP5;
import controlP5.Controller;
import controlP5.Pointer;
import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeWall;
import processing.core.PVector;

public class WallController<T> extends Controller<T> {

    public static final int SQUARE_SIZE = 20;
    public static final int PADDING = 4;

    protected FlashKubes p5;

    private PVector selectedKubeLineCol = new PVector(-1, -1);
    private AbstractKube selectedKube = null;

    public WallController(ControlP5 theControlP5, FlashKubes theP5, String theName) {
        super(theControlP5, theName);

        p5 = theP5;
        float[] values = new float[2];
        values[0] = -1;
        values[1] = -1;
        setArrayValue(values);
    }


    @Override
    protected void onClick() {
        AbstractKube myKube = getClickedKube();

        if (myKube != null) {
            if(!myKube.equals(selectedKube)) {
                selectedKubeLineCol.x = myKube.getLine();
                selectedKubeLineCol.y = myKube.getColumn();
                selectedKube = myKube;
                setArrayValue(0, selectedKubeLineCol.x);
                setArrayValue(1, selectedKubeLineCol.y);
            } else {
                selectedKubeLineCol.x = selectedKubeLineCol.y = -1;
                selectedKube = null;
                setArrayValue(0, -1);
                setArrayValue(1, -1);
            }
        }

        broadcast();
    }

    protected AbstractKube getClickedKube() {
        PVector myLineCol = getLineColFromMousePosition();

        AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol((int)myLineCol.x, (int)myLineCol.y);
        return myKube;
    }

    protected PVector getLineColFromMousePosition() {
        Pointer myPointer = getPointer();

        PVector myLineCol = new PVector();
        AbstractKube[][] myKubes = p5.getKubeWall().getKubes();

        for (int j = 0; j < myKubes.length; j++) {
            int theLine = KubeWall.getInvertedLine(j);
            if (myPointer.y() >= (SQUARE_SIZE * j) + (PADDING * j)
                    && myPointer.y() <= (SQUARE_SIZE * (j + 1)) + (PADDING * (j + 1))) {
                myLineCol.x = theLine;
            }
        }

        for (int i = 0; i < myKubes[0].length; i++) {
            int theCol = KubeWall.getInvertedColumn(i);
            int myLeftZoneX = (SQUARE_SIZE * i) + (PADDING * i);
            int myRightZoneX = (SQUARE_SIZE * (i + 1)) + (PADDING * (i + 1));

            if (myPointer.x() >= myLeftZoneX
                    && myPointer.x() <= myRightZoneX) {
                myLineCol.y = theCol;
            }
        }

        return myLineCol;
    }

    public static PVector getPositionFromLineCol(int theLine, int theColumn) {
        PVector myPosition = new PVector();
        myPosition.x = theColumn * (SQUARE_SIZE + PADDING);
        myPosition.y = theLine * (SQUARE_SIZE + PADDING);
        return myPosition;
    }

    public int getWidth() {
        return (int)((p5.getTotalColumns() * SQUARE_SIZE) + ((p5.getTotalColumns() - 1) * PADDING));
    }

    public int getHeight() {
        return (int)((p5.getTotalLines() * SQUARE_SIZE) + ((p5.getTotalLines() - 1) * PADDING));
    }

    protected int getInvertedColumn(int theColumn) {
        return p5.getTotalColumns() - (theColumn + 1);
    }

    public PVector getSelectedKubeLineCol() {
        return selectedKubeLineCol;
    }
}
