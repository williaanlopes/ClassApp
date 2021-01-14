package com.gurpster.facapemobile.view.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.listener.OnItemClickListener;
import com.gurpster.facapemobile.stickyheaders.SectioningAdapter;
import com.gurpster.facapemobile.util.CalendarUtils;
import com.gurpster.facapemobile.util.ColorUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 26/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleAdapter extends SectioningAdapter implements Filterable {

    private OnItemClickListener onItemClickListener;

    private List<Schedule> mSchedules;
    private List<Schedule> mFilteredList;
    private List<Section> sections = new ArrayList<>();

    private class Section {
        String alpha;
        List<Schedule> schedules = new ArrayList<>();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        private TextView subjectName;
        private TextView subjectProfessorName;
        private TextView subjectTime;
        private View topLine;
        private View bottomLine;
        private View background;
        private View marker;
//        TextView subjectLocation;

        ItemViewHolder(View itemView) {
            super(itemView);
//            cardBackground = itemView.findViewById(R.id.item_schedule_card_backgound);
//            subjectName = itemView.findViewById(R.id.item_schedule_subject_name);
//            subjectProfessorName = itemView.findViewById(R.id.item_schedule_subject_professor_name);
//            subjectTime = itemView.findViewById(R.id.item_schedule_subject_time);
//            subjectLocation = itemView.findViewById(R.id.item_schedule_subject_location);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectProfessorName = itemView.findViewById(R.id.professor_name);
            subjectTime = itemView.findViewById(R.id.time);
            topLine = itemView.findViewById(R.id.view_01);
            bottomLine = itemView.findViewById(R.id.view_02);
            background = itemView.findViewById(R.id.view_03);
            marker = itemView.findViewById(R.id.marker);
        }
    }

    public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        private LinearLayout headerBackground; // cor de fundo do titulo
        private TextView dayOfWeek; // titulo
        private TextView fullDate; // data completa

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerBackground = itemView.findViewById(R.id.item_schedule_day_header_bg);
            dayOfWeek = itemView.findViewById(R.id.item_schedule_day);
            fullDate = itemView.findViewById(R.id.full_date);
        }
    }

    public ScheduleAdapter(OnItemClickListener onItemClickListener) {
        mSchedules = new ArrayList<>();
        mFilteredList = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

//    public ScheduleAdapter(OnItemClickListener onItemClickListener, List<Schedule> schedules) {
//        this.onItemClickListener = onItemClickListener;
//        this.mSchedules = schedules;
//        this.sections = subjects;
//    }

    public void setList(List<Schedule> list, int i) {
        mSchedules = list;
        mFilteredList = list;
        sort(i);
        makeSections();
    }

    /**
     * Define de type of the view list
     *
     * @param type 0 to ascending 1 to descending 2 by current day
     */
    public void sort(int type) {
        switch (type) {
            case 0:
                groupListByCurrentDay(mFilteredList);
                break;
            case 1:
                groupList(mFilteredList);
                break;
            case 2:
                groupListReverse(mFilteredList);
                break;
        }
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).schedules.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public GhostHeaderViewHolder onCreateGhostHeaderViewHolder(ViewGroup parent) {
        final View ghostView = new View(parent.getContext());
        ghostView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new GhostHeaderViewHolder(ghostView);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_schedule_type_list_two, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_schedule_type_list_header_two, parent, false);
        return new HeaderViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {

        Section s = sections.get(sectionIndex);
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;
        Schedule schedule = s.schedules.get(itemIndex);

        ivh.subjectName.setText(schedule.getSubjectName());
        ivh.subjectProfessorName.setText(schedule.getProfessorName());
        ivh.subjectTime.setText(schedule.getTime());

        ivh.subjectTime.setShadowLayer(1, 0, 0, Color.parseColor("#9E9E9E"));

        if ( itemIndex == 0 ) {
            ivh.topLine.setVisibility(View.INVISIBLE);
            ivh.bottomLine.setVisibility(View.VISIBLE);
        } else if ( itemIndex ==  (s.schedules.size() - 1) ) {
            ivh.bottomLine.setVisibility(View.INVISIBLE);
            ivh.topLine.setVisibility(View.VISIBLE);
        } else {
            ivh.topLine.setVisibility(View.VISIBLE);
            ivh.bottomLine.setVisibility(View.VISIBLE);
        }

        if( !CalendarUtils.compareTime(schedule.getDayOfWeek(), schedule.getTime()) ) {
            ivh.marker.setBackgroundResource(R.drawable.ic_check_mark_round);
        } else {
            ivh.marker.setBackgroundResource(R.drawable.ic_circle_shape_outline);
        }

//        Drawable mDrawable = DrawableCompat.wrap(ivh.background.getBackground());
//        DrawableCompat.setTint(mDrawable, Color.parseColor("#3498db"));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.dayOfWeek.setText(CalendarUtils.intToStringDayBrShort(Integer.parseInt(s.alpha)));
        hvh.fullDate.setText(CalendarUtils.getCurrentDateBrFormat());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilteredList = mSchedules;
                } else {

                    List<Schedule> filteredList = new ArrayList<>();

                    for (Schedule schedule : mSchedules) {
                        if (schedule.getSubjectName().toLowerCase().contains(charString)
                                || CalendarUtils.intToStringDayBrShort(Integer.parseInt(schedule.getDayOfWeek())).toLowerCase().contains(charString)
                                || schedule.getProfessorName().toLowerCase().contains(charString)) {
                            filteredList.add(schedule);
                        }
                    }
                    // To avoid the shit NullPointerException !!
                    mFilteredList = filteredList.isEmpty() ? mSchedules : filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<Schedule>) filterResults.values;
                makeSections();
            }
        };
    }

    private void groupList(List<Schedule> list) {
        Collections.sort(list, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule s1, Schedule s2) {
                return s1.getDayOfWeek().compareTo(s2.getDayOfWeek());
            }
        });
    }

    private void groupListReverse(List<Schedule> list) {
        groupList(list);
        Collections.reverse(list);
    }

    private void groupListByCurrentDay(List<Schedule> list) {

        final int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        groupList(list);

        boolean flag = false;

        List<Schedule> newList = new ArrayList<>();
        List<Schedule> oldList = new ArrayList<>();

        for (Schedule s : list) {
            if (Integer.parseInt(s.getDayOfWeek()) == currentDay) { // add this item to the top of the list
                flag = true;
                newList.add(s);
            } else if (Integer.parseInt(s.getDayOfWeek()) != currentDay && flag) { // add this item to the middle of the list
                newList.add(s);
            } else { // add this item to the bottom of the list
                oldList.add(s);
            }
        }
//        for (Schedule s : list) {
//            if (Integer.parseInt(s.getDayOfWeek()) == currentDay) {
//                newList.add(s);
//            } else {
//                oldList.add(s);
//            }
//        }

        newList.addAll(oldList);
        mFilteredList.clear();
        mSchedules.clear();
        mFilteredList = newList;
        mSchedules = newList;
    }


    private void makeSections() {

        List<Section> newSections = new ArrayList<>();

//        sections.clear();
        String alpha = "";
        Section currentSection = null;

        for (Schedule schedule : mFilteredList) {
            if (!schedule.getDayOfWeek().equals(alpha)) {

                if (currentSection != null) {
                    newSections.add(currentSection);
                }

                currentSection = new Section();
                alpha = schedule.getDayOfWeek();
                currentSection.alpha = alpha;
            }

            if (currentSection != null) {
                currentSection.schedules.add(schedule);
            }
        }

        newSections.add(currentSection);
        sections.clear();
        sections = newSections;
        notifyAllSectionsDataSetChanged();
    }
}