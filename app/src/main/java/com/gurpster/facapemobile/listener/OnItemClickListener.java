package com.gurpster.facapemobile.listener;

import android.view.View;

import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.model.Subject;

/**
 * Created by Sistemas on 08/12/2017.
 */

public interface OnItemClickListener {

//    public void onItemClick(View view, int position);
//
//    public void onLongItemClick(View view, int position);

//    public void onItemClick(Subject subject, int position);

//    public void onLongItemClick(Subject subject, int position);

    public void onItemClick(Schedule schedule);

    public void onLongItemClick(Schedule schedule);
}
