package net.paperpixel.fk.kube;

import net.paperpixel.fk.core.FKConstants;
import processing.core.PVector;

import java.awt.*;

public class IdleKube extends AbstractKube {
    
    private Color oldColor;
    private Color newColor;
    
    private int speed = 200;
    private int incr = 0;

    public IdleKube(int theLine, int theColumn) {
        super(theLine, theColumn);

        oldColor = KubeWall.getRandomColor();
        newColor = KubeWall.getRandomColor(oldColor);
    }

    @Override
    protected void onDraw() {
        setColor(getFadeColor());
        setAlpha(FKConstants.KUBE_MAX_ALPHA);
    }

    @Override
    protected void onAfterDraw() {
    }

    @Override
    protected void onBeforePointCheck(PVector theRealWorldPoint) {
    }

    @Override
    protected void onAddPoint(PVector theRealWorldPoint) {
    }

    @Override
    protected void onUserEnter() {
    }

    @Override
    protected void onUserLeave() {
    }

    @Override
    protected void onUserInKube() {
    }

    @Override
    protected void onScaledPitchChange() {
    }

    @Override
    protected void reset() {
        setAlpha(0);
    }


    protected Color getFadeColor() {
        int myCurrentRed, myCurrentGreen, myCurrentBlue;

        myCurrentRed = oldColor.getRed() + ((incr * (newColor.getRed() - oldColor.getRed())) / (speed - 1));
        myCurrentGreen = oldColor.getGreen() + ((incr * (newColor.getGreen() - oldColor.getGreen())) / (speed - 1));
        myCurrentBlue = oldColor.getBlue() + ((incr * (newColor.getBlue() - oldColor.getBlue())) / (speed - 1));

        if (++incr >= speed) {
            incr = 0;
            oldColor = newColor;
            newColor = KubeWall.getRandomColor(oldColor);
        }
        
        return new Color(myCurrentRed, myCurrentGreen, myCurrentBlue);
    }
}
