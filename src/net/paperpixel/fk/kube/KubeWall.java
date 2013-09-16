package net.paperpixel.fk.kube;

import net.paperpixel.fk.animation.AnimationStepper;
import net.paperpixel.fk.communication.OSCCommunication;
import net.paperpixel.fk.core.FKConstants;
import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.HashMap;

public class KubeWall extends FKProcessing {

    public static final String ANIMATION_MODE_NORMAL = "Normal";
    public static final String ANIMATION_MODE_NIGHT = "Night";
    public static final String ANIMATION_MODE_FORCE_IDLE = "Force idle";
    public static final String ANIMATION_MODE_NO_IDLE = "No idle";

    private AbstractKube[][] kubes;
    private HashMap<Integer, AbstractKube> kubesByIndexes;
    private int[][] kubeTypeIndexes;
    private boolean userPresent = false;
    private boolean myIdle = false;
    private boolean distantIdle = false;
    private float lastPresenceTime = 0;
    private String animationMode = ANIMATION_MODE_NORMAL;



    public KubeWall(int parTotalRows, int parTotalColumns) {
        kubeTypeIndexes = new int[parTotalRows][parTotalColumns];

        for (int i = 0; i < parTotalRows; i++) {
            for (int j = 0; j < parTotalColumns; j++) {
                kubeTypeIndexes[i][j] = KubeType.NOTE_KUBE.ordinal();
            }
        }
        init(parTotalRows, parTotalColumns, kubeTypeIndexes);
    }

    public KubeWall(int theTotalLines, int theTotalColumns, int[][] theKubeTypeIndexes) {
        kubeTypeIndexes = theKubeTypeIndexes;
        init(theTotalLines, theTotalColumns, theKubeTypeIndexes);
    }

    private void init(int theTotalLines, int theTotalColumns, int[][] theKubeTypeIndexes) {
        kubes = new AbstractKube[theTotalLines][theTotalColumns];
        kubesByIndexes = new HashMap<Integer, AbstractKube>(theTotalColumns*theTotalLines);

        for (int i = 0; i < theTotalLines; i++) {
            for (int j = 0; j < theTotalColumns; j++) {
                try {
                    KubeType myType = KubeType.values()[theKubeTypeIndexes[i][j]];
                    kubes[i][j] = myType.getInstance(i, j);
                    kubes[i][j].setColor(getRandomColor());
                    setKubePosition(i,j);
                    int id = kubes[i][j].getId();
                    kubesByIndexes.put(id, kubes[i][j]);
                    //myKube = myType.getInstance(i, j);
                    //myKube.setColor(KubeWall.getRandomColor());
                } catch(ArrayIndexOutOfBoundsException e) {
                    kubes[i][j] = KubeType.NOTE_KUBE.getInstance(i, j);
                }
            }
        }

        AnimationStepper.getInstance().setup(kubesByIndexes);

        if(p5.isDebug())
            PApplet.println("Kubewall init Done");
    }

    public void check(PVector parRealWorldPoint) throws NullPointerException {
        try {
            if (!animationMode.equals(ANIMATION_MODE_FORCE_IDLE)) {
                for (int i = 0; i < kubes.length; i++) {
                    for (int j = 0; j < kubes[i].length; j++) {
                        kubes[i][j].check(parRealWorldPoint);
                    }
                }
            }
        } catch (NullPointerException e) {
            if(p5.isDebug())
                PApplet.println("can't perform check");
        }
    }

