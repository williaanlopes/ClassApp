package com.gurpster.facapemobile.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.gurpster.facapemobile.AcornAPI.enrol.Day;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.model.Subject;
import com.gurpster.facapemobile.util.CalendarUtils;
import com.gurpster.facapemobile.util.Configuration;
import com.gurpster.facapemobile.view.activities.DrawerActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TimetableFragment extends DaggerFragment implements ScheduleGridContract.View {

    @Inject
    ScheduleGridContract.Presenter presenter;

//    ScheduleListFragment.ScheduleListPerspective scheduleListPerspective;

    final int numberOfRows = 8, numberOfColumns = 7;
    private TextView[][] tv = new TextView[numberOfRows][numberOfColumns];
    private LinearLayout[][] ll = new LinearLayout[numberOfRows][numberOfColumns];
    private TextView selectedTextView;
    float scale;
    DisplayMetrics displayMetrics;
    int displayHeight;
    int displayWidth;
    private int minRowNums;
    final boolean SHOW_ALL_ROWS = false;
    //private Context context;
    View view;
    private int rowSize;
//    private Subject subject = new Subject();

//    public static TimetableFragment newInstance(String mode) {
//        // Load fragment
//        TimetableFragment fragment = new TimetableFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, mode);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Inject
    public TimetableFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // get current device screen pixels
        displayMetrics = DrawerActivity.displaymetrics;
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;
        scale = displayMetrics.density;
        rowSize = getPx(60);
        minRowNums = displayHeight / rowSize;
        if (minRowNums > 15)
            minRowNums = 15;

        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.fragment_schedule_grid, null, false);

//        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        //swipeContainer.setDistanceToTriggerSync(30);

//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        swipeContainer.post(new Runnable() {
//            @Override
//            public void run() {
//                if(updating)
//                    swipeContainer.setRefreshing(true);
//            }
//        });
//        swipeContainers.add(swipeContainer);

//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (updating) {
//                    return;
//                }
//                updating = true;
//
//                if (UserInfo.getUsername(activity).isEmpty() || UserInfo.getPassword(activity) == null || UserInfo.getPassword(activity).isEmpty()) {
//
//                    swipeContainer.setRefreshing(false);
//                    ((DrawerActivity) activity).downloadCoursesPrompt();
//                    return;
//                } else if (UserInfo.isUserPassChanged(activity)) {
//                    Log.i("Refresh", "user/pass changed");
//                    DrawerActivity.acorn = new Acorn(UserInfo.getUsername(activity), UserInfo.getPassword(activity));
//                }
//                ((DrawerActivity) activity).downloadCourseData(DrawerActivity.acorn, swipeContainer);
//
//            }
//
//        });


        // textViews tv --> LinearLayout ll--> GridLayout gl
        GridLayout gl = view.findViewById(R.id.gridLayout);
        // the earliest class begins at 7, thus i = 7
        for (int i = 0; i < numberOfRows; i++) {

            // set the left side time
            // print time schedule grid
            for (int k = 0; k < 1; k++) {
                tv[i][k] = new TextView(context);
//                tv[i][k].setText(i + ":00");
                tv[i][k].setText(getTimeList().get(i));
                tv[i][k].setBackgroundResource(R.drawable.cell_shape_time);
                tv[i][k].setGravity(Gravity.END);

//                if((i/2) != 0)
//                else
//                    tv[i][k].setGravity(Gravity.RIGHT | Gravity.BOTTOM);

                tv[i][k].setTextSize(12); // 10

                //params
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = rowSize;
                param.width = displayWidth / 10; // 11
                param.rightMargin = 5;
                param.leftMargin = 2;
                param.topMargin = 1;
                param.rowSpec = GridLayout.spec(i);
                param.columnSpec = GridLayout.spec(k);

                LinearLayout.LayoutParams param_mid = new LinearLayout.LayoutParams(displayWidth / 10, rowSize);

                tv[i][k].setLayoutParams(param_mid);

                //LinearLayout
                ll[i][k] = new LinearLayout(context);
                ll[i][k].setOrientation(LinearLayout.VERTICAL);
                ll[i][k].setGravity(Gravity.BOTTOM);
                ll[i][k].setLayoutParams(param);
                ll[i][k].addView(tv[i][k], 0);

                gl.addView(ll[i][k]);
            }

            //add
            for (int k = 1; k < numberOfColumns; k++) {

                tv[i][k] = new TextView(context);
                tv[i][k].setTextSize(10);
                tv[i][k].setBackgroundResource(R.drawable.cell_shape_bot);
                tv[i][k].setTextColor(Color.WHITE);
                tv[i][k].setTypeface(null, Typeface.BOLD);


                //params
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = rowSize;
                param.width = (displayWidth) / 11 * 2 - 5;
                param.rightMargin = 5;
                param.topMargin = 1;
                param.rowSpec = GridLayout.spec(i);
                param.columnSpec = GridLayout.spec(k);


                LinearLayout.LayoutParams param_mid = new LinearLayout.LayoutParams((displayWidth) / 11 * 2 - 5, 0, 10);
                tv[i][k].setLayoutParams(param_mid);

                //LinearLayout
                ll[i][k] = new LinearLayout(context);
                ll[i][k].setOrientation(LinearLayout.VERTICAL);
                ll[i][k].setLayoutParams(param);
                ll[i][k].addView(tv[i][k], 0);

                gl.addView(ll[i][k]);

            }
        }

        // Add OnclickListener to invoke timetable submenu
        TextView.OnClickListener ocl = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                selectedTextView = (TextView) v;
