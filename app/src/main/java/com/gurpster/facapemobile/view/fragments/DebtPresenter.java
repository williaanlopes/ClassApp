package com.gurpster.facapemobile.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Debt;
import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.di.ActivityScoped;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class DebtPresenter implements DebtContract.Presenter {

    private final PreferencesHelper preferences;
    private final ApiService apiService;
    private final Context context;

    @Nullable
    private DebtContract.View view;

    private String url = null;

    @Inject
    DebtPresenter(PreferencesHelper preferences, ApiService apiService, Context context) {
        this.preferences = preferences;
        this.apiService = apiService;
        this.context = context;
    }

    @Override
    public void loadData() {
//        String token = preferences.getAccessToken();
//        Call<String> call = apiService.getDebtUrl(token);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    List<Debt__> debts = response.body();
//                    if(view != null) view.showList(debts);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                if(view != null) view.showErrorView();
//            }
//        });
        view.showList(new ArrayList<Debt>(0));
    }

    @Override
    public void downloadFile(final Debt debt) {

        String token = preferences.getAccessToken();

        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    writeResponseBodyToDisk(response.body(), debt);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Facape Mobile error", "DebtWebView " + t.getMessage());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, Debt debt) {

        String fileName = "facape_boleto_" + debt.getType() + ".pdf";

        try {

            File futureStudioIconFile = new File(context.getExternalFilesDir(null), fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[1024];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                Log.d("WebView", "Done!");

                return true;
            } catch (IOException e) {
                Log.e("WebView", e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e("WebView", e.getMessage());
            return false;
        }
    }

    @Override
    public String getUrl() {

        String token = preferences.getAccessToken();

        Call<String> call = apiService.getDebtUrl(token);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    url = response.body();
                    if(view != null) view.showWebView();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(view != null) view.showErrorView();
            }
        });

        return this.url;
    }

    @Override
    public void takeView(DebtContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
