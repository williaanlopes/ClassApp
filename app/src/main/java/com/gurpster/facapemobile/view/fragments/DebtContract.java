package com.gurpster.facapemobile.view.fragments;

import android.webkit.WebView;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Debt;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface DebtContract {

    interface View extends BaseView<Presenter> {

        void messageDownload(String message);

        void showList(List<Debt> debts);

        void showWebView();

        void showErrorView();

        void stopRefresh();
    }

    interface Presenter extends BasePresenter<View> {

        void loadData();

        String getUrl();

        void downloadFile(Debt debt);

        void takeView(DebtContract.View view);

        void dropView();
    }
}
