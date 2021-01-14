package com.gurpster.facapemobile.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Place;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements Filterable {

    private List<Place> mArrayList;
    private List<Place> mFilteredList;
//    private Context context;
    private PlaceActions actions;

    public PlaceAdapter(PlaceActions actions) {
//        this.context = context;
        this.actions = actions;
        mArrayList = new ArrayList<>();
        mFilteredList = new ArrayList<>();
    }

    public PlaceAdapter(List<Place> arrayList) {
        mArrayList = arrayList;
    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_place, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setList(List<Place> list) {
        mArrayList = checkNotNull(list);
        mFilteredList = checkNotNull(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder viewHolder, int i) {

        Place place = mFilteredList.get(i);

        viewHolder.title.setText(place.getName());
        viewHolder.responsible.setText(place.getResponsible());
        if(!TextUtils.isEmpty(place.getPhone())) {
            viewHolder.viewPhone.setVisibility(View.VISIBLE);
            viewHolder.phone.setText(place.getPhone());
        }
        if(!TextUtils.isEmpty(place.getEmail())) {
            viewHolder.viewMail.setVisibility(View.VISIBLE);
            viewHolder.email.setText(place.getEmail());
        }

//        Glide.with(context)
//                .load(R.mipmap.location_img)
//                .into(viewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {

                    List<Place> filteredList = new ArrayList<>();

                    for (Place place : mArrayList) {
                        if (place.getName().toLowerCase().contains(charString)
                                || place.getPhone().toLowerCase().contains(charString)
                                || place.getResponsible().toLowerCase().contains(charString)) {
                            filteredList.add(place);
                        }
                    }

                    mFilteredList = filteredList.isEmpty() ? mArrayList : filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Place>) filterResults.values;
                notifyDataSetChanged();
//                if(mFilteredList.size() == mArrayList.size()) {
//                    actions.actionSearch(false);
//                }
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

//        private LinearLayout cardBackground;
        private TextView title;
        private TextView responsible;
        private TextView phone;
        private TextView email;
        private ImageView icon;
        private LinearLayout actionCall;
        private LinearLayout actionMail;

        private LinearLayout viewMail;
        private LinearLayout viewPhone;

        ViewHolder(View view) {
            super(view);

//            cardBackground = view.findViewById(R.id.grade_card_background);
            title = view.findViewById(R.id.place_title);
            responsible = view.findViewById(R.id.place_responsible);
            phone = view.findViewById(R.id.place_phone);
            email = view.findViewById(R.id.place_email);
            icon = view.findViewById(R.id.place_icon);

            actionCall = view.findViewById(R.id.call_phone);
            actionMail = view.findViewById(R.id.send_email);

            viewMail = view.findViewById(R.id.view_mail);
            viewPhone = view.findViewById(R.id.view_phone);

            actionCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actions.actionCall(mFilteredList.get(getLayoutPosition()).getPhone());
                }
            });
            actionMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actions.actionMail(mFilteredList.get(getLayoutPosition()).getEmail());
                }
            });
        }
    }

    public interface PlaceActions {
        void actionSearch(boolean result);
        void actionCall(String phone);
        void actionMail(String mail);
    }

}