package net.paperpixel.fk.kube;

import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.core.FKConstants;
import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;


public abstract class AbstractKube extends FKProcessing {
    protected PVector position = new PVector();
    protected int id, pointsInThisKube, prevPointsInThisKube, line, column = 0;
    protected float alpha = 0.0f;
    protected Color color = new Color(255, 255, 255);
    private KubeType type;
    protected int speed;
    private PVector pitch = new PVector(0, 0, 0);
    private PVector previousPitch = new PVector(0, 0, 0);
    private PVector scaledPitch = new PVector(0, 0, 0);
    private PVector previousScaledPitch = new PVector(-1, -1, -1);
    private boolean idle;

    public AbstractKube(int theLine, int theColumn) {
        line = theLine;
        column = theColumn;

        id = (theLine * p5.getTotalColumns()) + theColumn;
    }

    public void check(PVector theRealWorldPoint) {
        try {
            onBeforePointCheck(theRealWorldPoint);

            int halfSize = p5.getKubeSize() / 2;

            // si le point est dans cette boite, incrémenter mon propre compteur de points.
            if (theRealWorldPoint.x > position.x - halfSize && theRealWorldPoint.x < position.x + halfSize) {
                if (theRealWorldPoint.y > position.y - halfSize && theRealWorldPoint.y < position.y + halfSize) {
                    if (theRealWorldPoint.z > position.z - halfSize && theRealWorldPoint.z < position.z + halfSize) {
                        addPoint(theRealWorldPoint);
                    }
                }
            }
        } catch(NullPointerException e) {

        }
    }


    public void draw() {
        pointsInThisKube = 0;
        setPitch(new PVector(0, 0, 0));
//        setScaledPitch();


        p5.stroke(getColor().getRGB());
        p5.fill(getColor().getRGB(), getAlpha());
        p5.strokeWeight(2);

        p5.pushMatrix();
        p5.translate(this.position.x, this.position.y, this.position.z + FKConstants.KINECT_DISTANCE);

        onDraw();

        p5.box(p5.getKubeSize());
        p5.popMatrix();

        onAfterDraw();
    }

    public void addPoint(PVector theRealWorldPoint) {
        // si le nombre de points dans la boîte est plus grand que le nombre max défini, on quitte la boucle
        if (pointsInThisKube > FKConstants.KUBE_POINTS_RANGE.getMax())
            return;

        pointsInThisKube++;
        int halfSize = p5.getKubeSize() / 2;

        int myPitchX = (int) Math.abs((position.x - halfSize) - theRealWorldPoint.x);
        int myPitchY = (int) Math.abs((position.y - halfSize) - theRealWorldPoint.y);
        int myPitchZ = (int) Math.abs((position.z - halfSize) - theRealWorldPoint.z);
        getPitch().x = myPitchX > getPitch().x ? myPitchX : getPitch().x;
        getPitch().y = myPitchY > getPitch().y ? myPitchY : getPitch().y;
        getPitch().z = myPitchY > getPitch().z ? myPitchZ : getPitch().z;

        onAddPoint(theRealWorldPoint);
    }

    public void act() {
        // le nombre de points dans la box est-il suffisant à l'instant de l'appel de la méthode?
        if (pointsInThisKube > FKConstants.KUBE_POINTS_RANGE.getMin()) {
            /*Le nombre de points de la frame précédente est-il inférieur au minimum requis ?*/
            if(prevPointsInThisKube < FKConstants.KUBE_POINTS_RANGE.getMin()) {
                previousPitch = new PVector(0, 0, 0);
                previousScaledPitch = new PVector(-1, -1, -1);
                onUserEnter();
            } else {
                setScaledPitch();

                if (getScaledPitch().y != previousScaledPitch.y) {
                    onScaledPitchChange();
                }
                previousScaledPitch = new PVector(getScaledPitch().x, getScaledPitch().y, getScaledPitch().z);

                onUserInKube();

                speed = (int) previousPitch.dist(getPitch());
                previousPitch = getPitch();
            }
        } else {
            /*Est-ce que l'utilisateur vient de sortir du kube ?*/
            if(pointsInThisKube < FKConstants.KUBE_POINTS_RANGE.getMin() && prevPointsInThisKube > FKConstants.KUBE_POINTS_RANGE.getMin()) {
                onUserLeave();
            }
        }

        prevPointsInThisKube = pointsInThisKube;
    }



