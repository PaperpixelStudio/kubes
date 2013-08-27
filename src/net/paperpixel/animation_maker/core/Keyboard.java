package net.paperpixel.animation_maker.core;

import processing.core.PApplet;

import java.util.HashMap;

public class Keyboard extends AMProcessing {

    public static HashMap<Character, Integer> keyMap;

    public static void keyPressed() {
        if(p5.keyCode == PApplet.UP) {
            p5.getAnimator().setActiveFrame(Animator.PLAY_STEP_UP);
        } else if(p5.keyCode == PApplet.DOWN) {
            p5.getAnimator().setActiveFrame(Animator.PLAY_STEP_DOWN);
        } else if(p5.keyCode == PApplet.LEFT) {
            p5.getAnimator().moveFrame(Animator.FRAME_MOVE_UP);
        } else if(p5.keyCode == PApplet.RIGHT) {
            p5.getAnimator().moveFrame(Animator.FRAME_MOVE_DOWN);
        } else if(p5.key == ' ' || p5.keyCode == PApplet.ENTER || p5.keyCode == PApplet.RETURN) {
            if(!p5.getAnimator().isAnimationPlaying())
                p5.getAnimator().play();
            else
                p5.getAnimator().stop();
        } else if(p5.key == '+') {
            p5.getAnimator().addFrame(Animator.ADD_FRAME_AFTER);
        } else if(p5.key == '-') {
            p5.getAnimator().removeFrame();
        } else if(p5.key == '$') {
            p5.getAnimator().setActiveFrame(Animator.PLAY_FIRST);
        } else if(p5.key == 'µ') {
            p5.getAnimator().setActiveFrame(Animator.PLAY_LAST);
        } else if(p5.keyCode == PApplet.TAB) {
            p5.getAnimator().getActiveFrame().toggleKubes();
        } else if(p5.key == '=') {
            p5.getAnimator().duplicateFrame();
        } else {
            checkKeyMap(p5.key);
        }
    }

    private static void checkKeyMap(char key) {
        if(keyMap == null) {
            if (p5.getTotalColumns() == 6 && p5.getTotalLines() == 3) {
                createKeyMap6by3();
            }
        }

        if (keyMap.get(key) != null) {
            p5.getAnimator().getActiveFrame().activateKube(keyMap.get(key));
        }
    }

    private static void createKeyMap6by3() {
        keyMap = new HashMap<Character, Integer>();

        keyMap.put('a', 17);
        keyMap.put('z', 16);
        keyMap.put('e', 15);
        keyMap.put('r', 14);
        keyMap.put('t', 13);
        keyMap.put('y', 12);
        keyMap.put('q', 11);
        keyMap.put('s', 10);
        keyMap.put('d', 9);
        keyMap.put('f', 8);
        keyMap.put('g', 7);
        keyMap.put('h', 6);
        keyMap.put('w', 5);
        keyMap.put('x', 4);
        keyMap.put('c', 3);
        keyMap.put('v', 2);
        keyMap.put('b', 1);
        keyMap.put('n', 0);
    }
}
