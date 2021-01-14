package com.gurpster.facapemobile.util;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.gurpster.facapemobile.R;

/**
 * Created by Williaan Lopes (d3x773r) on 27/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ViewUtils {

    public static final int SNACKBAR_SUCCESS = 0;
    public static final int SNACKBAR_ERROR = 1;
    public static final int SNACKBAR_WARNING = 2;
    public static final int SNACKBAR_INFO = 3;

    public static void showSnackbar(View view, String message, int type, boolean isLong) {
        Snackbar snack = Snackbar.make(view, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
        View snackView = snack.getView();
        switch (type) {
            case 0:
                snackView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_green_500));
                break;
            case 1:
                snackView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_red_500));
                break;
            case 2:
                snackView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_yellow_700));
                break;
            case 3:
                snackView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_blue_500));
                break;
        }

        TextView tv = snackView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();
    }
}
