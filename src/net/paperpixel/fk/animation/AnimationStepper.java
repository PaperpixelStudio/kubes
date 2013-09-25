package net.paperpixel.fk.animation;

import net.paperpixel.fk.animation.animation_types.DoubleRandom;
import net.paperpixel.fk.animation.animation_types.RandomFill;
import net.paperpixel.fk.core.FKProcessing;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeWall;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 19/11/12
 * Time: 18:46
 * To change this template use File | Settings | File Templates.
 */
public class AnimationStepper extends FKProcessing {

    private int stepMillis;
    private HashMap<Integer, AbstractKube> map;
    int stepsCpt = 0;
    ArrayList<Integer> prevStep;
    private ArrayList<ArrayList<Integer>> steps;
    private static AnimationStepper instance;


    public static AnimationStepper getInstance() {
        if(instance == null) {
            instance = new AnimationStepper();
        }
        return instance;
    }

    public void setup(HashMap<Integer, AbstractKube> _map) {
        map = _map;
        AnimationFactory.createAnimations();
    }

    private ArrayList<ArrayList<Integer>> getAnimationSteps() {

        if (p5.getKubeWall().getAnimationMode().equals(KubeWall.ANIMATION_MODE_NIGHT)
                || p5.getKubeWall().getAnimationMode().equals(KubeWall.ANIMATION_MODE_FORCE_IDLE)) {
            steps = AnimationFactory.getAnimation();
        } else {
            steps = AnimationFactory.getAnimation(DoubleRandom.class);
        }

        stepsCpt = 0;
        return steps;
    }

    public void animate() {

        if (steps == null)
            steps = getAnimationSteps();

        if (nextStep()) {
            try {
                int length = steps.size();

                if (stepsCpt > 0) {
                    toggleKube(steps.get(stepsCpt - 1), 0);
                } else {
                    p5.getKubeWall().resetColors();
                    if (prevStep != null)
                        toggleKube(prevStep, 0);
                }

                toggleKube(steps.get(stepsCpt), 255);

                if (stepsCpt == length - 1) {
                    prevStep = steps.get(length - 1);
                    steps = getAnimationSteps();
                    return;
                }

                stepsCpt++;
            } catch (IndexOutOfBoundsException e) {
                if (p5.isDebug()) {
                    PApplet.println("Couldn'x animate this step IOOBE");
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                if (p5.isDebug()) {
                    PApplet.println("Couldn'x animate this step NPE");
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void reset() {
        getAnimationSteps();
    }

    private void toggleKube(ArrayList<Integer> kubesIds, int alpha){
        if(map != null) {
            Iterator<Integer> it = kubesIds.iterator();
            while(it.hasNext()){
                try {
                    AbstractKube kube = map.get(it.next());
                    kube.setAlpha(alpha);
                } catch(NullPointerException e) {
                    if(p5.isDebug())
                        PApplet.println("the ID doesn'x exist");
                }
            }
        } else {
            if(p5.isDebug())
                PApplet.println("Set the hashmap first.");
        }
    }

    private boolean nextStep(){
        int bps = (int) (p5.getBpm() / 60);
        int beatDelay = 1000 / bps;
        if (p5.millis() - stepMillis > (beatDelay)) {
            stepMillis = p5.millis();
            return true;
        }
        return false;
    }
}