    public void draw() {
        try {
            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    if(kubes[i][j].isIdle() != isIdle()) {
                        kubes[i][j].setIdle(isIdle());
                    }
                    kubes[i][j].draw();
                }
            }
            if(isIdle()) {
                AnimationStepper.getInstance().animate();
            }
        } catch(NullPointerException e) {
            if(p5.isDebug()){
                PApplet.println("can't draw kubewall NPE");
                e.printStackTrace();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (p5.isDebug()) {
                PApplet.println("can't draw kubewall AIOOBE");
                e.printStackTrace();
            }
            p5.recreateKubeWall();
        }
    }

    public void act() throws NullPointerException{
        boolean isUserInKube = false;
        for (int i = 0; i < kubes.length; i++) {
            for (int j = 0; j < kubes[i].length; j++) {
                kubes[i][j].act();
                if (kubes[i][j].pointsInThisKube > FKConstants.KUBE_POINTS_RANGE.getMin()) {
                    isUserInKube = true;
                }
            }
        }

        userPresent = isUserInKube;
    }




    public void setKubeWallPosition() {
        for (int i = 0; i < p5.getTotalLines(); i++) {
            for (int j = 0; j < p5.getTotalColumns(); j++) {
                setKubePosition(i, j);
            }
        }
    }

    private void setKubePosition(int _line, int _col) {
        if(p5.isDrawKubes()) {
            int col = ((p5.getTotalColumns() - 1) - _col);
            kubes[_line][_col].setPosition(new PVector(
                    p5.getKubeWallPosition().x + (col * p5.getKubeSize()) + (p5.getKubeMargin() * col),
                    p5.getKubeWallPosition().y + (_line * p5.getKubeSize()) + (p5.getKubeMargin() * _line),
                    p5.getKubeWallPosition().z
            ));
        }
    }


    public void resetColors() {
        for (int i = 0; i < kubes.length; i++) {
            for (int j = 0; j < kubes[i].length; j++) {
                AbstractKube myKube = (AbstractKube) kubes[i][j];
                myKube.setColor(getRandomColor(myKube.getColor()));
            }
        }
    }

    public void resetAllKubes() {
        for (int i = 0; i < kubes.length; i++) {
            for (int j = 0; j < kubes[i].length; j++) {
                kubes[i][j].reset();
            }
        }
    }

    public AbstractKube[][] getKubes(){
        return kubes;
    }

    public void setIsUserPresent(boolean isIt) {
        userPresent = isIt;
    }

    public boolean isUserPresent() {
        return userPresent;
    }

    public AbstractKube getKubeFromLineCol(int theLine, int theCol) {
        try {
            return kubes[theLine][theCol];
        } catch(ArrayIndexOutOfBoundsException e) {
            if(p5.isDebug())
                PApplet.println("Kube doesn't exist at line " + theLine + " and col " + theCol);
            return null;
        }
    }

    /**
     *
     * Retrouve un Kube en fonction d'un point
     *
     * @param thePoint le point que l'on veut analyser
     * @return AbstractKube : le kube dans lequel le point se trouve
     */

    public AbstractKube getKubeFromPoint(PVector thePoint) {
        PVector myPoint = new PVector(thePoint.x, thePoint.y, thePoint.z);
        myPoint.sub(p5.getKubeWallPosition());
        int halfSize = p5.getKubeSize() / 2;
        myPoint.add(new PVector(halfSize, halfSize, halfSize));

        /*Le point est contenu dans le kube wall en profondeur*/
        if (myPoint.z > 0 && myPoint.z < p5.getKubeSize()) {
            int col = (int) Math.floor(myPoint.x / (p5.getKubeSize() + p5.getKubeMargin()));
            int line = (int) Math.floor(myPoint.y / (p5.getKubeSize() + p5.getKubeMargin()));

            if (myPoint.x <= ((col + 1) * p5.getKubeSize()) + (col * p5.getKubeMargin())) {
                if (myPoint.y <= ((line + 1) * p5.getKubeSize()) + (col * p5.getKubeMargin())) {
                    return getKubeFromLineCol(line, getInvertedColumn(col));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public void replaceKube(int theLine, int theCol, AbstractKube theKube) {
        kubes[theLine][theCol] = theKube;
        setKubePosition(theLine, theCol);
        kubesByIndexes.put(theKube.getId(), theKube);
    }

    public HashMap<Integer, AbstractKube> getKubesByIndexesMap(){
        return kubesByIndexes;
    }


    public static int getInvertedLine(int theLine) {
        return p5.getTotalLines() - (theLine + 1);
    }

    public static int getInvertedColumn(int theColumn) {
        return p5.getTotalColumns() - (theColumn + 1);
    }

    /**
     * determines if the kubewall has been unused
     */
    public void checkIdle(){
        try {
            if(isUserPresent()){
                if(myIdle == true) {
                    setMyIdle(false);
                    setDistantIdle(false);
                    resetAllKubes();
                }
                lastPresenceTime = p5.millis();
                return;
            }
            if(p5.millis() > lastPresenceTime + (p5.getIdleDelay() * 1000) && !myIdle){
                setMyIdle(true);
                resetAllKubes();
            }
        } catch (NullPointerException e) {
            if(p5.isDebug())
                PApplet.println("Can't check myIdle");
        }
    }



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



    public boolean isIdle() {
        if (animationMode.equals(ANIMATION_MODE_FORCE_IDLE)) {
            return true;
        } else if (animationMode.equals(ANIMATION_MODE_NO_IDLE)) {
            return false;
        } else {
            if (!p5.isIdleSync()) {
                return myIdle;
            } else {
                return (myIdle && distantIdle);
            }
        }
    }

    public void setMyIdle(boolean _idle){
        myIdle = _idle;
        if (p5.isIdleSync()) {
            OSCCommunication.sendIdleStatus(_idle);
        }
    }

    public void setDistantIdle(boolean theDistantIdle) {
        distantIdle = theDistantIdle;
    }

    public String getAnimationMode() {
        return animationMode;
    }

    public void setAnimationMode(String animationMode) {
        this.animationMode = animationMode;
    }
}
