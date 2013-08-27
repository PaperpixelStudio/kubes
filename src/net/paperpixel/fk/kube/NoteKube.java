package net.paperpixel.fk.kube;

import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.communication.MidiType;
import net.paperpixel.fk.core.FKConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class NoteKube extends AbstractKube {


    //private PVector position;
    private boolean isHit = false;
    private boolean receivedToggleValue = false;
    private boolean isSwitchedOn = false;
    private boolean makeBeat = false;


    public NoteKube(int parRow, int parColumn) {
        super(parRow, parColumn);
    }

    @Override
    protected void onDraw() {
        // bug - si le kube ne se "vide" pas au userLeave, sécurité :
        if (pointsInThisKube > FKConstants.KUBE_POINTS_RANGE.getMin() && getAlpha() > 0) {
            onUserLeave();
        }
    }

    @Override
    protected void onAfterDraw() {
    }

    @Override
    protected void onBeforePointCheck(PVector theRealWorldPoint) {
    }

    @Override
    protected void onAddPoint(PVector theRealWorldPoint) {
    }

    @Override
    protected void onUserInKube() {
        float myAlpha = PApplet.map(pointsInThisKube, FKConstants.KUBE_POINTS_RANGE.getMin(), FKConstants.KUBE_POINTS_RANGE.getMax(), 0, FKConstants.KUBE_MAX_ALPHA);
//        float myAlpha = PApplet.map(getScaledPitch().y, 0, p5.getNotesPerKube(), 0, FKConstants.KUBE_MAX_ALPHA);
        setAlpha(myAlpha);
    }

    @Override
    protected void onScaledPitchChange() {
        _playNote();
    }

    @Override
    protected void onUserEnter() {
        setColor(KubeWall.getRandomColor(getColor()));
    }

    @Override
    protected void onUserLeave() {
        setAlpha(0);

        try {
            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_X, 0);
            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_Y, 0);
//            MidiCommunication.sendNote(getId(), 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reset() {
        setAlpha(0);
    }


    private void _playNote() {
        int midiPitchX = (int) PApplet.map((int) getPitch().x, 0, p5.getKubeSize(), 0, 127);
        int midiPitchY = (int) PApplet.map((int) getPitch().y, 0.0f, p5.getKubeSize(), 0, 127);
//        int midiPitchY = (int) PApplet.map((int) getScaledPitch().y, 0.0f, p5.getNotesPerKube(), 40, 100);

        /*on peut tester, une sorte de multi multi touch touch*/
//        int midiPitch = (int) PApplet.map(getPitch().x + getPitch().y, 0, p5.getKubeSize() * 2, 40, 100);

        try {
//            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_X, midiPitchX);
//            MidiCommunication.sendAudioController(getId(), MidiType.PITCH_Y, midiPitchY);
//            MidiCommunication.sendNote(getId(), midiPitchY, speed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
