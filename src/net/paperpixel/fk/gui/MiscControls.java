package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.core.FKConstants;

public class MiscControls extends AbstractControlGroup {

    private Toggle mouse_test;
    private Toggle is_debug;
    private Slider bpmSlider;
    private Toggle random_kube_colors;
    private Range frequencyRange;
    private Range speedRange;
    private Button stopButton;

    public MiscControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {

        int pos_y = FKControls.GROUP_TITLE_HEIGHT;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "Miscellaneous");


        /*BPM*/

        bpmSlider = cp5.addSlider("bpm")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("BPM")
                .setRange(FKConstants.BPM.getMin(), FKConstants.BPM.getMax())
                .setBroadcast(true)
                .setGroup(controlGroup);

        pos_y += 40;



        /*IS DEBUG*/

        is_debug = cp5.addToggle("debug")
                .setBroadcast(false)
                .setCaptionLabel("Debug mode")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .setMode(ControlP5.SWITCH);

        pos_y += 40;

        /*MOUSE TEST*/

        mouse_test = cp5.addToggle("mouse_test")
                .setBroadcast(false)
                .setCaptionLabel("Test interaction with mouse")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .setMode(ControlP5.SWITCH);

        pos_y += 40;

        /*DRAW CUBES*/

        mouse_test = cp5.addToggle("draw_kubes")
                .setBroadcast(false)
                .setCaptionLabel("Draw kubes")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setValue(true)
                .setBroadcast(true)
                .setMode(ControlP5.SWITCH);

        pos_y += 40;

        /*MOUSE TEST*/

        random_kube_colors = cp5.addToggle("randomKubeColors")
                .setCaptionLabel("Random color for kubes")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
//                .addListener(new RandomColorListener())
                .setMode(ControlP5.SWITCH);

        pos_y += 40;

        /*PITCH RANGE*/

        cp5.addSlider("notesPerKube")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("Notes per kube")
                .setRange(FKConstants.PITCH_SCALE_MIN, 100)
                .setBroadcast(true)
                .setGroup(controlGroup);

        pos_y += 40;

//        /*MIN SPEED TO PLAY NOTE*/
//
//        speedRange = cp5.addRange("minSpeedToPlay")
//                .setBroadcast(false)
//                .setPosition(0, pos_y)
//                .setRange(20, 150)
//                .setCaptionLabel("Min and max speed")
//                .setSize(100, 30)
//                .setBroadcast(true)
//                .setGroup(controlGroup);
//
//        pos_y += 40;
//
//        /*PITCH RANGE*/
//
//        frequencyRange = cp5.addRange("frequencyRange")
//                .setBroadcast(false)
//                .setPosition(0, pos_y)
//                .setRange(0.05f, 1)
//                .setCaptionLabel("Midi freq. range")
//                .setSize(100, 30)
//                .setBroadcast(true)
//                .setGroup(controlGroup);
//
//        pos_y += 40;


        /*STOP KUBES BUTTON*/

        stopButton = cp5.addButton("stop_kubes_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("Reset all kubes")
                .setPosition(0, pos_y)
                .setSize(100, 20)
                .setValue(0)
                .addListener(new StopButtonListener())
                .setBroadcast(true);

        pos_y += 40;




        return controlGroup;
    }

    public Range getFrequencyRange() {
        return frequencyRange;
    }

    public Range getSpeedRange() {
        return speedRange;
    }

    public Slider getBpmSlider() {
        return bpmSlider;
    }

    private class StopButtonListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWall().resetAllKubes();
        }
    }
}
