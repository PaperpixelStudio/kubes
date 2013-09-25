package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.communication.DMXCommunication;
import net.paperpixel.fk.gui.wall_controller.*;
import net.paperpixel.fk.gui.wall_controller.WallView;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
//import net.paperpixel.fk.util.Pair;
import processing.core.PApplet;
import processing.serial.*;

public class DMXMappingControls extends AbstractControlGroup {

    private Toggle send_dmx;
    private DMXWallController wallController;
    private DropdownList serialPortsList;
    private Textfield kubeChannelTF;
    private Button mapKubeButton;

    private AbstractKube selectedKube;

    public DMXMappingControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {
        int pos_x = 0;
        int pos_y = FKControls.GROUP_TITLE_HEIGHT;

        FKControls.createGroupTitle(controlGroup, "Light DMX mapping");

        /*SEND MIDI*/

        send_dmx = cp5.addToggle("send_dmx")
                .setCaptionLabel("Send dmx")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setMode(ControlP5.SWITCH);


        pos_y += 60;

        /*LIGHT DEVICE*/

        serialPortsList = FKControls.createDropdownList("serialPortsList")
                .setLabel("Serial ports")
                .setPosition(0, pos_y)
                .setSize(150, 200)
                .setGroup(controlGroup)
                .addListener(new ChangePortListener());


        populateSerialPorts();


        /*WALL CONTROLLER*/

        pos_y += 30;

        wallController = new DMXWallController(cp5, p5, "dmx_wall_controller");
        wallController.setSize(150, 120);
        wallController.setPosition(0, pos_y);
        wallController.setGroup(controlGroup);
        wallController.setView(new WallView(p5));
        wallController.addListener(new LightWallListener());
        wallController.registerProperty("dmxChannels");


        /*Textfield to save file as*/

        pos_x = 100;

        kubeChannelTF = cp5.addTextfield("kube_dmx_channel")
                .setCaptionLabel("Kube channel")
                .setPosition(pos_x, pos_y)
                .setSize(90, 20)
                .hide()
                .setGroup(controlGroup);

        pos_x += 90 + FKControls.CONTROL_PADDING;


        /*Button to save file as*/

        mapKubeButton = cp5.addButton("map_kube")
                .setCaptionLabel("Map kube")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(pos_x, pos_y)
                .setSize(100, 20)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .hide()
                .addListener(new MapKubeChannelListener());



        return controlGroup;
    }

    public void populateSerialPorts() {
        serialPortsList.clear();

        for(int i = 0; i < Serial.list().length; i++) {
            serialPortsList.addItem(Serial.list()[i], i);
        }
    }

    /*Listeners*/

    private class LightWallListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            try {
                AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol((int) theEvent.getArrayValue()[0], (int) theEvent.getArrayValue()[1]);

                if (!myKube.getType().equals(KubeType.INACTIVE_KUBE)) {
                    kubeChannelTF.show();
                    kubeChannelTF.setText("" + myKube.getDmxChannel());
                    mapKubeButton.show();
                    selectedKube = myKube;
                } else {
                }
            } catch(NullPointerException e) {
                kubeChannelTF.hide();
                mapKubeButton.hide();
                selectedKube = null;
            }
        }
    }

    class ChangePortListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            DMXCommunication.setup(serialPortsList.getItem((int)serialPortsList.getValue()).getName());
        }
    }

    private class MapKubeChannelListener implements ControlListener {
        @Override
        public void controlEvent(ControlEvent theEvent) {
            if(selectedKube != null) {
                try {
                    int myChannel = Integer.parseInt(kubeChannelTF.getText());
                    selectedKube.setDmxChannel(myChannel);
                    wallController.setDmxChannelProperty();
                    if(p5.isDebug())
                        PApplet.println("[DMX: kube " + selectedKube.getId() + " mapped on DMX channel " + myChannel + "]");
                } catch(NumberFormatException e) {
                    if(p5.isDebug())
                        PApplet.println("ERROR: please use only numbers to map DMX channels.");
                } catch(Exception e) {
                    if(p5.isDebug())
                        PApplet.println("WARNING: could not change dmx channel for kube " + selectedKube.getId() + ".");
                }
            }
        }
    }

    public DMXWallController getWallController() {
        return wallController;
    }

    public DropdownList getSerialPortsList() {
        return serialPortsList;
    }

    public String getSerialPortName() {
        return serialPortsList.getItem((int)serialPortsList.getValue()).getName();
    }
}
