package net.paperpixel.animation_maker.kube;

import net.paperpixel.animation_maker.core.AMProcessing;
import processing.core.PVector;

import java.awt.*;

public class AMKube extends AMProcessing {
    private int id;
    private Color color;
    private boolean active;
    private PVector position = new PVector(0, 0);

    public AMKube(int theLine, int theColumn) {
        id = AMKubeWall.createId(theLine, theColumn);
    }

    public void draw() {
        p5.pushMatrix();
        p5.translate(position.x, position.y);

        if(active) {
            p5.fill(getColor().getRGB());
        } else {
            p5.noFill();
        }

        p5.strokeWeight(2);
        p5.stroke(getColor().getRGB());

        p5.rect(0, 0, p5.getKubeSize(), p5.getKubeSize());

        p5.popMatrix();
    }

    public void drawOnionSkin() {
        p5.pushMatrix();
        p5.translate(position.x, position.y);

        if(active) {
            p5.fill(getColor().getRGB(), 30);
        } else {
            p5.noFill();
        }

        p5.noStroke();
        p5.rect(0, 0, p5.getKubeSize(), p5.getKubeSize());

        p5.popMatrix();
    }

    public void click() {
        setActive(!active);
    }

    public boolean isActive() {
        return active;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setActive(boolean active) {
        if(!p5.getAnimator().isAnimationPlaying())
            this.active = active;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setPosition(int posX, int posY) {
        this.position.x = posX;
        this.position.y = posY;
    }
}
