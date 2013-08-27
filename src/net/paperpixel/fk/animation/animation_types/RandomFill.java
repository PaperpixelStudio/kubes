package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;
import java.util.Collections;

public class RandomFill extends AbstractAnimation {
    
    ArrayList<Integer> kubeIds;

    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        int totalKubes = p5.getTotalColumns() * p5.getTotalLines();
        ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();

        kubeIds = new ArrayList<Integer>();
        for (int i = 0; i < totalKubes; i++) {
            kubeIds.add(i);
        }

        Collections.shuffle(kubeIds);
        
        for(int i = 0; i < kubeIds.size(); i++) {
            ArrayList<Integer> step = new ArrayList<Integer>();
            step.add(kubeIds.get(i));
            for(int j = 0; j < i; j++) {
                step.add(kubeIds.get(j));
            }
            steps.add(step);
        }

        reset();

        return steps;
    }

    @Override
    public String getName() {
        return "Random Fill";
    }

    @Override
    public boolean hasRandom() {
        return true;
    }
}
