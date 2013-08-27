package net.paperpixel.animation_maker.core;

import net.paperpixel.animation_maker.kube.AMKube;
import net.paperpixel.animation_maker.kube.AMKubeWall;
import processing.core.PVector;

public class Frame extends AMProcessing {

    private AMKubeWall kubeWall;
    private boolean toggleKube;

    public Frame() {
        kubeWall = new AMKubeWall(p5.getTotalLines(), p5.getTotalColumns());
    }

    public void draw() {
        kubeWall.draw();
    }

    public void drawOnionSkin() {
        kubeWall.drawOnionSkin();
    }
    
    public Frame clone() {
        Frame newFrame = new Frame();
        newFrame.getKubeWall().setStates(kubeWall.getStates());
        return newFrame;
    }

    public void activateKube(PVector mousePosition) {
        AMKube kube = kubeWall.getKubeFromMousePosition(mousePosition);
        if (kube != null) {
            kube.click();
        }
    }

    public void activateKube(int index) {
        AMKube kube = kubeWall.getKubeById(index);
        if (kube != null) {
            kube.click();
        }
    }

    public void toggleKubes() {
        kubeWall.setAllKubesActive(!toggleKube);
        toggleKube = !toggleKube;
    }

    public AMKubeWall getKubeWall() {
        return kubeWall;
    }

    public void setKubeWall(AMKubeWall kubeWall) {
        this.kubeWall = kubeWall;
    }

    public boolean isToggleKube() {
        return toggleKube;
    }

    public void setToggleKube(boolean toggleKube) {
        this.toggleKube = toggleKube;
    }
}
