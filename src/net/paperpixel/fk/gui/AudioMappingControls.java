package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.communication.MidiType;
import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.gui.wall_controller.WallController;
import net.paperpixel.fk.gui.wall_controller.WallView;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
import promidi.MidiIO;

public class AudioMappingControls extends AbstractControlGroup {

    private DropdownList midiDeviceDDL;
    private WallController wallController;
    private Textlabel kubeTextLabel;
    private Button clipPlayButton;
    private Button clipStopButton;
    private Button mapPitchXButton;
    private Button mapPitchYButton;
    private Toggle send_midi;

    private AbstractKube selectedKube = null;

    public AudioMappingControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {
        int pos_y = FKControls.GROUP_TITLE_HEIGHT;
        int temp_pos_y = FKControls.GROUP_TITLE_HEIGHT;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "Audio midi mapping");


        /*SEND MIDI*/

        send_midi = cp5.addToggle("send_audio_midi")
                .setCaptionLabel("Send audio midi")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setMode(ControlP5.SWITCH);



        /*AUDIO DEVICE*/

        pos_y += 20;

        midiDeviceDDL = FKControls.createDropdownList("midiOutputDevice2")
                .setLabel("MIDI output devices")
                .setPosition(90, pos_y)
                .setSize(150, 200)
                .setGroup(controlGroup);

        pos_y += 20;

        MidiCommunication.populateMidiDevices(midiDeviceDDL);


        /*WALL CONTROLLER*/

        wallController = new WallController(cp5, p5, "midi_wall_controller");
        wallController.setSize(150, 120);
        wallController.setPosition(0, pos_y);
        wallController.setGroup(controlGroup);
        wallController.setView(new WallView(p5));
        wallController.addListener(new MidiWallListener());


        /*TEXT LABEL*/

        kubeTextLabel = cp5.addTextlabel("midi_wall_label")
                .setGroup(controlGroup)
                .setPosition(160, pos_y)
                .hide();

        pos_y += 30;


        /*CLIP PLAY BUTTON*/

        temp_pos_y = pos_y;

        clipPlayButton = cp5.addButton("midi_wall_clip_play_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("PLAY")
                .setPosition(160, pos_y)
                .setValue(0)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y += 30;


        /*CLIP STOP BUTTON*/

        clipStopButton = cp5.addButton("midi_wall_clip_stop_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("STOP")
                .setPosition(160, pos_y)
                .setValue(1)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y = temp_pos_y;


        /*PITCH X MAP BUTTON*/

        mapPitchXButton = cp5.addButton("midi_wall_x_map_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("MAP PITCH X")
                .setPosition(160, pos_y)
                .setValue(2)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        pos_y += 30;


        /*PITCH Y MAP BUTTON*/

        mapPitchYButton = cp5.addButton("midi_wall_y_map_button")
                .setBroadcast(false)
                .setGroup(controlGroup)
                .setCaptionLabel("MAP PITCH Y")
                .setPosition(160, pos_y)
                .setValue(3)
                .addListener(new MapButtonListener())
                .setBroadcast(true)
                .hide();

        return controlGroup;
    }

    private class MidiWallListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {

            try {
                AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol((int) theEvent.getArrayValue()[0], (int) theEvent.getArrayValue()[1]);
                String myTextLabel = myKube.getType().getName() + " kube with id " + MidiCommunication.getMasterSlaveKubeId(myKube.getId()) + " selected.";

                if (myKube.getType() == KubeType.NOTE_KUBE) {
                    myTextLabel += " Channel: " + (myKube.getId() + 2);
                }

                if (!myKube.getType().equals(KubeType.INACTIVE_KUBE)) {
                    kubeTextLabel
                            .setText(myTextLabel)
                            .show();

                    selectedKube = myKube;

                    if (myKube.getType().equals(KubeType.LOOP_KUBE)) {
                        clipPlayButton.show();
                        clipStopButton.show();
                        mapPitchXButton.hide();
                        mapPitchYButton.hide();
                    } else if(myKube.getType().equals(KubeType.NOTE_KUBE)) {
                        mapPitchXButton.show();
                        mapPitchYButton.show();
                        clipPlayButton.hide();
                        clipStopButton.hide();
                    } else if (myKube.getType().equals(KubeType.EFFECT_KUBE)) {
                        mapPitchXButton.hide();
                        mapPitchYButton.show();
                        clipPlayButton.hide();
                        clipStopButton.hide();
                    } else {

                    }
                } else {
                    kubeTextLabel.hide();
                    clipPlayButton.hide();
                    clipStopButton.hide();
                    mapPitchXButton.hide();
                    mapPitchYButton.hide();
                }
            } catch(NullPointerException e) {
                kubeTextLabel.hide();
                clipPlayButton.hide();
                clipStopButton.hide();
                mapPitchXButton.hide();
                mapPitchYButton.hide();
            }
        }
    }

    private class MapButtonListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            MidiType myType = MidiType.values()[(int) theEvent.getValue()];
            try {
                MidiCommunication.learnAudio(selectedKube.getId(), myType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getDeviceName() {
        return MidiIO.getInstance(p5).getOutputDeviceName((int) midiDeviceDDL.getValue());
    }

    public DropdownList getAudioDeviceDDL() {
        return midiDeviceDDL;
    }
}
