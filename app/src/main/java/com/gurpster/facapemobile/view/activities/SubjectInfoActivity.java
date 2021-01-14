package com.gurpster.facapemobile.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.model.Subject;
import com.r0adkll.slidr.Slidr;

public class SubjectInfoActivity extends AppCompatActivity {

    private Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_info);


        Slidr.attach(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
//            toolbar.inflateMenu(R.menu.menu_avatar_toolbar_sample);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_left_arrow);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);
        String name = intent.getStringExtra("name");
        setTitle(name);

        subject = new Subject();

        TextView title = findViewById(R.id.title);
        title.setText(name);

        FloatingActionButton sendMail = findViewById(R.id.send_email);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SubjectInfoActivity.this, SendEmailActivity.class);
//                intent.putExtra("professor", subject.getProfessorName());
//                intent.putExtra("email", "mail@mail.com");
//                startActivity(intent);

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "mail@mail.com" });
                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
