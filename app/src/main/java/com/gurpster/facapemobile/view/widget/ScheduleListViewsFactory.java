package com.gurpster.facapemobile.view.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.ScheduleRepository;
import com.gurpster.facapemobile.model.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ScheduleRepository scheduleRepository;
    private List<Subject> subjects = new ArrayList<>();
    private List<Schedule> scheduleList = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;

    public ScheduleListViewsFactory(ScheduleRepository scheduleRepository, Context context, Intent intent) {
        this.scheduleRepository = scheduleRepository;
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
//        generateSubjects();
        loadData();
    }

    @Override
    public void onCreate() {
//        subjects = generateItens();
//        generateSubjects();
        Log.d("widgetList", "Create Widget!");
    }

    @Override
    public void onDestroy() {
        scheduleList.clear();
    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_schedule_widget_type_list);

        Subject item = subjects.get(position);
        row.setTextViewText(R.id.item_widget_subject_name, item.getName());
        row.setTextViewText(R.id.item_widget_subject_time, item.getStartTime());
        row.setTextViewText(R.id.item_widget_subject_professor_name, item.getProfessorName());
//        row.setTextViewText(R.id.item_widget_subject_location, item.getProfessorName());

//        BorderDrawable drawable = new BorderDrawable();
//        drawable.setLeftBorder(2, ColorUtils.pickColor(position));
        row.setInt(R.id.item_schedule_card_backgound, "setBackgroundResource", getBackground(position));

        Intent fillInIntent = new Intent();
        row.setOnClickFillInIntent(R.id.item_schedule_card_backgound, fillInIntent);

        Log.d("widgetList", "position: " + position + " subject: " + item.getName());
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return  1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
//        subjects.clear();
//        generateItens();
        scheduleList.clear();
        loadData();
    }

    private void loadData() {
        scheduleRepository.getSchedules(new ScheduleDataSource.LoadScheduleCallback() {
            @Override
            public void onScheduleLoaded(List<Schedule> schedules) {
                scheduleList = schedules;
            }

            @Override
            public void onDataNotAvailable() {
                scheduleList = new ArrayList<>(0);
            }
        });
    }

    private void generateItens() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        String day = dayFormat.format(calendar.getTime());

        List<Subject> list = new ArrayList<>();

        //1° horario
        list.add(new Subject("FUND COMP", "Monday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FUND COMP", "Tuesday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FISICA COMP", "Wednesday", "07:30", "09:10", "Fabrizio Costa"));
        list.add(new Subject("ALGORITMOS", "Thursday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("ALGORITMOS", "Friday", "07:30", "09:10", "Alexandre Braga"));

        //2° horario
        list.add(new Subject("FISICA COMP", "Monday", "09:10", "10:50", "Fabrizio Costa"));
        list.add(new Subject("MET PQ CT", "Tuesday", "09:10", "10:50", "Dinani"));
        list.add(new Subject("MAT BASICA", "Wednesday", "09:10", "10:50", "Geovanilde"));
        list.add(new Subject("MET PQ CT", "Thursday", "09:10", "10:50", "Dinani"));
        list.add(new Subject("MAT BASICA", "Friday", "09:10", "10:50", "Geovanilde"));

        for (Subject subject: list) {
            if(day.equals(subject.getDay())) {
                subjects.add(subject);
            }
        }
    }

    private void generateSubjects() {

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
//        String day = dayFormat.format(calendar.getTime());

        subjects.add(new Subject("FUND COMP - Sala", "Monday", "07:30", "09:10", "Gondim"));
        subjects.add(new Subject("MET PQ CT - Sala", "Tuesday", "09:10", "10:50", "Dinani"));
    }

    private Integer getBackground(int i) {
        Integer rt;

//        int randomNum = new Random().nextInt(5 - 1 + 1) + 1;

        switch (i) {
            case 0:
                rt = R.drawable.left_border_green;
                break;
            case 1:
                rt = R.drawable.left_border_red;
                break;
            case 2:
                rt = R.drawable.left_border_blue;
                break;
            case 3:
                rt = R.drawable.left_border_yellow;
                break;
            case 4:
                rt = R.drawable.left_border_yellow;
                break;
            default:
                rt = R.drawable.left_border_blue;
                break;
        }

        return rt;
    }
}
