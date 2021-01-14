package com.gurpster.facapemobile.view.activities;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;

/**
 * Created by Williaan Lopes (d3x773r) on 24/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface PlaceContract_ {

    interface View extends BaseView<Presenter> {

        void showListViewType();

        void showMapViewType();
    }

    interface Presenter extends BasePresenter<View> {

        void selectView();

        void takeView(PlaceContract_.View view);

        void dropView();
    }
}
