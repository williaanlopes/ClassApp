package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface HistoricContract {

    interface View extends BaseView<Presenter> {

        void onLoadDataSuccess();

        void onLoadDataError();

    }

    interface Presenter extends BasePresenter<View> {

        void takeView(HistoricContract.View view);

        void dropView();
    }
}