    /*EVENTS*/

    protected abstract void onDraw();
    protected abstract void onAfterDraw();
    protected abstract void onBeforePointCheck(PVector theRealWorldPoint);
    protected abstract void onAddPoint(PVector theRealWorldPoint);
    protected abstract void onUserEnter();
    protected abstract void onUserLeave();
    protected abstract void onUserInKube();
    protected abstract void onScaledPitchChange();



    /*ABSTRACT*/

    protected abstract void reset();



    /*GETTER & SETTERS*/


    public int getId() {
        return id;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float theAlpha) {
        alpha = theAlpha;

        sendLight();
    }

    protected void sendLight() {
        try {
            MidiCommunication.sendVisual(getId(), getAlphaBlendedColor(), getAlpha());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setColor(int parR, int parG, int parB) {
        color = new Color(parR, parG, parB);
    }

    public void setColor(Color myColor) {
        color = myColor;
    }

    public Color getColor() {
        if(p5.isRandomKubeColors()) {
            return color;
        } else {
            return type.getColor();
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int row) {
        this.line = row;
    }

    public KubeType getType() {
        return type;
    }

    public void setType(KubeType type) {
        this.type = type;
    }


    public Color getAlphaBlendedColor() {
        float myRedSrc = (float) getColor().getRed() / 255;
        float myGreenSrc = (float) getColor().getGreen() / 255;
        float myBlueSrc = (float) getColor().getBlue() / 255;
        float myAlphaSrc = (float) ((255 - getAlpha()) / 255);

        float myRedResult = ((1 - myAlphaSrc) * myRedSrc) + (myAlphaSrc * FKConstants.MATTE_COLOR.getRed());
        float myGreenResult = ((1 - myAlphaSrc) * myGreenSrc) + (myAlphaSrc * FKConstants.MATTE_COLOR.getGreen());
        float myBlueResult = ((1 - myAlphaSrc) * myBlueSrc) + (myAlphaSrc * FKConstants.MATTE_COLOR.getBlue());

        return new Color(
                (int) PApplet.constrain(myRedResult * 255, 0, 255),
                (int) PApplet.constrain(myGreenResult * 255, 0, 255),
                (int) PApplet.constrain(myBlueResult * 255, 0, 255)
        );
    }





    public PVector getPitch() {
        return pitch;
    }

    public void setPitch(PVector pitch) {
        this.pitch = pitch;
    }

    public PVector getScaledPitch() {
        return scaledPitch;
    }

    public void setScaledPitch() {
//        myScaledPitch.x = PApplet.map(getPitch().x, 0, p5.getKubeSize(), FKConstants.PITCH_SCALE_MIN, p5.getNotesPerKube());
//        myScaledPitch.y = PApplet.map(getPitch().y, 0, p5.getKubeSize(), FKConstants.PITCH_SCALE_MIN, p5.getNotesPerKube());
//        myScaledPitch.z = PApplet.map(getPitch().z, 0, p5.getKubeSize(), FKConstants.PITCH_SCALE_MIN, p5.getNotesPerKube());

        scaledPitch.x = (int) ((getPitch().x / p5.getKubeSize()) * p5.getNotesPerKube());
        scaledPitch.y = (int) ((getPitch().y / p5.getKubeSize()) * p5.getNotesPerKube());
        scaledPitch.z = (int) ((getPitch().z / p5.getKubeSize()) * p5.getNotesPerKube());
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }
}
