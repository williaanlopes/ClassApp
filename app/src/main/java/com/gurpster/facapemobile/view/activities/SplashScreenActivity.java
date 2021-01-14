package com.gurpster.facapemobile.view.activities;

import android.os.Bundle;
import android.os.Handler;

import com.gurpster.facapemobile.R;

import dagger.android.support.DaggerAppCompatActivity;
import xyz.hanks.library.bang.SmallBangView;

public class SplashScreenActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent serviceIntent = new Intent("GRADE_SERVICE");
//        startService(serviceIntent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        final Intent intent = new Intent(this, DrawerActivity.class);
//        intent.putExtra("name", "Williaan Souza");
//        intent.putExtra("course", "Ciência da Computação");

        final SmallBangView like_text = findViewById(R.id.like_text);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                like_text.setSelected(true);
                like_text.likeAnimation();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                like_text.stopAnimation();
//                startActivity(intent);
//                Intent serviceIntent = new Intent("INIT_SERVICE");
//                startService(serviceIntent);
                finish();
            }
        }, 4000);
    }
}