//
                if (selectedTextView.getText() != "") {
                    String name = selectedTextView.getText().toString();

//                    ColorDrawable cd = (ColorDrawable) selectedTextView.getBackground();
//                    int colorCode = cd.getColor();
//                    String code = selectedTextView.getText().toString().substring(1, 9);
//                    int index = 0;
//                    for(int i = 0; i< courseList.length; i++){
//                        if(courseList[i].getCourseCode().contains(code)){
//                            index = i;
//                            break;
//                        }
//                    }
//                    Intent intent = new Intent(getActivity(), SubjectInfoActivity.class);
//                    intent.putExtra("id", 0);
//                    intent.putExtra("name", subject.getName());
////                    intent.putExtra("INDEX",index);
//                    startActivityForResult(intent, 2);
//
                    Toast.makeText(getContext(), "Clicked on " + name, Toast.LENGTH_SHORT).show();
                }
            }


        };

        for (int i = 0; i < numberOfRows; i++) {
            for (int k = 1; k < numberOfColumns; k++) {
                tv[i][k].setOnClickListener(ocl);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        if (getArguments() != null)
//            mode = getArguments().getString(ARG_PARAM1);

//        removeRows();
    }

//    public void refresh() {
//        Log.i("TimetableRefresh", mode + " Timetable");
//
//        if (courseJson == null || courseJson.equals(""))
//            courseJson = Configuration.loadString("courseJson", activity);
//
//        Gson gson = new Gson();
//        courseList = gson.fromJson(courseJson, Course[].class);
//
//        if (mode != null) {
////            removeRows();
////            if(courseList != null)
////                refreshCeil();
//        }
//    }

//    private void removeRows() {
//        // reset to VISIBLE and default setting
//        for (int i = 0; i < numberOfRows; i++) {
//            for (int k = 0; k < numberOfColumns; k++) {
//                if (ll[i][k] != null) {
//                    if (k != 0) {
//                        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                        param.height = rowSize;
//                        param.width = (displayWidth) / 11 * 2 - 5;
//                        param.rightMargin = 5;
//                        param.topMargin = 1;
//                        param.rowSpec = GridLayout.spec(i);
//                        param.columnSpec = GridLayout.spec(k);
//                        ll[i][k].setLayoutParams(param);
//                    }
//                    ll[i][k].setVisibility(View.VISIBLE);
//                }
//                if (tv[i][k] != null) {
//                    if (k != 0) {
//
//                        tv[i][k].setText("");
//                        tv[i][k].setBackgroundResource(R.drawable.cell_shape_bot);
//                    }
//                    tv[i][k].setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        //delete rows
//        try {
//            if (!SHOW_ALL_ROWS) {
//                float earliest = 7, latest = 22;
//                Date time[] = null;
//                try {
//                    time = getEarliestAndLatestTime();
//                    //System.out.println("time: " + time);
//                } catch (ParseException e) {
//                    Log.i("removeRows", "Uninitialized timetable");
//                }
//                if (mode.equals("fall")) {
//                    earliest = time[0].getHours();
//                    latest = time[1].getHours();
//                } else if (mode.equals("winter")) {
//                    earliest = time[2].getHours();
//                    latest = time[3].getHours();
//                } else if (mode.equals("summer1")) {
//                    earliest = time[4].getHours();
//                    latest = time[5].getHours();
//                } else if (mode.equals("summer2")) {
//                    earliest = time[6].getHours();
//                    latest = time[7].getHours();
//                }
//                for (int i = 7; i < earliest; i++) {
//                    for (int k = 0; k < 6; k++) {
//                        if (ll[i][k] != null)
//                            ll[i][k].setVisibility(View.GONE);
//                        if (tv[i][k] != null)
//                            tv[i][k].setVisibility(View.GONE);
//                    }
//                }
//                for (int i = (int) latest + 1; i < 24; i++) {
//                    for (int k = 0; k < 6; k++) {
//                        if (ll[i][k] != null)
//                            ll[i][k].setVisibility(View.GONE);
//                        if (tv[i][k] != null)
//                            tv[i][k].setVisibility(View.GONE);
//                    }
//                }
//            }
//        } catch (NullPointerException e) {
//            Log.i("removeRows", "Uninitialized timetable");
//        }
//    }

//    public static Date[] getEarliestAndLatestTime() throws ParseException {
//
//        DateFormat format = new SimpleDateFormat("hh:mma", Locale.CANADA);
//        // fall earliest, fall latest, winter earliest, winter latest
//        Date[] time = {format.parse("11:59PM"), format.parse("00:01AM"), format.parse("11:59PM"), format.parse("00:01AM"),
//                format.parse("11:59PM"), format.parse("00:01AM"), format.parse("11:59PM"), format.parse("00:01AM")};
//        for (Course course : courseList) {
//            int courseSession = getCourseSession(course);
//
//            List<com.gurpster.facapemobile.entity.Activity> activities = course.getActivities();
//            for (com.gurpster.facapemobile.entity.Activity activity : activities) {
//                if (activity.getDays() != null)
//                    for (Day day : activity.getDays()) {
//                        Date startTime = null;
//                        Date endTime = null;
//                        startTime = format.parse(day.getStartTime());
//                        endTime = format.parse(day.getEndTime());
//                        if (courseSession == FALL || courseSession == FULL) {
//                            if (startTime.before(time[0]))
//                                time[0] = startTime;
//                            if (endTime.after(time[1]))
//                                time[1] = endTime;
//                        }
//                        if (courseSession == WINTER || courseSession == FULL) {
//                            if (startTime.before(time[2]))
//                                time[2] = startTime;
//                            if (endTime.after(time[3]))
//                                time[3] = endTime;
//                        }
//                        if (courseSession == SUMMER_FULL || courseSession == SUMMER_1) {
//                            if (startTime.before(time[4]))
//                                time[4] = startTime;
//                            if (endTime.after(time[5]))
//                                time[5] = endTime;
//                        }
//                        if (courseSession == SUMMER_FULL || courseSession == SUMMER_2) {
//                            if (startTime.before(time[6]))
//                                time[6] = startTime;
//                            if (endTime.after(time[7]))
//                                time[7] = endTime;
//                        }
//                    }
//            }
//        }
//        for (int i = 0; i < 4; i++) {
//            //  calculate the start/end time difference and update them to display
//            while (time[i * 2 + 1].getHours() - time[i * 2].getHours() < minRowNums) {
//                if (time[i * 2].getHours() > 7)
//                    time[i * 2].setHours(time[i * 2].getHours() - 1);
//                if (time[i * 2 + 1].getHours() < 22)
//                    time[i * 2 + 1].setHours(time[i * 2 + 1].getHours() + 1);
//            }
//        }
//
//        return time;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {
//            refreshCeil();
//            createSchedule();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadSchedules();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        inflater.inflate(R.menu.menu_schedule_type, menu);

        MenuItem item1 = menu.findItem(R.id.schedule_perspective_two);
        item1.setVisible(true);
        MenuItem item2 = menu.findItem(R.id.schedule_perspective_one);
        item2.setVisible(false);

//        MenuItem item3 = menu.findItem(R.id.group);
//        item3.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.schedule_perspective_two: {
//                boolean changed = Configuration.saveString("defaultTimetable", "1", getContext());

//                scheduleListPerspective.onPerspectiveChange(1);
                presenter.setTimetable(String.valueOf(1));

                // Create new fragment and transaction
                ScheduleListFragment newFragment = new ScheduleListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.flContent, newFragment);
                transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();

