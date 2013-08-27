package net.paperpixel.fk.kube;

import net.paperpixel.fk.communication.MidiType;
import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.core.FKConstants;
import processing.core.PApplet;
import processing.core.PVector;


public class EffectKube extends AbstractKube {
    
    int effectValue = 0;

    public EffectKube(int theRow, int theColumn) {
        super(theRow, theColumn);

        try {
            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_Y, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw() {
        /*Le kube est toujours vide*/
        if(!p5.getKubeWall().isIdle())
            p5.noFill();
    }

    @Override
    protected void onAfterDraw() {
        /*On crée un cube dans le kube qui affiche le niveau de l'effet*/
        p5.noStroke();
        p5.fill(getColor().getRGB(), FKConstants.KUBE_MAX_ALPHA);
        p5.pushMatrix();
        p5.translate(this.position.x, this.position.y - (p5.getKubeSize() / 2) + (effectValue / 2), this.position.z + FKConstants.KINECT_DISTANCE);
        p5.box(p5.getKubeSize(), effectValue, p5.getKubeSize());
        p5.popMatrix();
    }

    @Override
    protected void onBeforePointCheck(PVector theRealWorldPoint) {
    }

    @Override
    protected void onAddPoint(PVector theRealWorldPoint) {
    }

    @Override
    protected void onUserEnter() {
    }

    @Override
    protected void onUserLeave() {
    }

    @Override
    protected void onUserInKube() {
        effectValue = (int) getPitch().y;

        /*On gère l'alpha du kube pour envoyer aux LEDs*/
        if (effectValue > 40) {
            setAlpha((int) PApplet.map(effectValue, 0, p5.getKubeSize(), 0, 255));
        } else {
            setAlpha(0);
        }

        sendAudio((int) PApplet.map(effectValue, 0, p5.getKubeSize(), 0, 127));
    }

    @Override
    protected void onScaledPitchChange() {
    }

    protected void sendAudio(int myMidiValue) {
        try {
            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_Y, myMidiValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void reset() {
        setAlpha(0);
        effectValue = 0;
        sendAudio(0);
    }
}
