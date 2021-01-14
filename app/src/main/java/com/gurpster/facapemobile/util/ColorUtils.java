package com.gurpster.facapemobile.util;

import android.graphics.Color;

/**
 * Created by Williaan Lopes (d3x773r) on 26/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ColorUtils {

    public static int pickColor(int num){
        int color;
        //material color
        switch(num) {
            case 12:
                color = Color.parseColor("#EF5350");
                return color;
            case 11:
                color = Color.parseColor("#EC407A");
                return color;
            case 10:
                color = Color.parseColor("#AB47BC");
                return color;
            case 9:
                color = Color.parseColor("#7E57C2");
                return color;
            case 8:
                color = Color.parseColor("#5C6BC0");
                return color;
            case 7:
                color = Color.parseColor("#42A5F5");
                return color;
            case 6:
                color = Color.parseColor("#29B6F6");
                return color;
            case 5:
                color = Color.parseColor("#26C6DA");
                return color;
            case 4:
                color = Color.parseColor("#26A69A");
                return color;
            case 3:
                color = Color.parseColor("#66BB6A");
                return color;
            case 2:
                color = Color.parseColor("#9CCC65");
                return color;
            case 1:
                color = Color.parseColor("#D4E157");
                return color;
            case 0:
                color = Color.parseColor("#8D6E63");
                return color;
            default:
                color = Color.parseColor("#FFA726");
                return color;
        }
    }
}