//                if(changed)
//                    Toast.makeText(getContext(), "Change Perspective!", Toast.LENGTH_SHORT).show();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSchedule(List<Schedule> schedules) {

        int count = 0;
        int col = 1;
        Locale locale = new Locale("pt", "BR");
//        for (Subject subject : subject.generateScheduleItens()) {
        for (Schedule schedule : schedules) {

            int startTime = -1, endTime = -1, weekNum = -1;

            int dayOfWeek = Integer.parseInt(schedule.getDayOfWeek()) - 1;
            SimpleDateFormat format = new SimpleDateFormat("H:m", locale);

            try {
                startTime = format.parse(schedule.getTime()).getHours();
//                startTime = format.parse(subject.getStartTime()).getHours();
//                endTime = format.parse(subject.getEndTime()).getHours();
//                endTime = format.parse(subject.getEndTime()).getHours();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            addContent2(startTime, dayOfWeek, 2, schedule.getSubjectName(), count, 2, schedule.getTime());
            count++;
//            col++;
//            }

        }

//        GridLayout gl = (GridLayout) view.findViewById(R.id.gridLayout);
//        for (int i = 13; i < numberOfRows; i++) {
//
//            for(int j = 0; j < numberOfColumns; j++) {
//                gl.removeView(ll[i][j]);
//            }
//        }
    }

    public static float getDecimal(float a) {
        return a - (int) a;
    }

    public void addContent2(float row, float col, float rowspan, String content, int courseNum, int color, String time) {

        Map<String, Integer> t = getTime();
        int r = t.get(time);

//        int row_int = (int) row;
        int col_int = (int) col;
        int rowspan_int = (int) (Math.ceil(rowspan + getDecimal(row)));
//        int rowspan_int = 2;
        float row_dec_top = getDecimal(row);
        //calculate the greatest occupied rowSpan
        float row_dec_bot = rowspan_int - row_dec_top - rowspan;

        //float col_dec = getDecimal(col);
        int top_weight = Math.round(row_dec_top / rowspan_int * 1000);
        int bot_weight = Math.round(row_dec_bot / rowspan_int * 1000);
        int mid_weight = 1000 - top_weight - bot_weight;


        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.rowSpec = GridLayout.spec(r, 1);
//        Log.d("TimeTableFragment", "rowspan_int " + rowspan_int);
        param.setGravity(Gravity.FILL_VERTICAL);
        param.width = (displayWidth) / 11 * 2 - 5;
        param.rightMargin = 5;
        param.topMargin = 1;
        param.columnSpec = GridLayout.spec(col_int);

        // TO-DO fix this
//        if(!(row_int == row) || !(col_int == col) || !(rowspan_int == rowspan)) {
//
////            System.out.println("an abnormal time");
////            System.out.println(row_dec_top+" "+"?"+" "+row_dec_bot);
////            System.out.println(top_weight+" "+mid_weight+" "+bot_weight);
//            LinearLayout.LayoutParams param_mid = new LinearLayout.LayoutParams((displayWidth) / 11 * 2 - 5, 0, mid_weight);
//
//            tv[row_int][col_int].setLayoutParams(param_mid);
//        }

        try {

//            if (rowspan > 1) {
//                for (int i = 1; i < rowspan; i++) {
//                    tv[r + i][col_int].setVisibility(View.GONE);
//                }
//            }

            ll[r][col_int].setLayoutParams(param);
            tv[r][col_int].setText(content);
            tv[r][col_int].setBackgroundColor(pickColor(color));
            tv[r][col_int].setPadding(5, 5, 5, 5);
            tv[r][col_int].setGravity(Gravity.FILL_VERTICAL);
//            tv[row_int][col_int].setTextColor(getResources().getColor(R.color.md_grey_800));


        } catch (Exception e) {
            e.printStackTrace();
        }


//        tv[row_int][col_int].setText(" CSC207H1\\n LEC 0101\\n RW000");

//        if(courseList[courseNum].color != 0){
//            tv[row_int][col_int].setBackgroundColor(courseList[courseNum].color);
//            //System.out.println("Set Color: " + courseList[courseNum].color);
//        }
//        else {
//        }
    }

