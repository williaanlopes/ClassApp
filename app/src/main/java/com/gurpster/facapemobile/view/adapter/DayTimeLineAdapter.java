package com.gurpster.facapemobile.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.model.DayTimeLineModel;
import com.gurpster.facapemobile.model.OrderStatus;
import com.gurpster.facapemobile.model.Orientation;
import com.gurpster.facapemobile.util.DateTimeUtils;
import com.gurpster.facapemobile.util.VectorDrawableUtils;
import com.gurpster.facapemobile.view.viewholder.DayTimeLineViewHolder;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 05/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DayTimeLineAdapter extends RecyclerView.Adapter<DayTimeLineViewHolder> {

    private List<DayTimeLineModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public DayTimeLineAdapter(List<DayTimeLineModel> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public DayTimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;
//
//        if(mOrientation == Orientation.HORIZONTAL) {
//            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_horizontal_line_padding : R.layout.item_timeline_horizontal, parent, false);
//        } else {
            view = mLayoutInflater.inflate(R.layout.item_timeline_line_padding, parent, false);
//        }

        return new DayTimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(DayTimeLineViewHolder holder, int position) {

        DayTimeLineModel timeLineModel = mFeedList.get(position);

//        if(timeLineModel.getStatus() == OrderStatus.INACTIVE) {
//            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
//        } else
        if(timeLineModel.getStatus() == OrderStatus.INACTIVE || timeLineModel.getStatus() == OrderStatus.ACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_check_mark_round, R.color.checkMarkColor));
        } else {
//            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.checkMarkColor));
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_circle_shape_outline, android.R.color.darker_gray));
        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        } else
            holder.mDate.setVisibility(View.GONE);

        holder.mMessage.setText(timeLineModel.getMessage());
        if(timeLineModel.getStatus() == OrderStatus.INACTIVE || timeLineModel.getStatus() == OrderStatus.ACTIVE) {
            holder.mMessage.setPaintFlags(holder.mMessage.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mMessage.setTextColor(Color.parseColor("#beffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return (mFeedList != null ? mFeedList.size() : 0);
    }

}
