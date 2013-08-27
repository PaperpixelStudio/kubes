package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class DoubleRandom extends AbstractAnimation{
    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        ArrayList<ArrayList<Integer>> steps = new ArrayList<ArrayList<Integer>>();

        int totalKubes = p5.getTotalColumns() * p5.getTotalLines();

        for(int i = 0; i < 10; i++) {
            ArrayList<Integer> step = createStep((int) p5.random(0, totalKubes), (int) p5.random(0, totalKubes));
            steps.add(step);
        }

        return steps;
    }

    @Override
    public String getName() {
        return "Double Random";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
