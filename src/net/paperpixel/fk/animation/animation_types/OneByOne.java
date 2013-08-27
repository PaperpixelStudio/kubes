package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;
import processing.core.PApplet;

import java.util.ArrayList;

public class OneByOne extends AbstractAnimation {

    public ArrayList<ArrayList<Integer>> getAnimationSteps() {
        ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();
        int totalKubes = p5.getTotalColumns() * p5.getTotalLines();

        for(int i = 0; i < totalKubes; i++){
            steps.add(createStep(i));
        }
        for(int i = totalKubes -1; i>=0; i--){
           steps.add(createStep(i));
        }

        return steps;
    }

    @Override
    public String getName() {
        return "One by one";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
