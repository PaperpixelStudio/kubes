package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.communication.OSCCommunication;
import processing.core.PApplet;

public class NetworkingControls extends AbstractControlGroup {

    private Toggle slaveToggle;
    private Slider kubeNumberSlider;
    private Textfield listeningPort;
    private Textfield remotePort;
    private Textfield ipAddress;
    private Button masterUpdate;
    private Button updateButton;
    
    private int updateButtonYPos;

    public NetworkingControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {

        int pos_y = FKControls.GROUP_TITLE_HEIGHT;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "Networking");



        ipAddress = cp5.addTextfield("ipAddress")
                .setBroadcast(false)
                .setText("192.168.0.1")
                .setBroadcast(true)
                .setCaptionLabel("IP Address")
                .setPosition(0, pos_y)
                .setGroup(controlGroup);

        pos_y += 40;

        remotePort = cp5.addTextfield("remotePort")
                .setBroadcast(false)
                .setText("" + 9000)
                .setBroadcast(true)
                .setCaptionLabel("Remote port")
                .setPosition(0, pos_y)
                .setGroup(controlGroup);

        pos_y += 40;

        listeningPort = cp5.addTextfield("listeningPort")
                .setBroadcast(false)
                .setText("" + 9000)
                .setBroadcast(true)
                .setCaptionLabel("Listening port")
                .setPosition(0, pos_y)
                .setGroup(controlGroup);

        pos_y += 40;


        /*MASTER/SLAVE*/

        slaveToggle = cp5.addToggle("slave")
                .setBroadcast(false)
                .setCaptionLabel("Am I a slave ?")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new SlaveToggleListener())
                .setMode(ControlP5.SWITCH);

        pos_y += 40;


        /*SLAVE*/

        kubeNumberSlider = cp5.addSlider("masterKubeNumber")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("Master kubes")
                .setRange(1, 20)
                .setNumberOfTickMarks(20)
                .setBroadcast(true)
                .setGroup(controlGroup);

        pos_y += 50;


        updateButtonYPos = pos_y;

        updateButton = cp5.addButton("update_button")
                .setCaptionLabel("Update")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(0, pos_y)
                .setSize(100, 20)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new NetworkListener());


        return controlGroup;
    }

    public Button getUpdateButton() {
        return updateButton;
    }



    private class SlaveToggleListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            /*if the program is slave*/
            if(theEvent.getValue() > 0) {
                kubeNumberSlider.show();
                updateButton.setPosition(0, updateButtonYPos);
            }
            /*if the programm is master*/
            else {
                kubeNumberSlider.hide();
                updateButton.setPosition(0, updateButtonYPos - 50);
            }
        }
    }

    private class NetworkListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            try {
                int myListeningPort = Integer.parseInt(listeningPort.getText());
                int myRemotePort = Integer.parseInt(remotePort.getText());
                OSCCommunication.init(ipAddress.getText(), myListeningPort, myRemotePort);
            } catch(NumberFormatException e) {
                if(p5.isDebug())
                    PApplet.println("the sender port must be an integer number");
            }
        }
    }
}
