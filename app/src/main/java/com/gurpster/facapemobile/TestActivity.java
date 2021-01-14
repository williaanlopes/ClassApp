package com.gurpster.facapemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class TestActivity extends AppCompatActivity {

    private static final int FRAME_RATE = 30;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btnSingIn;
    private FrameLayout loadingView;
    private FAImageView faImageView1;
    DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lg);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        final Intent i = new Intent(this, DayTimeLineActivity.class);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(i);
//            }
//        });

        initView();
    }

    private void initView() {
        editTextUsername = findViewById(R.id.editText_username);
        editTextPassword = findViewById(R.id.editText_password);
        btnSingIn = findViewById(R.id.btn_singin);
        btnSingIn.setActivated(false);
        loadingView = findViewById(R.id.loading_view);
        faImageView1 = findViewById(R.id.faimageview1);
//        metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        animationLogin(metrics);

//        editTextUsername.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length() != 0){
//                    editTextUsername.setError("Campo Obrigratorio!");
//                } else {
//                    editTextUsername.setError(null);
//                    btnSingIn.setActivated(true);
//                }
//            }
//        });

//        editTextPassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length() != 0){
//                    editTextPassword.setError("Campo Obrigratorio!");
//                } else {
//                    editTextPassword.setError(null);
//                    btnSingIn.setActivated(true);
//                }
//            }
//        });

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingView.setVisibility(View.VISIBLE);
                faImageView1.startAnimation();
            }
        });

    }

    private void animationLogin(DisplayMetrics metrics) {

        faImageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);

        faImageView1.setInterval(FRAME_RATE);
        faImageView1.setLoop(true);
        faImageView1.addImageFrame(R.drawable.frame_000);
        faImageView1.addImageFrame(R.drawable.frame_001);
        faImageView1.addImageFrame(R.drawable.frame_002);
        faImageView1.addImageFrame(R.drawable.frame_003);
        faImageView1.addImageFrame(R.drawable.frame_004);
        faImageView1.addImageFrame(R.drawable.frame_005);
        faImageView1.addImageFrame(R.drawable.frame_006);
        faImageView1.addImageFrame(R.drawable.frame_007);
        faImageView1.addImageFrame(R.drawable.frame_008);
        faImageView1.addImageFrame(R.drawable.frame_009);
        faImageView1.addImageFrame(R.drawable.frame_010);
        faImageView1.addImageFrame(R.drawable.frame_011);
        faImageView1.addImageFrame(R.drawable.frame_012);
        faImageView1.addImageFrame(R.drawable.frame_013);
        faImageView1.addImageFrame(R.drawable.frame_014);
        faImageView1.addImageFrame(R.drawable.frame_015);
        faImageView1.addImageFrame(R.drawable.frame_016);
        faImageView1.addImageFrame(R.drawable.frame_017);
        faImageView1.addImageFrame(R.drawable.frame_018);
        faImageView1.addImageFrame(R.drawable.frame_019);
        faImageView1.addImageFrame(R.drawable.frame_020);
        faImageView1.addImageFrame(R.drawable.frame_021);
        faImageView1.addImageFrame(R.drawable.frame_022);
        faImageView1.addImageFrame(R.drawable.frame_023);
        faImageView1.addImageFrame(R.drawable.frame_024);
        faImageView1.addImageFrame(R.drawable.frame_025);
        faImageView1.addImageFrame(R.drawable.frame_026);
        faImageView1.addImageFrame(R.drawable.frame_027);
        faImageView1.addImageFrame(R.drawable.frame_028);
        faImageView1.addImageFrame(R.drawable.frame_029);
        faImageView1.addImageFrame(R.drawable.frame_030);
        faImageView1.addImageFrame(R.drawable.frame_031);
        faImageView1.addImageFrame(R.drawable.frame_032);
        faImageView1.addImageFrame(R.drawable.frame_033);
        faImageView1.addImageFrame(R.drawable.frame_034);
        faImageView1.addImageFrame(R.drawable.frame_035);
        faImageView1.addImageFrame(R.drawable.frame_036);
        faImageView1.addImageFrame(R.drawable.frame_037);
        faImageView1.addImageFrame(R.drawable.frame_038);
        faImageView1.addImageFrame(R.drawable.frame_039);
        faImageView1.addImageFrame(R.drawable.frame_040);
        faImageView1.addImageFrame(R.drawable.frame_041);
        faImageView1.addImageFrame(R.drawable.frame_042);
        faImageView1.addImageFrame(R.drawable.frame_043);
        faImageView1.addImageFrame(R.drawable.frame_044);
        faImageView1.addImageFrame(R.drawable.frame_045);
        faImageView1.addImageFrame(R.drawable.frame_046);
        faImageView1.addImageFrame(R.drawable.frame_047);
        faImageView1.addImageFrame(R.drawable.frame_048);
        faImageView1.addImageFrame(R.drawable.frame_049);
        faImageView1.addImageFrame(R.drawable.frame_050);
        faImageView1.addImageFrame(R.drawable.frame_051);
        faImageView1.addImageFrame(R.drawable.frame_052);
        faImageView1.addImageFrame(R.drawable.frame_053);
        faImageView1.addImageFrame(R.drawable.frame_054);
        faImageView1.addImageFrame(R.drawable.frame_055);
        faImageView1.addImageFrame(R.drawable.frame_056);
        faImageView1.addImageFrame(R.drawable.frame_057);
        faImageView1.addImageFrame(R.drawable.frame_058);
        faImageView1.addImageFrame(R.drawable.frame_059);
        faImageView1.addImageFrame(R.drawable.frame_060);
        faImageView1.addImageFrame(R.drawable.frame_061);
        faImageView1.addImageFrame(R.drawable.frame_062);
        faImageView1.addImageFrame(R.drawable.frame_063);
        faImageView1.addImageFrame(R.drawable.frame_064);
        faImageView1.addImageFrame(R.drawable.frame_065);
        faImageView1.addImageFrame(R.drawable.frame_066);
        faImageView1.addImageFrame(R.drawable.frame_067);
        faImageView1.addImageFrame(R.drawable.frame_068);
        faImageView1.addImageFrame(R.drawable.frame_069);
        faImageView1.addImageFrame(R.drawable.frame_070);
        faImageView1.addImageFrame(R.drawable.frame_071);
        faImageView1.addImageFrame(R.drawable.frame_072);
        faImageView1.addImageFrame(R.drawable.frame_073);
        faImageView1.addImageFrame(R.drawable.frame_074);
        faImageView1.addImageFrame(R.drawable.frame_075);
        faImageView1.addImageFrame(R.drawable.frame_076);
        faImageView1.addImageFrame(R.drawable.frame_077);
        faImageView1.addImageFrame(R.drawable.frame_078);
        faImageView1.addImageFrame(R.drawable.frame_079);
        faImageView1.addImageFrame(R.drawable.frame_080);
        faImageView1.addImageFrame(R.drawable.frame_081);
        faImageView1.addImageFrame(R.drawable.frame_082);
        faImageView1.addImageFrame(R.drawable.frame_083);
        faImageView1.addImageFrame(R.drawable.frame_084);
        faImageView1.addImageFrame(R.drawable.frame_085);
        faImageView1.addImageFrame(R.drawable.frame_086);
        faImageView1.addImageFrame(R.drawable.frame_087);
        faImageView1.addImageFrame(R.drawable.frame_088);
        faImageView1.addImageFrame(R.drawable.frame_089);
        faImageView1.addImageFrame(R.drawable.frame_090);
        faImageView1.addImageFrame(R.drawable.frame_091);
        faImageView1.addImageFrame(R.drawable.frame_092);
        faImageView1.addImageFrame(R.drawable.frame_093);
        faImageView1.addImageFrame(R.drawable.frame_094);
        faImageView1.addImageFrame(R.drawable.frame_095);
        faImageView1.addImageFrame(R.drawable.frame_096);
        faImageView1.addImageFrame(R.drawable.frame_097);
        faImageView1.addImageFrame(R.drawable.frame_098);
        faImageView1.addImageFrame(R.drawable.frame_099);
        faImageView1.addImageFrame(R.drawable.frame_100);
        faImageView1.addImageFrame(R.drawable.frame_101);
        faImageView1.addImageFrame(R.drawable.frame_102);
        faImageView1.addImageFrame(R.drawable.frame_103);
        faImageView1.addImageFrame(R.drawable.frame_104);
        faImageView1.addImageFrame(R.drawable.frame_105);
        faImageView1.addImageFrame(R.drawable.frame_106);
        faImageView1.addImageFrame(R.drawable.frame_107);
        faImageView1.addImageFrame(R.drawable.frame_108);
        faImageView1.addImageFrame(R.drawable.frame_109);
        faImageView1.addImageFrame(R.drawable.frame_110);
        faImageView1.addImageFrame(R.drawable.frame_111);
        faImageView1.addImageFrame(R.drawable.frame_112);
        faImageView1.addImageFrame(R.drawable.frame_113);
        faImageView1.addImageFrame(R.drawable.frame_114);
        faImageView1.addImageFrame(R.drawable.frame_115);
        faImageView1.addImageFrame(R.drawable.frame_116);
        faImageView1.addImageFrame(R.drawable.frame_117);
        faImageView1.addImageFrame(R.drawable.frame_118);
        faImageView1.addImageFrame(R.drawable.frame_119);
    }

}
