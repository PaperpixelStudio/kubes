package net.paperpixel.fk.kube;

import processing.core.PVector;

public class InactiveKube extends AbstractKube {
    public InactiveKube(int theRow, int theColumn) {
        super(theRow, theColumn);
    }

    @Override
    protected void onDraw() {
        p5.noFill();
        p5.noStroke();
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
    }
}
