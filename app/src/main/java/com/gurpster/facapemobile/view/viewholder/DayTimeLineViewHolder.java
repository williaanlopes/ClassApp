package com.gurpster.facapemobile.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.gurpster.facapemobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Williaan Lopes (d3x773r) on 05/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DayTimeLineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_timeline_date)
    public TextView mDate;
    @BindView(R.id.text_timeline_title)
    public TextView mMessage;
    @BindView(R.id.time_marker)
    public TimelineView mTimelineView;

    public DayTimeLineViewHolder(View itemView, int viewType) {

        super(itemView);

        ButterKnife.bind(this, itemView);
//        mDate = itemView.findViewById(R.id.text_timeline_date);
//        mMessage = itemView.findViewById(R.id.text_timeline_title);
//        mTimelineView = itemView.findViewById(R.id.time_marker);

        mTimelineView.initLine(viewType);
    }
}