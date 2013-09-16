package net.paperpixel.fk.kube;

import net.paperpixel.fk.communication.MidiCommunication;

import javax.sound.midi.ShortMessage;

public class XaCube extends NoteKube {
    public XaCube(int parRow, int parColumn) {
        super(parRow, parColumn);
    }

    @Override
    protected void onUserEnter() {
        super.onUserEnter();
        try {
            MidiCommunication.sendNote(getId(), 127, ShortMessage.NOTE_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onUserLeave() {
        super.onUserLeave();

        try {
            MidiCommunication.sendNote(getId(), 0, ShortMessage.NOTE_OFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
