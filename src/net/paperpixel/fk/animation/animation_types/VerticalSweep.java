package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 24/11/12
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class VerticalSweep extends AbstractAnimation {

    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i < p5.getTotalColumns(); i++){
            ArrayList<Integer> step = new ArrayList<Integer>();

            for(int j = 0; j < p5.getTotalLines(); j++){

                step.add(i + j * p5.getTotalColumns());
            }
            moves.add(step);
        }
        for(int i = p5.getTotalColumns() -1; i >=0; i-- ){
            ArrayList<Integer> step = new ArrayList<Integer>();

            for(int j =0; j < p5.getTotalLines(); j++){
               step.add(i + j * p5.getTotalColumns());
            }
            moves.add(step);
        }
        return moves;

    }

    @Override
    public String getName() {
        return "Verical sweep";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
