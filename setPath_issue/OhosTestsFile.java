package com.applibgroup.canvasdrawtest;

import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.agp.render.Path;
import ohos.agp.render.Region;
import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleOhosTest {
    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("com.applibgroup.canvasdrawtest", actualBundleName);
    }

    /**
     * Test if a Rectangle Path contains the rectangle it was generated from
     */
    @Test
    public void testContains() {
        RectFloat rectFloat = new RectFloat(0,0,100,100);

        // Created a Rectangle Path using the same rect
        Path myPath = new Path();
        myPath.addRect(rectFloat, Path.Direction.CLOCK_WISE);

        assertTrue(myPath.conservativelyContainsRect(rectFloat)); // Expected true, observed true
    }
    /**
     * Test if a Rectangle Path contains a larger rectangle than the one it was generated from
     */
    @Test
    public void testContains2() {
        RectFloat rectFloat = new RectFloat(0,0,100,100);

        // Created a Rounded Rectangle using the same rect
        Path myPath = new Path();
        myPath.addRect(rectFloat, Path.Direction.CLOCK_WISE);

        rectFloat.scale(1.5f);

        assertFalse(myPath.conservativelyContainsRect(rectFloat)); // Expected false, observed false
    }

    /**
     * Test if a Region made out of a rectangle contains the rectangle
     */
    @Test
    public void testContains3(){
        Rect rect = new Rect(0,0,100,100);
        Region myRegion = new Region(rect);

        assertTrue(myRegion.quickContains(rect)); // Expected true, observed true
    }
    @Test
    public void testSetPath1(){
        Rect rect = new Rect(0,0,100,100);
        Region myRegion = new Region(rect);
        Path myPath = new Path();
        myPath.addRect(rect.left, rect.top, rect.right,rect.bottom, Path.Direction.CLOCK_WISE);

        Region intersectRegion = new Region();
        boolean res = intersectRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed false
    }
    @Test
    public void testSetPath2() {

        RectFloat rectFloat = new RectFloat(0,0,100,100);
        // Created a Region using the rectFloat boundary points
        Rect myRect = new Rect((int)rectFloat.left,(int)rectFloat.top,(int)rectFloat.right,(int)rectFloat.bottom);
        Region myRegion = new Region(myRect);

        // Created a Rounded Rectangle using the same rect
        Path myPath = new Path();
        myPath.addRoundRect(rectFloat, new float[] {5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f}, Path.Direction.CLOCK_WISE);

        Region intersectionRegion = new Region();
        boolean res = intersectionRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed to be false
    }
    @Test
    public void testSetPath3() {
        RectFloat rectFloat = new RectFloat(0,0,100,100);
        // Created a Region using the rectFloat boundary points
        Rect myRect = new Rect((int)rectFloat.left,(int)rectFloat.top,(int)rectFloat.right,(int)rectFloat.bottom);
        Region myRegion = new Region(myRect);

        // Created a Rounded Rectangle using the same rect
        Path myPath = new Path();
        myPath.addRect(rectFloat, Path.Direction.CLOCK_WISE);

        Region intersectionRegion = new Region();
        boolean res = intersectionRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed to be false
    }
}