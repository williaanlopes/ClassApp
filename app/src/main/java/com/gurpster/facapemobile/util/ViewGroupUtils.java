package com.gurpster.facapemobile.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Williaan Lopes (d3x773r) on 10/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ViewGroupUtils {

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }
}