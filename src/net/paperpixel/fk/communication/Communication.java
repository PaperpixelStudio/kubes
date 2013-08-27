package net.paperpixel.fk.communication;

import net.paperpixel.fk.core.FlashKubes;
import net.paperpixel.fk.kube.AbstractKube;


/*
 * La classe se charge de la net.paperpixel.fk.communication MIDI/SERIAL/OSC
 */
public class Communication {

	/**
	 * LA méthode qui se charge de l'envoi
	 * @param String method: la méthode d'envoi OSC/MIDI/DMX 
	 * @param AbstractKube Kube: le net.paperpixel.fk.kube duquel reprendre ls données
	 */
	public static void send(FlashKubes applet, int method, AbstractKube kube){
		switch(method){
		case 1:{ // OSC
			//OSCCommunication.send(applet, kube);
			break;
		}
		case 2:{ // MIDI
			break;
		}
		case 3:{ // DMX
			break;
		}
		}
	}
}
