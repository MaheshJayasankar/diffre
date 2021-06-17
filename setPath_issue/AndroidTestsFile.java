package com.example.diffrerebuild;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.diffrerebuild", appContext.getPackageName());
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
        myPath.addRect(rect.left, rect.top, rect.right,rect.bottom, Path.Direction.CW);

        Region intersectRegion = new Region();
        boolean res = intersectRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed false
    }
    @Test
    public void testSetPath2() {

        RectF rectFloat = new RectF(0,0,100,100);
        // Created a Region using the rectFloat boundary points
        Rect myRect = new Rect((int)rectFloat.left,(int)rectFloat.top,(int)rectFloat.right,(int)rectFloat.bottom);
        Region myRegion = new Region(myRect);

        // Created a Rounded Rectangle using the same rect
        Path myPath = new Path();
        myPath.addRoundRect(rectFloat, new float[] {5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f}, Path.Direction.CW);

        Region intersectionRegion = new Region();
        boolean res = intersectionRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed to be false
    }
    @Test
    public void testSetPath3() {
        RectF rectFloat = new RectF(0,0,100,100);
        // Created a Region using the rectFloat boundary points
        Rect myRect = new Rect((int)rectFloat.left,(int)rectFloat.top,(int)rectFloat.right,(int)rectFloat.bottom);
        Region myRegion = new Region(myRect);

        // Created a Rounded Rectangle using the same rect
        Path myPath = new Path();
        myPath.addRect(rectFloat, Path.Direction.CW);

        Region intersectionRegion = new Region();
        boolean res = intersectionRegion.setPath(myPath, myRegion);

        assertTrue(res); // Expected true, observed to be false
    }
}