package com.gurpster.facapemobile.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gurpster.facapemobile.BuildConfig;
import com.gurpster.facapemobile.R;
import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        ImageView image = (ImageView) findViewById(R.id.about_image);
        Picasso.with(this)
                .load("https://scontent.fpnz1-1.fna.fbcdn.net/v/t1.0-1/c2.29.491.491/s160x160/156000_430094017079078_759681764_n.jpg?oh=eb7276611950a4f93d87140a9ae2a712&oe=5AD4E750")
                .resize(120, 120)
                .into(image);

        String versionName = "Version " + BuildConfig.VERSION_NAME;
        TextView versionCode = (TextView) findViewById(R.id.about_app_version_code);
        versionCode.setText(versionName);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
