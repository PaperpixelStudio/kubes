package net.paperpixel.fk.kube;

import net.paperpixel.fk.communication.MidiType;
import net.paperpixel.fk.communication.MidiCommunication;
import net.paperpixel.fk.core.FKConstants;
import processing.core.PApplet;
import processing.core.PVector;


public class LoopKube extends AbstractKube {

    private boolean isActive = false;

    private int startMillis = 0;
    private boolean isFadeUp = true;

    public LoopKube(int theRow, int theColumn) {
        super(theRow, theColumn);
        p5.millis();
    }

    @Override
    protected void onDraw() {
        /*Si le kube est actif, fade sur l'alpha*/
        if (isActive) {
            int myFadedAlpha = _fadeAlpha();
            if(myFadedAlpha != getAlpha() && myFadedAlpha != 0)
                setAlpha(myFadedAlpha);
        } else {
            /*si le kube est idle, ne pas mettre l'alpha à 0*/
            if (getAlpha() > 0 && !isIdle()) {
                setAlpha(0);
            }
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
    protected void onUserEnter() {
        setActive(!isActive);

        if(isActive)  {
            setColor(KubeWall.getRandomColor(getColor()));
            startMillis = p5.millis();
            isFadeUp = true;

        }
    }

    @Override
    protected void onUserLeave() {
    }

    @Override
    protected void onUserInKube() {
    }

    @Override
    protected void onScaledPitchChange() {
    }

    @Override
    protected void reset() {
        if (isActive()) {
            setActive(false);
            setAlpha(0);
        }
    }


    private int _fadeAlpha() {
        int bps = (int) (p5.getBpm() / 60);
        int beatDelay = 1000 / bps;

        /*Tentative de fade (foireux)*/
        int myValue = (int) PApplet.map((p5.millis() - startMillis) * 2 , 0, beatDelay, 0, FKConstants.KUBE_MAX_ALPHA);

        if (p5.millis() - startMillis > (beatDelay / 2)) {
            startMillis = p5.millis();
            isFadeUp = !isFadeUp;
            return 0;
        } else {
            /*Plus simple: un beat allumé, un beat éteint*/
            if(isFadeUp) {
                return (int) FKConstants.KUBE_MAX_ALPHA;
            } else {
                return 40;
            }
        }
    }

    private void _sendClipNote() {
        try {
            if (isActive()) {
                MidiCommunication.sendAudioController(id, MidiType.CLIP_PLAY, 127);
//                MidiCommunication.sendClipNote(id, MidiType.CLIP_PLAY);
            } else {
                MidiCommunication.sendAudioController(id, MidiType.CLIP_STOP, 127);
//                MidiCommunication.sendClipNote(id, MidiType.CLIP_STOP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*GETTERS & SETTERS*/

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        _sendClipNote();
    }
}
