package net.paperpixel.animation_maker.core;

import processing.core.PApplet;
import processing.core.PVector;

public class AnimationMaker extends PApplet {

    private int animationStepDelay = 500;
    private int totalLines = 3;
    private int totalColumns = 6;
    private int kubeSize = 70;
    private int kubeMargin = 15;
    private PVector kubeWallPosition = new PVector(20, 20);

    private Animator animator;
    private AMControls controls;
    private Clipboard clipboard;

    static public void main(String args[]) {
        PApplet.main(new String[] { "net.paperpixel.animation_maker.core.AnimationMaker" });
    }

    public void setup() {
        smooth();
        size(900, 600);

        AMProcessing.setup(this);
        controls = new AMControls();

        animator = new Animator();

        clipboard = new Clipboard();
    }

    public void draw() {
        background(30);

        animator.draw();
    }

    public void mouseClicked() {
        animator.getActiveFrame().activateKube(new PVector(mouseX, mouseY));
    }

    public void keyPressed() {
        Keyboard.keyPressed();
    }



    public int getTotalColumns() {
        return totalColumns;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getKubeMargin() {
        return kubeMargin;
    }

    public int getKubeSize() {
        return kubeSize;
    }

    public PVector getKubeWallPosition() {
        return kubeWallPosition;
    }

    public Animator getAnimator() {
        return animator;
    }

    public AMControls getControls() {
        return controls;
    }

    public int getAnimationStepDelay() {
        return animationStepDelay;
    }

    public Clipboard getClipboard() {
        return clipboard;
    }
}
