package net.paperpixel.animation_maker.core;

import net.paperpixel.animation_maker.kube.Colors;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AnimationMaker extends PApplet {

    private int animationStepDelay = 500;
    private int totalLines = 6;
    private int totalColumns = 37;
    private int kubeSize = 20;
    private int kubeMargin = 3;
    private PVector kubeWallPosition = new PVector(20, 20);

    private boolean is_brush = true;

    private Animator animator;
    private AMControls controls;
    private Clipboard clipboard;
    private Colors current_am_color = Colors.WHITE;

    static public void main(String args[]) {
        PApplet.main(new String[]{"net.paperpixel.animation_maker.core.AnimationMaker"});
    }

    public void setup() {
        smooth();
        size(1200, 600);

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

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        animator.getActiveFrame().activateKube(new PVector(mouseEvent.getX(), mouseEvent.getY()), isBrush());
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

    public boolean isBrush() {
        return is_brush;
    }

    public Colors getCurrentAMColor() {
        return current_am_color;
    }


    /*SETTERS*/
    public void setBrush(boolean _is_brush) {
        is_brush = _is_brush;
    }

    public void setCurrentColor(Colors _current_color) {
        current_am_color = _current_color;
    }
}