//    public void addContent(float row, float col, float rowspan, String content, int courseNum, int color) {
//
//        int row_int = (int) row;
//        int col_int = (int) col;
//        int rowspan_int = (int) (Math.ceil(rowspan + getDecimal(row)));
////        int rowspan_int = 2;
//        float row_dec_top = getDecimal(row);
//        //calculate the greatest occupied rowSpan
//        float row_dec_bot = rowspan_int - row_dec_top - rowspan;
//
//        //float col_dec = getDecimal(col);
//        int top_weight = Math.round(row_dec_top / rowspan_int * 1000);
//        int bot_weight = Math.round(row_dec_bot / rowspan_int * 1000);
//        int mid_weight = 1000 - top_weight - bot_weight;
//
//
//        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//        param.rowSpec = GridLayout.spec(row_int, rowspan_int);
//        Log.d("TimeTableFragment", "rowspan_int " + rowspan_int);
//        param.setGravity(Gravity.FILL_VERTICAL);
//        param.width = (displayWidth) / 11 * 2 - 5;
//        param.rightMargin = 5;
//        param.topMargin = 1;
//        param.columnSpec = GridLayout.spec(col_int);
//
//        // TO-DO fix this
////        if(!(row_int == row) || !(col_int == col) || !(rowspan_int == rowspan)) {
////
//////            System.out.println("an abnormal time");
//////            System.out.println(row_dec_top+" "+"?"+" "+row_dec_bot);
//////            System.out.println(top_weight+" "+mid_weight+" "+bot_weight);
////            LinearLayout.LayoutParams param_mid = new LinearLayout.LayoutParams((displayWidth) / 11 * 2 - 5, 0, mid_weight);
////
////            tv[row_int][col_int].setLayoutParams(param_mid);
////        }
//
//        try {
//
////            if (rowspan > 1) {
////                for (int i = 1; i < rowspan; i++) {
////                    tv[row_int + i][col_int].setVisibility(View.GONE);
////                }
////            }
//
//            ll[row_int][col_int].setLayoutParams(param);
//            tv[row_int][col_int].setText(content);
////            BorderDrawable drawable = new BorderDrawable();
////            drawable.setBackground(getResources().getDrawable(R.drawable.item_schedule));
////            drawable.setTopBorder(2, pickColor(courseNum));
////            tv[row_int][col_int].setBackground(getResources().getDrawable(R.drawable.item_schedule));
////            ColorUtils.setAlphaComponent(pickColor(courseNum), 150);
//            tv[row_int][col_int].setBackgroundColor(pickColor(color));
//            tv[row_int][col_int].setPadding(5, 5, 5, 5);
//            tv[row_int][col_int].setGravity(Gravity.FILL_VERTICAL);
////            tv[row_int][col_int].setTextColor(getResources().getColor(R.color.md_grey_800));
////            Log.d("TimeTableFragment", content);
//            Log.d("Matriz", "linha: " + row_int + " coluna: " + col_int);
//
//        } catch (Exception e) {
////            Log.d("TimeTableFragment", e.getMessage());
//            e.printStackTrace();
//        }
//
//
////        tv[row_int][col_int].setText(" CSC207H1\\n LEC 0101\\n RW000");
//
////        if(courseList[courseNum].color != 0){
////            tv[row_int][col_int].setBackgroundColor(courseList[courseNum].color);
////            //System.out.println("Set Color: " + courseList[courseNum].color);
////        }
////        else {
////        }
//    }

    /**
     * put values
     */
