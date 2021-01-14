package com.gurpster.facapemobile.view.activities;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;

/**
 * Created by Williaan Lopes (d3x773r) on 24/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void successful(String authToken);

//        void failure(boolean snackbar);
        void failure(String errorMessage);

//        void next();

//        void back();

        void showProgress();

    }

    interface Presenter extends BasePresenter<View> {

        void singIn(String userName, String password);

//        void storeData(String login, String password, String token);

        void forgotPassword(String login);

        void takeView(LoginContract.View view);

        void dropView();
    }
}
