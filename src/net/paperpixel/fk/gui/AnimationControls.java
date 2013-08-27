package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.animation.AnimationStepper;
import net.paperpixel.fk.kube.KubeWall;

public class AnimationControls extends AbstractControlGroup {

    private Toggle idleMode;
    private Slider idleDelay;
    private DropdownList animationMode;

    public AnimationControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {

        int pos_y = FKControls.GROUP_TITLE_HEIGHT;
        int animation_mode_pos_y;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "Animation");


        animation_mode_pos_y = pos_y;

        pos_y += 20;

        idleDelay = cp5.addSlider("idleDelay")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("Idle delay")
                .setRange(5, 100)
                .setBroadcast(true)
                .addListener(new IdleDelayListener())
                .setGroup(controlGroup);

        pos_y += 40;

        cp5.addToggle("idleSync")
                .setBroadcast(false)
                .setCaptionLabel("Idle synchronisation")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new IdleSyncListener())
                .setMode(ControlP5.SWITCH);



        animationMode = FKControls.createDropdownList("animation_mode")
                .setPosition(0, animation_mode_pos_y + 10)
                .setGroup(controlGroup)
                .setLabel("Animation mode")
                .addListener(new AnimationModeListener());

        populateAnimationModeDDL();

        return controlGroup;
    }

    public void populateAnimationModeDDL() {
        animationMode.clear();

        animationMode.addItem(KubeWall.ANIMATION_MODE_NORMAL, 0);
        animationMode.addItem(KubeWall.ANIMATION_MODE_NIGHT, 1);
        animationMode.addItem(KubeWall.ANIMATION_MODE_FORCE_IDLE, 2);
        animationMode.addItem(KubeWall.ANIMATION_MODE_NO_IDLE, 3);
    }

    public DropdownList getAnimationMode() {
        return animationMode;
    }

    private class IdleModeListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            if(theEvent.getValue() == 0) {
                p5.getKubeWall().setIsUserPresent(true);
            }
        }
    }

    private class IdleDelayListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWall().setIsUserPresent(true);
        }
    }

    private class IdleSyncListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWall().setIsUserPresent(true);
        }
    }

    private class AnimationModeListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWall().setAnimationMode(animationMode.getItem((int) theEvent.getValue()).getName());
            p5.getKubeWall().setIsUserPresent(true);
            AnimationStepper.getInstance().reset();
        }
    }
}
