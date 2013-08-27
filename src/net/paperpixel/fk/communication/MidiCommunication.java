package net.paperpixel.fk.communication;

import controlP5.DropdownList;
import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;
import promidi.*;

import java.awt.*;
// import themidibus.MidiBus;

public class MidiCommunication extends FKProcessing {

    public static final int DEFAULT_AUDIO_CHANNEL = 1;
    public static final int DEFAULT_LIGHTING_CHANNEL = 0;

    public static String AUDIO_COMMUNICATION = "audio_communication";
    public static String LIGHTING_COMMUNICATION = "visual_communication";

//    public static int prevMillis = 0;

    public static void learnAudio(int theKubeId, MidiType theType) throws Exception {
        sendAudio(theKubeId, theType, 127);
    }

    public static void learnVisual(int theKubeId, VisualMappingType theMappingType) throws Exception {
        sendLighting(theKubeId, theMappingType, 127);
    }

    /**
     * Sends midi controller message without speed notion (should not be used in loops)
     *
     * @param theKubeId
     * @param theType
     * @param theValue
     * @throws Exception
     */
    public static void sendAudioController(int theKubeId, MidiType theType, int theValue) throws Exception {
        sendAudio(theKubeId, theType, theValue);
    }


    public static void sendVisual(int theKubeId, Color theColor, float theAlpha) throws Exception {
        int myRedValue = (int) PApplet.map(theColor.getRed(), 0, 255, 0, 127);
        int myGreenValue = (int) PApplet.map(theColor.getGreen(), 0, 255, 0, 127);
        int myBlueValue = (int) PApplet.map(theColor.getBlue(), 0, 255, 0, 127);

        if (theAlpha == 0) {
            myRedValue = myBlueValue = myGreenValue = 0;
        }

        sendLighting(theKubeId, VisualMappingType.RED, myRedValue);
        sendLighting(theKubeId, VisualMappingType.GREEN, myGreenValue);
        sendLighting(theKubeId, VisualMappingType.BLUE, myBlueValue);
    }



//    /**
//     * Sends midi note message with speed notion, so that
//     * a message is sent less frequently when the gesture is slow
//     *
//     * @param theKubeId
//     * @param theValue
//     * @param theSpeed
//     * @throws Exception
//     */

//    public static void sendNote(int theKubeId, int theValue, int theSpeed) throws Exception {
//        if (p5.isSendAudioMidi()/* && isFrequencyOk(theSpeed) && isMinSpeedToPlay(theSpeed)*/) {
//            try {
//                int myValue = PApplet.constrain(theValue, 0, 127);
//
//                MidiOut out = MidiIO.getInstance(p5).getMidiOut(1 + getMasterSlaveKubeId(theKubeId), getDeviceName(AUDIO_COMMUNICATION));
//                out.sendNote(new Note(myValue, 100, 1000));
//                if(p5.isDebug())
//                    PApplet.println("[NOTE: Midi message sent on device " + getDeviceName(AUDIO_COMMUNICATION) + " channel " + (2 + getMasterSlaveKubeId(theKubeId)) + " note value " + myValue + "]");
//            } catch (Exception e) {
//                if(p5.isDebug())
//                    PApplet.println("could not send midi message");
//            }
//        }
//    }

//    public static void sendNote(int theKubeId, int theValue) throws Exception {
//        if(p5.isSendAudioMidi()) {
//            try {
//                int myValue = PApplet.constrain(theValue, 0, 127);
//
//                MidiOut out = MidiIO.getInstance(p5).getMidiOut(1 + getMasterSlaveKubeId(theKubeId), getDeviceName(AUDIO_COMMUNICATION));
//                out.sendNote(new Note(myValue, 100, 1000));
//                if(p5.isDebug())
//                    PApplet.println("[NOTE: Midi message sent on device " + getDeviceName(AUDIO_COMMUNICATION) + " channel " + (2 + getMasterSlaveKubeId(theKubeId)) + " note value " + myValue + "]");
//            } catch (Exception e) {
//                if(p5.isDebug())
//                    PApplet.println("could not send midi message");
//            }
//        }
//    }

    private static void sendAudio(int theKubeId, MidiType theType, int theValue) {
        if(p5.isSendAudioMidi()) {
            send(DEFAULT_AUDIO_CHANNEL, getControllerNumber(theKubeId, theType), theValue);
        }
    }

