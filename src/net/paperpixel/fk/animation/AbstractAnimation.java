package net.paperpixel.fk.animation;

import net.paperpixel.fk.core.FKProcessing;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class AbstractAnimation extends FKProcessing {

    private ArrayList<ArrayList<Integer>> steps;
    private ArrayList<Integer> addedStep = new ArrayList<Integer>();

    protected abstract ArrayList<ArrayList<Integer>> getAnimationSteps();
    public abstract String getName();
    public abstract boolean hasRandom();


    public ArrayList<ArrayList<Integer>> getAnimation() {
        if (steps == null || hasRandom()) {
            steps = getAnimationSteps();
            steps = removeInactiveSteps(steps);
        }
        return steps;
    }

    /**
     * Checks on each step if the animation is played on inactive kubes only.
     * If so, removes the step
     * @param steps every steps of the animation
     * @return every steps of the animation without the inactive ones
     */
    private ArrayList<ArrayList<Integer>> removeInactiveSteps(ArrayList<ArrayList<Integer>> steps) {
        HashMap<Integer, AbstractKube> map = p5.getKubeWall().getKubesByIndexesMap();
        for (int i = steps.size()-1; i >= 0; i--) {
            ArrayList<Integer> step = steps.get(i);
            boolean stepIsInactive = true;
            if (step.size() == 0) {
                stepIsInactive = false;
            }
            for (int j = 0; j < step.size(); j++) {
                try {
                    AbstractKube kube = map.get(step.get(j));
                    if(kube.getType() != KubeType.INACTIVE_KUBE) {
                        stepIsInactive = false;
                    }
                } catch(NullPointerException e) {
                    if(p5.isDebug())
                        PApplet.println("kube with ID " + j + " doesn'x exist");
                }
            }
            if(stepIsInactive) {
                steps.remove(i);
            }
        }
        return steps;
    }

    protected ArrayList<Integer> createStep(int ... args) {
        ArrayList<Integer> step = new ArrayList<Integer>();
        for(int i=0; i < args.length; i++) {
            step.add(args[i]);
        }
        return step;
    }

    protected ArrayList<Integer> createAndAddStep(int... args) {
        ArrayList<Integer> myStep = createStep(args);
        ArrayList<Integer> myAddedStep = new ArrayList<Integer>();
        myAddedStep.addAll(myStep);
        myAddedStep.addAll(addedStep);
        addedStep.clear();
        addedStep.addAll(myAddedStep);
        return myAddedStep;
    }

    protected void clear() {
        addedStep.clear();
    }

    protected ArrayList<ArrayList<Integer>> addSteps(ArrayList<Integer>... args) {
        ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < args.length; i++) {
            steps.add(args[i]);
        }
        return steps;
    }
    
    protected ArrayList<Integer> getAllKubeIds() {
        ArrayList<Integer> myKubeIds = new ArrayList<Integer>();

        int totalKubes = p5.getTotalColumns() * p5.getTotalLines();

        for (int i = 0; i < totalKubes; i++) {
            myKubeIds.add(i);
        }

        return myKubeIds;
    }

    public void reset() {
        steps = null;
    }
    
    
    protected ArrayList<ArrayList<Integer>> parseJSON(String theJSON) {
        try {
            JSONArray json = new JSONArray(theJSON);
            ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();
            
            for(int i = 0; i < json.length(); i++) {
                JSONObject jsonStep = json.getJSONObject(i);
                ArrayList<Integer> step = new ArrayList<Integer>();
                
                for (int j = 0; j < jsonStep.length(); j++) {
                    if(jsonStep.getBoolean(String.valueOf(j))) {
                        step.add(j);
                    }
                }
                steps.add(step);
            }

            return steps;
        } catch (JSONException e) {
            if(p5.isDebug())
                PApplet.println("JSON not parsed. Check your JSON string.");
        }
        return null;
    }
}
