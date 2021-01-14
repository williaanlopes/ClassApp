package com.gurpster.facapemobile.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.model.Grade;
import com.gurpster.facapemobile.util.ColorUtils;
import com.gurpster.facapemobile.view.fragments.GradeFragment;
import com.gurpster.facapemobile.view.widget.BorderDrawable;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> implements Filterable {
    private List<Grades> mArrayList;
    private List<Grades> mFilteredList;
    private GradeFragment.GradeItemListener itemListener;

    public GradeAdapter(GradeFragment.GradeItemListener itemListener){
        mArrayList = new ArrayList<>();
        mFilteredList = new ArrayList<>();
        this.itemListener = itemListener;
    }

    public GradeAdapter(List<Grades> arrayList) {
        setList(arrayList);
    }

    public void replaceData(List<Grades> grades) {
        setList(grades);
        notifyDataSetChanged();
    }

    private void setList(List<Grades> grades) {
        mArrayList = checkNotNull(grades);
        mFilteredList = checkNotNull(grades);
    }

    @Override
    public GradeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_grade, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapter.ViewHolder viewHolder, int i) {

        BorderDrawable drawable = new BorderDrawable();
        drawable.setLeftBorder(2, ColorUtils.pickColor(i));
        viewHolder.cardBackground.setBackground(drawable);

        viewHolder.subjectName.setText(mFilteredList.get(i).getSubject());
//        Status status = Status. (Integer.parseInt(mFilteredList.get(i).getStatus()));
        viewHolder.status.setText(mFilteredList.get(i).getStatus());

        viewHolder.mark1.setText(mFilteredList.get(i).getGrade1());
        viewHolder.mark2.setText(mFilteredList.get(i).getGrade2());
        viewHolder.mark3.setText(mFilteredList.get(i).getGrade3());
        viewHolder.mark4.setText(mFilteredList.get(i).getGrade4());
        viewHolder.average.setText(mFilteredList.get(i).getScore());
        viewHolder.absent.setText(mFilteredList.get(i).getAbsences());

        final Grades grade = mFilteredList.get(i);

        viewHolder.cardBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onGradeClick(grade);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
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

                    List<Grades> filteredList = new ArrayList<>();

                    for (Grades grade : mArrayList) {
                        if (grade.getSubject().toLowerCase().contains(charString) || grade.getAbsences().toLowerCase().contains(charString)) {
                            filteredList.add(grade);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Grades>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout cardBackground;
        private TextView subjectName;
        private TextView status;
        private TextView mark1;
        private TextView mark2;
        private TextView mark3;
        private TextView mark4;
        private TextView average;
        private TextView absent;

        ViewHolder(View view) {
            super(view);

            cardBackground = (LinearLayout)view.findViewById(R.id.grade_card_background);
            subjectName = (TextView)view.findViewById(R.id.grade_subject_name);
            status = (TextView)view.findViewById(R.id.grade_status);
            mark1 = (TextView)view.findViewById(R.id.grade_1);
            mark2 = (TextView)view.findViewById(R.id.grade_2);
            mark3 = (TextView)view.findViewById(R.id.grade_3);
            mark4 = (TextView)view.findViewById(R.id.grade_final);
            average = (TextView)view.findViewById(R.id.grade_average);
            absent = (TextView)view.findViewById(R.id.grade_absent);
        }
    }

}