    private static void sendLighting(int theKubeId, VisualMappingType theType, int theValue) {
        if(p5.isSendLightMidi()) {
            send(DEFAULT_LIGHTING_CHANNEL, getControllerNumber(theKubeId, theType), theValue);
        }
    }

    public static void send(int theChannel, int theControllerNumber, int theValue) {
        try{
            String theDeviceName = "";
            if(theChannel == DEFAULT_AUDIO_CHANNEL) {
                theDeviceName = getDeviceName(AUDIO_COMMUNICATION);
            } else if(theChannel == DEFAULT_LIGHTING_CHANNEL) {
                theDeviceName = getDeviceName(LIGHTING_COMMUNICATION);
            }
            MidiOut out = MidiIO.getInstance(p5).getMidiOut(theChannel, theDeviceName);
            int myValue = PApplet.constrain(theValue, 0, 127);

            out.sendController(new Controller(theControllerNumber, myValue));

            if(p5.isDebug()) {
                PApplet.println("[CONTROLLER: Midi message sent on device " + theDeviceName + " controller " + theControllerNumber + " with value " + theValue + "]");
            }

            if(p5.isSlave()) {
                OSCCommunication.sendMidi(OSCCommunication.MIDI_CONTROLLER_MESSAGE, theChannel, theControllerNumber, theValue);
            }
        } catch (Exception e) {
            if(p5.isDebug())
                PApplet.println("could not send midi message");
        }
    }

    public static int getControllerNumber(int theKubeId, MidiType theType) {
        int myControllerNumber = (theKubeId * MidiType.values().length) + theType.ordinal();
        if (p5.isSlave()) {
            return myControllerNumber + (p5.getMasterKubeNumber() * MidiType.values().length);
        } else {
            return myControllerNumber;
        }
    }

    public static int getControllerNumber(int theKubeId, VisualMappingType theType) {
        int myControllerNumber = (theKubeId * MidiType.values().length) + theType.ordinal();
        if (p5.isSlave()) {
            return myControllerNumber + (p5.getMasterKubeNumber() * VisualMappingType.values().length);
        } else {
            return myControllerNumber;
        }
    }

    public static int getMasterSlaveKubeId(int theKubeId) {
        if(p5.isSlave()) {
            return theKubeId + p5.getMasterKubeNumber();
        } else {
            return theKubeId;
        }
    }

//    private static boolean isMinSpeedToPlay(int theSpeed) {
//        return theSpeed > (p5.isMouseTest() ? 5 : (int) p5.getControls().getMiscControls().getSpeedRange().getArrayValue(0));
//    }

//    private static boolean isFrequencyOk(int theSpeed) {
//        float myMinFrequence = p5.getControls().getMiscControls().getFrequencyRange().getArrayValue(0);
//        float myMaxFrequence = p5.getControls().getMiscControls().getFrequencyRange().getArrayValue(1);
//        int maxSpeed = (int) p5.getControls().getMiscControls().getSpeedRange().getArrayValue(1);
//
//        float myMidiSpeed = PApplet.constrain(PApplet.map(theSpeed, 0, maxSpeed, myMaxFrequence, myMinFrequence), myMinFrequence, myMaxFrequence);
//        int myMidiSpeedMillis = (int) (myMidiSpeed * 1000);
//
//        if (p5.millis() - prevMillis > myMidiSpeedMillis) {
//            prevMillis = p5.millis();
//            return true;
//        } else {
//            return false;
//        }
//    }


    public static String getDeviceName(String theCommunicationType) {
        int myDDLValue;

        if(theCommunicationType == LIGHTING_COMMUNICATION)
            myDDLValue = (int) p5.getControls().getLightingMappingControls().getLightingDeviceDDL().getValue();
        else
            myDDLValue = (int) p5.getControls().getAudioMappingControls().getAudioDeviceDDL().getValue();

        return MidiIO.getInstance(p5).getOutputDeviceName(myDDLValue);
    }


    public static void populateMidiDevices(DropdownList theDDL) {
        MidiIO midiIO = MidiIO.getInstance(p5);

        theDDL.clear();

        for (int i = 0; i < midiIO.numberOfOutputDevices(); i++) {
            theDDL.addItem(midiIO.getOutputDeviceName(i), i);
        }
    }
}