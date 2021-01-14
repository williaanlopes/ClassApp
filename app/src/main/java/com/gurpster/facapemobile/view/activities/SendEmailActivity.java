package com.gurpster.facapemobile.view.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.util.ColorUtils;

public class SendEmailActivity extends AppCompatActivity {

    private FloatingActionButton btnSend;
    private EditText edtMailTo;
    private EditText edtMailBody;
    private TextView txvProfessorName;
    private boolean send = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViews();

        String subjectProfessorName = "";
        String subjectProfessorMail = "";
        Intent intent = getIntent();
        if(intent != null) {
            subjectProfessorName = intent.getStringExtra("name");
            subjectProfessorMail = intent.getStringExtra("email");
        }

        txvProfessorName.setText(subjectProfessorName);
        edtMailTo.setText(subjectProfessorMail, TextView.BufferType.EDITABLE);
        edtMailTo.setSelection(subjectProfessorMail.length());
        edtMailTo.clearFocus();
        edtMailBody.requestFocus();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        edtMailBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if(charSequence.length() != 0) {
                    send = true;
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_green_500)));
                }
                else {
                    send = false;
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_red_500)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String [] content = {edtMailTo.getText().toString().trim()};
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto"," ", null));
//                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edtMailTo.getText().toString().trim()});
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, edtMailBody.getText().toString().trim());
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));

                if(send)
                    Snackbar.make(view, "Send email...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Email body is empty!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initViews() {
        edtMailTo = (EditText) findViewById(R.id.to);
        edtMailBody = (EditText) findViewById(R.id.mail);
        txvProfessorName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
