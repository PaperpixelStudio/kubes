package net.paperpixel.fk.pointcloud;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 18/11/12
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class PCDisplayerFactory{

    private static HashMap<Class, PointCloudDiplayer> displayerClasses = new HashMap<Class, PointCloudDiplayer>();
    
    public static PointCloudDiplayer getDisplayer(Class theClass) {
        if(displayerClasses.get(theClass) == null) {
            try {
                displayerClasses.put(theClass, (PointCloudDiplayer) theClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return displayerClasses.get(theClass);
    }

}
