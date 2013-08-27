package net.paperpixel.fk.gui.wall_controller;

import controlP5.ControlP5;
import controlP5.ControllerProperty;
import controlP5.Textlabel;
import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
import net.paperpixel.fk.kube.KubeWall;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

public class SetupWallController extends WallController<SetupWallController> {

    protected int[][] kubeTypeIndexes;
    private Textlabel label;

    public SetupWallController(ControlP5 theControlP5, FlashKubes theP5, String theName, Textlabel theLabel) {

        super(theControlP5, theP5, theName);
        label = theLabel;

        SetupWallView view = new SetupWallView(theP5);
        setView(view);

        registerProperty("kubeTypeIndexes");
    }

    @Override
    protected void onClick() {
        AbstractKube myKube = getClickedKube();

        if(myKube != null) {
            KubeType myNewType = null;
            KubeType[] myKubeTypes = KubeType.values();

            for (int i = 0; i < myKubeTypes.length; i++) {
                KubeType thisType = myKubeTypes[i];
                if (thisType.equals(myKube.getType())) {
                    if(i < myKubeTypes.length - 1) {
                        myNewType = myKubeTypes[i + 1];
                    } else {
                        myNewType = myKubeTypes[0];
                    }
                    break;
                }
            }

            if (myNewType != null) {
                PVector myPos = getLineColFromMousePosition();
                AbstractKube myNewKube = myNewType.getInstance((int) myPos.x, (int)myPos.y);
                p5.getKubeWall().replaceKube((int) myPos.x, (int) myPos.y, myNewKube);

                label.setText("#" + MidiCommunication.getMasterSlaveKubeId(myNewKube.getId()) + ": " + myNewType.getName() + " kube");

                createKubeTypeIndexes();
            }
        }
    }

    private void listProperties() {
        List<ControllerProperty> myList = cp5.getProperties().get(this);
        for (ControllerProperty cp : myList) {
            if(p5.isDebug())
                PApplet.println("prop: " + cp.toString());
        }
    }

    public void createKubeTypeIndexes() {
        kubeTypeIndexes = new int[p5.getTotalLines()][p5.getTotalColumns()];
        
        try {
            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    kubeTypeIndexes[i][j] = p5.getKubeWall().getKubeFromLineCol(i, j).getType().ordinal();
                }
            }
        } catch(NullPointerException e) {
            if(p5.isDebug())
                PApplet.println("Kubewall not yet created");
        }
    }

    public int[][] getKubeTypeIndexes() {
        return kubeTypeIndexes;
    }

    public void setKubeTypeIndexes(int[][] theSquareColors) {
        kubeTypeIndexes = theSquareColors;
        p5.setKubeWall(new KubeWall(p5.getTotalLines(), p5.getTotalColumns(), kubeTypeIndexes));
    }
}
