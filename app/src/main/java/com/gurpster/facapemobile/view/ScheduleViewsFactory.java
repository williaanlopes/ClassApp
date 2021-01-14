package com.gurpster.facapemobile.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.data.source.ScheduleDataSource;
import com.gurpster.facapemobile.data.source.ScheduleRepository;
import com.gurpster.facapemobile.model.Subject;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjection;

/**
 * Created by Williaan Lopes (d3x773r) on 10/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduleViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    ScheduleRepository scheduleRepository;
    private static final String[] items = {"lorem"};
    private List<Subject> subjects = new ArrayList<>();
    private List<Schedule> scheduleList = new ArrayList<>();

    private Context context = null;
    private int appWidgetId;


//    final int numberOfRows = 23, numberOfColumns = 7;
//    private static int rowSize;
//    private TextView[][] tv = new TextView[numberOfRows][numberOfColumns];
//    private LinearLayout[][] ll = new LinearLayout[numberOfRows][numberOfColumns];
//    static DisplayMetrics displayMetrics;
//    static int displayHeight;
//    static int displayWidth;

    public ScheduleViewsFactory(ScheduleRepository scheduleRepository, Context context, Intent intent) {
        this.scheduleRepository = scheduleRepository;
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
//        subjects = generateItens();
        loadData();
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_schedule_widget);

        // add time
//        for(int i = 0; i < 1; i++){
//            for(int j = 0; j < 2; j++){
//                row.setTextViewText(getTextViewId(i, j), subjects.get(j).getStartTime());
//            }
//        }

        try{

            // add schedule
            int count = 0;
            for(int i = 0; i < 2; i++){
                for(int j = 1; j < 6; j++){
                    row.setTextViewText(getTextViewId(i, j), scheduleList.get(count).getSubjectName());
                    Log.d("ScheduleViewsFactory", "item -> " + scheduleList.get(j).getSubjectName());
                    count++;
                }
            }

        } catch (Exception e){
            Log.d("ScheduleViewsFactory", "Error -> " + e.getMessage());
        }
//        Intent i = new Intent();
//        Bundle extras = new Bundle();
//        extras.putString(ScheduleWidget.EXTRA_WORD, items[position]);
//        i.putExtras(extras);
//        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
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
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
//        subjects = generateItens();
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

    private static List<Subject> generateItens() {
        List<Subject> list = new ArrayList<>();

        //1° horario
        list.add(new Subject("FUND COMP - Sala", "Monday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FUND COMP - Sala", "Tuesday", "07:30", "09:10", "Gondim"));
        list.add(new Subject("FISICA COMP - Sala", "Wednesday", "07:30", "09:10", "Fabrizio Costa"));
        list.add(new Subject("ALGORITMOS - Sala", "Thursday", "07:30", "09:10", "Alexandre Braga"));
        list.add(new Subject("ALGORITMOS - Sala", "Friday", "07:30", "09:10", "Alexandre Braga"));

        //2° horario
        list.add(new Subject("FISICA COMP - Sala", "Monday", "09:10", "10:50", "Fabrizio Costa"));
        list.add(new Subject("MET PQ CT - Sala", "Tuesday", "09:10", "10:50", "Dinani"));
        list.add(new Subject("MAT BASICA - Sala", "Wednesday", "09:10", "10:50", "Geovanilde"));
        list.add(new Subject("MET PQ CT - Sala", "Thursday", "09:10", "10:50", "Dinani"));
        list.add(new Subject("MAT BASICA - Sala", "Friday", "09:10", "10:50", "Geovanilde"));

        return list;
    }

    private int getTextViewId(int column, int row) {
        int ids[][] = {
                {R.id.text00, R.id.text01, R.id.text02, R.id.text03, R.id.text04, R.id.text05, R.id.text06},
                {R.id.text10, R.id.text11, R.id.text12, R.id.text13, R.id.text14, R.id.text15, R.id.text16},
        };
        return ids[column][row];
    }
}
