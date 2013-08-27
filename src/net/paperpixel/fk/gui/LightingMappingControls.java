package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.communication.VisualMappingType;
import net.paperpixel.fk.gui.wall_controller.WallController;
import net.paperpixel.fk.gui.wall_controller.WallView;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
import promidi.MidiIO;

public class LightingMappingControls extends AbstractControlGroup {

    private WallController wallController;
    private Textlabel kubeTextLabel;
    private Button redMapButton;
    private Button blueMapButton;
    private Button greenMapButton;
    private DropdownList lightDeviceDDL;
    private Toggle send_midi;

    private AbstractKube selectedKube = null;

    public LightingMappingControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {
        int pos_y = FKControls.GROUP_TITLE_HEIGHT;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "Light midi mapping");



        /*SEND MIDI*/

        send_midi = cp5.addToggle("send_light_midi")
                .setCaptionLabel("Send light midi")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setMode(ControlP5.SWITCH);



        /*LIGHT DEVICE*/

        pos_y += 20;

        lightDeviceDDL = FKControls.createDropdownList("lightOutputDevice")
                .setLabel("MIDI output devices")
                .setPosition(90, pos_y)
                .setSize(150, 200)
                .setGroup(controlGroup);

        pos_y += 20;

        MidiCommunication.populateMidiDevices(lightDeviceDDL);


        /*WALL CONTROLLER*/

        wallController = new WallController(cp5, p5, "light_wall_controller");
        wallController.setSize(150, 120);
        wallController.setPosition(0, pos_y);
        wallController.setGroup(controlGroup);
        wallController.setView(new WallView(p5));
        wallController.addListener(new LightWallListener());


        /*TEXT LABEL*/

        kubeTextLabel = cp5.addTextlabel("light_wall_label")
                .setGroup(controlGroup)
                .setPosition(160, pos_y)
                .hide();

        pos_y += 30;


        /*RED MAP BUTTON*/

        redMapButton = cp5.addButton("light_wall_red_map_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("MAP RED")
                .setPosition(160, pos_y)
                .setValue(0)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y += 30;


        /*BLUE MAP BUTTON*/

        blueMapButton = cp5.addButton("light_wall_blue_map_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("MAP GREEN")
                .setPosition(160, pos_y)
                .setValue(1)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y += 30;


        /*RED GREEN BUTTON*/

        greenMapButton = cp5.addButton("light_wall_green_map_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("MAP BLUE")
                .setPosition(160, pos_y)
                .setValue(2)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y += 30;

        return controlGroup;
    }

    private class LightWallListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            try {
                AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol((int) theEvent.getArrayValue()[0], (int) theEvent.getArrayValue()[1]);
                String myTextLabel = myKube.getType().getName() + " kube with id " + MidiCommunication.getMasterSlaveKubeId(myKube.getId()) + " selected.";

                kubeTextLabel.show();
                redMapButton.show();
                blueMapButton.show();
                greenMapButton.show();

                if (!myKube.getType().equals(KubeType.INACTIVE_KUBE)) {
                    kubeTextLabel
                            .setText(myTextLabel)
                            .show();

                    selectedKube = myKube;
                } else {
                    kubeTextLabel.hide();
                    redMapButton.hide();
                    blueMapButton.hide();
                    greenMapButton.hide();

                }
            } catch(NullPointerException e) {
                kubeTextLabel.hide();
                redMapButton.hide();
                blueMapButton.hide();
                greenMapButton.hide();
            }
        }
    }

    private class MapButtonListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            VisualMappingType myType = VisualMappingType.values()[(int) theEvent.getValue()];
            try {
                MidiCommunication.learnVisual(selectedKube.getId(), myType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DropdownList getLightingDeviceDDL() {
        return lightDeviceDDL;
    }
}
