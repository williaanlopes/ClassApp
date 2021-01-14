package com.gurpster.facapemobile.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.data.source.remote.LoginService;
import com.gurpster.facapemobile.guest.GuestActivity;
import com.gurpster.facapemobile.util.Configuration;
import com.gurpster.facapemobile.util.RetrofitServiceFactory;
import com.gurpster.facapemobile.util.ViewUtils;
import com.gurpster.facapemobile.view.CheckBoxImageView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import kr.pe.burt.android.lib.faimageview.FAImageView;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class LoginActivity extends DaggerAppCompatActivity implements LoginContract.View {

    private static final String TAG = "LOGIN";

    @Inject LoginContract.Presenter presenter;

    private CoordinatorLayout coordinatorLayout;
    private LinearLayout boxUsername;
    private LinearLayout boxPassword;
    private ProgressBar progress;

    private EditText editTextUsername;
    private EditText editTextPassword;

    private Button btnSingIn;
    private Button actionGuest;

    private TextView textViewLoginHelp;

    private CheckBoxImageView viewPassowrd;

//    private RoundCornerProgressBar progressBar;

    private int progressCount = 1;
    private boolean runProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        progress = findViewById(R.id.progress);

        boxPassword = findViewById(R.id.box_username);
        boxUsername = findViewById(R.id.box_password);

        editTextUsername = findViewById(R.id.editText_username);
        editTextPassword = findViewById(R.id.editText_password);

        btnSingIn = findViewById(R.id.btn_sing_in);
        actionGuest = findViewById(R.id.action_login_guest);

        textViewLoginHelp = findViewById(R.id.textView_login_help);
        viewPassowrd = findViewById(R.id.view_password);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( TextUtils.isEmpty(editTextUsername.getText()) ) {
                    editTextUsername.setError("Campo Obrigatorio");
                } else if ( TextUtils.isEmpty(editTextPassword.getText()) ) {
                    editTextPassword.setError("Campo Obrigatorio");
                } else {
                    showProgress();
                    presenter.singIn(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });

        textViewLoginHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
//                Toast.makeText(LoginActivity.this, "Help me!", Toast.LENGTH_SHORT).show();
            }
        });


        actionGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, GuestActivity.class));
                new FinestWebView.Builder(getApplicationContext())
                        .showMenuShareVia(false)
                        .show("http://www.facape.br/");
            }
        });

        boxPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.requestFocus();
            }
        });
        boxUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextUsername.requestFocus();
            }
        });
        findViewById(R.id.ic_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextUsername.requestFocus();
            }
        });
        findViewById(R.id.ic_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.requestFocus();
            }
        });
        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.requestFocus();
            }
        });
        editTextUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextUsername.requestFocus();
            }
        });

        viewPassowrd.setOnCheckedChangeListener(new CheckBoxImageView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextPassword.setTransformationMethod(null);
                    editTextPassword.setSelection(editTextPassword.getText().length());
                } else {
                    editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    editTextPassword.setSelection(editTextPassword.getText().length());
                }
            }
        });
    }

    @Override
    public void showProgress() {
        runProgress = true;
        progress.setVisibility(View.VISIBLE);

        viewPassowrd.setChecked(false);
        viewPassowrd.setEnabled(false);

        editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
        editTextPassword.setSelection(editTextPassword.getText().length());

        editTextUsername.setError(null);
        editTextPassword.setError(null);
        editTextUsername.clearFocus();
        editTextPassword.clearFocus();
        editTextUsername.setEnabled(false);
        editTextPassword.setEnabled(false);
        editTextUsername.setFocusable(false);
        editTextPassword.setFocusable(false);

        btnSingIn.setEnabled(false);
        actionGuest.setEnabled(false);
        textViewLoginHelp.setClickable(false);

        boxUsername.setBackgroundResource(R.drawable.background_edit_text_disable);
        boxPassword.setBackgroundResource(R.drawable.background_edit_text_disable);
        editTextUsername.setBackgroundResource(R.drawable.background_edit_text_gray);
        editTextPassword.setBackgroundResource(R.drawable.background_edit_text_gray);

        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (runProgress) {
                            progress.setProgress(progressCount++);
                            handler.postDelayed(this, 100);
                        }
                    }
                });
            }
        }).start();
    }

    private void stopProgress() {
        runProgress = false;
        progressCount = 0;
        progress.setVisibility(View.INVISIBLE);

        boxUsername.setBackgroundResource(R.drawable.background_button_white_strocke);
        boxPassword.setBackgroundResource(R.drawable.background_button_white_strocke);
        editTextUsername.setBackgroundResource(R.drawable.background_edit_text_white);
        editTextPassword.setBackgroundResource(R.drawable.background_edit_text_white);

        editTextUsername.setEnabled(true);
        editTextPassword.setEnabled(true);
        editTextPassword.setFocusableInTouchMode(true);
        editTextUsername.setFocusable(true);
        editTextUsername.setFocusableInTouchMode(true);
        editTextPassword.setFocusable(true);
        viewPassowrd.setEnabled(true);

        btnSingIn.setEnabled(true);
        actionGuest.setEnabled(true);
        textViewLoginHelp.setClickable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void successful(String authToken) {

        Intent serviceIntent = new Intent("INIT_SERVICE");
        startService(serviceIntent);
        Intent intent = new Intent(getApplication(), DrawerActivity.class);
        startActivity(intent);

        stopProgress();
        finish();
    }

    @Override
    public void failure(String errorMessage) {
        stopProgress();
        ViewUtils.showSnackbar(coordinatorLayout, errorMessage, ViewUtils.SNACKBAR_ERROR, true);
    }

    public void showForgotPasswordDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = dialogView.findViewById(R.id.login);

//        dialogBuilder.setTitle("Custom dialog");
//        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if( TextUtils.isEmpty(edt.getText()) ) {
                    edt.setError("Campo Obrigatório");
                } else {
                    // TODO logica para lembrar senha
//                    presenter.forgotPassword(edt.getText().toString());
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                edt.setText(null);
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
