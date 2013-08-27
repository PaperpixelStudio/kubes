package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class FallingLines extends AbstractAnimation {
    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {

        /*
        * JSON:
        * [{"0":false,"1":false,"2":false,"3":false,"4":false,"5":false,"6":false,"7":false,"8":false,"9":false,"10":false,"11":false,"12":false,"13":false,"14":false,"15":false,"17":true,"16":false},{"0":false,"1":false,"2":false,"3":false,"4":false,"5":false,"6":false,"7":false,"8":false,"9":false,"10":false,"11":true,"12":false,"13":false,"14":false,"15":false,"17":true,"16":true},{"0":false,"1":false,"2":false,"3":false,"4":false,"5":false,"6":false,"7":false,"8":false,"9":false,"10":true,"11":true,"12":false,"13":false,"14":false,"15":true,"17":true,"16":true},{"0":false,"1":false,"2":false,"3":false,"4":true,"5":true,"6":false,"7":false,"8":false,"9":true,"10":true,"11":true,"12":false,"13":false,"14":true,"15":true,"17":true,"16":true},{"0":false,"1":false,"2":false,"3":true,"4":true,"5":true,"6":false,"7":false,"8":true,"9":true,"10":true,"11":true,"12":false,"13":true,"14":true,"15":true,"17":true,"16":true},{"0":false,"1":false,"2":true,"3":true,"4":true,"5":true,"6":false,"7":true,"8":true,"9":true,"10":true,"11":true,"12":true,"13":true,"14":true,"15":true,"17":true,"16":true},{"0":false,"1":true,"2":true,"3":true,"4":true,"5":true,"6":true,"7":true,"8":true,"9":true,"10":true,"11":true,"12":true,"13":true,"14":true,"15":true,"17":true,"16":true},{"0":true,"1":true,"2":true,"3":true,"4":true,"5":true,"6":true,"7":true,"8":true,"9":true,"10":true,"11":true,"12":true,"13":true,"14":true,"15":true,"17":true,"16":true}]
        * */

        return parseJSON(
                "[{\"0\":false,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false,\"11\":false,\"12\":false,\"13\":false,\"14\":false,\"15\":false,\"17\":true,\"16\":false},{\"0\":false,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false,\"11\":true,\"12\":false,\"13\":false,\"14\":false,\"15\":false,\"17\":true,\"16\":true},{\"0\":false,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":true,\"11\":true,\"12\":false,\"13\":false,\"14\":false,\"15\":true,\"17\":true,\"16\":true},{\"0\":false,\"1\":false,\"2\":false,\"3\":false,\"4\":true,\"5\":true,\"6\":false,\"7\":false,\"8\":false,\"9\":true,\"10\":true,\"11\":true,\"12\":false,\"13\":false,\"14\":true,\"15\":true,\"17\":true,\"16\":true},{\"0\":false,\"1\":false,\"2\":false,\"3\":true,\"4\":true,\"5\":true,\"6\":false,\"7\":false,\"8\":true,\"9\":true,\"10\":true,\"11\":true,\"12\":false,\"13\":true,\"14\":true,\"15\":true,\"17\":true,\"16\":true},{\"0\":false,\"1\":false,\"2\":true,\"3\":true,\"4\":true,\"5\":true,\"6\":false,\"7\":true,\"8\":true,\"9\":true,\"10\":true,\"11\":true,\"12\":true,\"13\":true,\"14\":true,\"15\":true,\"17\":true,\"16\":true},{\"0\":false,\"1\":true,\"2\":true,\"3\":true,\"4\":true,\"5\":true,\"6\":true,\"7\":true,\"8\":true,\"9\":true,\"10\":true,\"11\":true,\"12\":true,\"13\":true,\"14\":true,\"15\":true,\"17\":true,\"16\":true},{\"0\":true,\"1\":true,\"2\":true,\"3\":true,\"4\":true,\"5\":true,\"6\":true,\"7\":true,\"8\":true,\"9\":true,\"10\":true,\"11\":true,\"12\":true,\"13\":true,\"14\":true,\"15\":true,\"17\":true,\"16\":true}]"
        );
    }

    @Override
    public String getName() {
        return "Falling lines";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
