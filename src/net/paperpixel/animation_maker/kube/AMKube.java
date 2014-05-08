package net.paperpixel.animation_maker.kube;

import net.paperpixel.animation_maker.core.AMProcessing;
import processing.core.PVector;

public class AMKube extends AMProcessing {
    private int id;
    private Colors color;
    private boolean active;
    private PVector position = new PVector(0, 0);

    public AMKube(int theLine, int theColumn) {
        id = AMKubeWall.createId(theLine, theColumn);
    }

    public void draw() {
        p5.pushMatrix();
        p5.translate(position.x, position.y);

        if(active) {
            p5.fill(getAMColor().getColor().getRGB());
            p5.stroke(getAMColor().getColor().getRGB());
        } else {
            p5.noFill();
            p5.stroke(150);
        }
        p5.strokeWeight(1);

        p5.rect(0, 0, p5.getKubeSize(), p5.getKubeSize());

        p5.popMatrix();
    }

    public void drawOnionSkin() {
        p5.pushMatrix();
        p5.translate(position.x, position.y);

        if(active) {
            p5.fill(getAMColor().getColor().getRGB(), 60);
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

    public Colors getAMColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public void setActive(boolean active) {
        if(!p5.getAnimator().isAnimationPlaying()) {
            this.setColor(p5.getCurrentAMColor());
            this.active = active;
        }
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setPosition(int posX, int posY) {
        this.position.x = posX;
        this.position.y = posY;
    }

    public int getColorId() {
        if(active) {
            return getAMColor().getId();
        } else {
            return 0;
        }
    }
}
