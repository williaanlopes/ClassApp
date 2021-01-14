package com.gurpster.facapemobile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setViews();
    }

    protected void initViews() {

    }

    protected void setViews() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
