package com.gurpster.facapemobile.view.fragments;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface MessageContract {

    interface View extends BaseView<Presenter> {

        void showMessages(List<String> messages);

        void showErrorView();
    }

    interface Presenter extends BasePresenter<View> {

        void takeView(MessageContract.View view);

        void dropView();
    }
}
