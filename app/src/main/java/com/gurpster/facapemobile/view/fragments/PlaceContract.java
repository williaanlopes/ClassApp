package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Place;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface PlaceContract {

    interface View extends BaseView<Presenter> {

        void show(List<Place> places);

        void showErrorView();
    }

    interface Presenter extends BasePresenter<View> {

        void loadData();

        void takeView(PlaceContract.View view);

        void dropView();
    }
}