//    public void refreshCeil() {
//        int courseNum = 0;
//
//        for (Course course : courseList) {
//            // select the corresponding courses to display
//            int courseSession = getCourseSession(course);
//
//            if ((courseSession == FALL || courseSession == FULL) && mode.equals("fall") ||
//                    (courseSession == WINTER || courseSession == FULL) && mode.equals("winter") ||
//                    (courseSession == SUMMER_1 || courseSession == SUMMER_FULL) && mode.equals("summer1") ||
//                    (courseSession == SUMMER_2 || courseSession == SUMMER_FULL) && mode.equals("summer2")) {
//                List<com.gurpster.facapemobile.entity.Activity> activities = course.getActivities();
//                String contentCourse, enrollment;
//
//                int startTime = -1, endTime = -1, weekNum = -1;
//
//                for (com.gurpster.facapemobile.entity.Activity activity : activities) {
//                    if (activity != null)
//                        for (Day day : activity.getDays()) {
//                            Map<String, Integer> dayToNum = new HashMap<String, Integer>();
//                            dayToNum.put("Monday", 1);
//                            dayToNum.put("Tuesday", 2);
//                            dayToNum.put("Wednesday", 3);
//                            dayToNum.put("Thursday", 4);
//                            dayToNum.put("Friday", 5);
//                            dayToNum.put("Saturday", 6);
//                            //System.out.println(day.getDayOfWeek());
//                            weekNum = dayToNum.get(day.getDayOfWeek());
//                            //System.out.println( weekNum[courseNum]);
//                            SimpleDateFormat format = new SimpleDateFormat("hh:mma", Locale.CANADA);
//                            try {
//                                startTime = format.parse(day.getStartTime()).getHours();
//                                endTime = format.parse(day.getEndTime()).getHours();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            contentCourse = (" " + course.getCourseCode() + "\n "
//                                    + activity.getActivityId() + "\n "
//                                    + day.getRoomLocation());
//
////                            addContent(startTime, weekNum, endTime - startTime,
////                                    contentCourse, courseList[courseNum], courseNum);
////
////                            addContent(startTime, weekNum, endTime - startTime,
////                                    contentCourse, courseList[courseNum], courseNum);
//                        }
//                }
//            }
//            courseNum++;
//        }
//    }

    public int getPx(int dps) {
        return (int) (dps * scale + 0.5f);

    }

    public static int pickColor(int num) {
        int color;
        //material color
        switch (num) {
            case 0:
                color = Color.parseColor("#EF5350");
                return color;
            case 1:
                color = Color.parseColor("#EC407A");
                return color;
            case 2:
                color = Color.parseColor("#AB47BC");
                return color;
            case 3:
                color = Color.parseColor("#7E57C2");
                return color;
            case 4:
                color = Color.parseColor("#5C6BC0");
                return color;
            case 5:
                color = Color.parseColor("#42A5F5");
                return color;
            case 6:
                color = Color.parseColor("#29B6F6");
                return color;
            case 7:
                color = Color.parseColor("#26C6DA");
                return color;
            case 8:
                color = Color.parseColor("#26A69A");
                return color;
            case 9:
                color = Color.parseColor("#66BB6A");
                return color;
            case 10:
                color = Color.parseColor("#9CCC65");
                return color;
            case 11:
                color = Color.parseColor("#D4E157");
                return color;
            case 12:
                color = Color.parseColor("#8D6E63");
                return color;
            default:
                color = Color.parseColor("#FFA726");
                return color;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public List<String> getTimeList() {
        List<String> list = new ArrayList<>();
        list.add("07:30");
        list.add("09:10");
        list.add("10:50");
        list.add("13:30");
        list.add("15:10");
        list.add("16:50");
        list.add("18:50");
        list.add("20:30");
        return list;
    }

    public Map<String, Integer> getTime() {

        Map<String, Integer> time = new HashMap<>();
        time.put("07:30", 0);
        time.put("09:10", 1);
        time.put("10:50", 2);
        time.put("13:30", 3);
        time.put("15:10", 4);
        time.put("16:50", 5);
        time.put("18:50", 6);
        time.put("20:30", 7);

        return time;
    }

    @Override
    public void showSchedules(List<Schedule> schedules) {
        createSchedule(schedules);
    }
}
