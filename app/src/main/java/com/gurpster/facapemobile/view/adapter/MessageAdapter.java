package com.gurpster.facapemobile.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.util.ColorUtils;
import com.gurpster.facapemobile.view.widget.BorderDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<String> mArrayList = new ArrayList<>();

    public MessageAdapter() {
    }

    public MessageAdapter(List<String> arrayList) {
        mArrayList = arrayList;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_message, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setList(List<String> list) {
        mArrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder viewHolder, int i) {

        BorderDrawable drawable = new BorderDrawable();
        drawable.setLeftBorder(2, ColorUtils.pickColor(0));
        viewHolder.cardBackground.setBackground(drawable);
        viewHolder.title.setText("Test");
        viewHolder.content.setText(mArrayList.get(i));
//        viewHolder.icon.setBackgroundResource(mArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout cardBackground;
        private TextView title;
        private TextView content;
        private ImageView icon;

        ViewHolder(View view) {
            super(view);

            cardBackground = view.findViewById(R.id.grade_card_background);
            title = view.findViewById(R.id.message_title);
            content = view.findViewById(R.id.message_content);
            icon = view.findViewById(R.id.message_icon);

        }
    }

}