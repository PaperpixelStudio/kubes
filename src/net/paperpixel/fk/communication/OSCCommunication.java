package net.paperpixel.fk.communication;

import net.paperpixel.fk.core.FKConstants;
import net.paperpixel.fk.core.FKProcessing;
import netP5.NetAddress;
import oscP5.OscEventListener;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscStatus;
import processing.core.PApplet;


public class OSCCommunication extends FKProcessing{
	
	private static OscP5 osc;
	private static NetAddress address;

    public final static String MIDI_NOTE_MESSAGE = "/note";
    public final static String MIDI_CONTROLLER_MESSAGE = "/ctrl";
    public final static String IDLE_SYNC_MESSAGE = "/idle";
    public final static String BPM_CHANGE_MESSAGE = "/bpm";
	
	/*
	 * return OscP5 instance
	 */
	private static OscP5 getOsc(){
		if(osc == null){
			osc = new OscP5(p5, FKConstants.RECEIVE_PORT);
		}
		return osc;
	}
	/*
	 * return NetAddress instance
	 */
	private static NetAddress getAddress(){
		if(address == null) {
			address = new NetAddress(FKConstants.IP_ADDRESS, FKConstants.SEND_PORT);
		}
		return address;
	}


    /**
     * Recreate an instance of OscP5 and NetAddress with new parameters
     * @param theAddress the ip address of the machine you want to connect to
     * @param theListeningPort the port
     * @param theRemotePort
     */
    public static void init(String theAddress, int theListeningPort, int theRemotePort) {
        if(osc != null)
            osc.stop();

        osc = new OscP5(p5, theListeningPort);
        address = new NetAddress(theAddress, theRemotePort);
        osc.addListener(new OscListener());
    }
	
	/**
	 * La méthode se charge de construire le message OSC
	 * @param message le message OSC
     * @param channel le numero du chanelMidi
     * @param kubeId l'id du kube à passer
     * @param value la valeur
     *
     * TODO cette méthode n'envoit pour l'instant que l'équivalent d'un controller
	 */
	public static void sendMidi(String message, int channel, int kubeId, int value){

		// note channel kube number

		OscMessage msg = new OscMessage(message);

        msg.add(channel);
        msg.add(kubeId);
        msg.add(value);

        getOsc().send(msg, getAddress());
	}


    public static void sendIdleStatus(boolean theStatus) {
        OscMessage myMsg = new OscMessage(IDLE_SYNC_MESSAGE);
        myMsg.add(theStatus ? 1 : 0);

        getOsc().send(myMsg, getAddress());
        if(p5.isDebug())
            PApplet.println("OSC IDLE SYNC: sent message with status: " + theStatus);
    }



    private static class OscListener implements OscEventListener {
        /**
         * Méthode appellée à chaque fois qu'un message osc est reçu
         * @param theMessage
         */
        public void oscEvent(OscMessage theMessage) {
            if (theMessage.checkAddrPattern(IDLE_SYNC_MESSAGE)) {
                p5.getKubeWall().setDistantIdle(theMessage.get(0).intValue() > 0 ? true : false);
                if(p5.isDebug())
                    PApplet.println("OSC IDLE SYNC: received message with value: " + (theMessage.get(0).intValue() > 0 ? true : false));

            } else if (theMessage.checkAddrPattern(MIDI_CONTROLLER_MESSAGE)) {
                MidiCommunication.send(theMessage.get(0).intValue(), theMessage.get(1).intValue(), theMessage.get(2).intValue());

            } else if (theMessage.checkAddrPattern("/1/fader5")) {
                float theValue = PApplet.map(theMessage.get(0).floatValue(), 0, 1, 60, 180);
                p5.getControls().getMiscControls().getBpmSlider().setValue(theValue);
                if (p5.isDebug())
                    PApplet.println("OSC: received BPM CHANGE message with value: " + theMessage.get(0).floatValue());
            }
        }

        public void oscStatus(OscStatus theStatus) {
        }
    }
}
