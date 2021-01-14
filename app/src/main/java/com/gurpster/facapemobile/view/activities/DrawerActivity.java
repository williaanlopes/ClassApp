package com.gurpster.facapemobile.view.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gurpster.facapemobile.BuildConfig;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Student;
import com.gurpster.facapemobile.receiver.WifiScannReceiver;
import com.gurpster.facapemobile.util.AlarmManagerHelper;
import com.gurpster.facapemobile.util.SimpleAlarmManager;
import com.gurpster.facapemobile.util.UserInfo;
import com.gurpster.facapemobile.view.fragments.DebtFragment;
import com.gurpster.facapemobile.view.fragments.GradeFragment;
import com.gurpster.facapemobile.view.fragments.HistoricFragment;
import com.gurpster.facapemobile.view.fragments.MessageFragment;
import com.gurpster.facapemobile.view.fragments.PerformanceFragment;
import com.gurpster.facapemobile.view.fragments.PlacesFragment;
import com.gurpster.facapemobile.view.fragments.ScheduleListFragment;
import com.gurpster.facapemobile.view.fragments.TimetableFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DrawerActivity extends DaggerAppCompatActivity
        implements DrawerContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static final boolean DEBUG = false;
    private static final String TAG = "MAIN";

    @Inject
    DrawerContract.Presenter presenter;
    @Inject
    TimetableFragment timetable_fall_fragment;
    @Inject
    GradeFragment gradeFragment;
    @Inject
    ScheduleListFragment scheduleListFragment;
    @Inject
    DebtFragment debtFragment;
    //    @Inject PerformanceFragment performanceFragment;
    @Inject
    HistoricFragment historicFragment;
    @Inject
    MessageFragment messageFragment;
    @Inject
    PlacesFragment placeFragment;

    FragmentManager fragmentManager;

    public Activity activity;
    public static float scale;
    public static DisplayMetrics displaymetrics;
    public static int dis_height;
    public static int dis_width;
    private static Handler handler = new Handler();
    private static String mTitle;
    private static boolean initialized = false;
    private static Menu navi_menu;
    public static String versionName, currentVersionCode;

    private NavigationView navigationView;
    private Runnable mPendingRunnable;
    Handler mHandler = new Handler();
    DrawerLayout drawer;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Fragment[] arrayFragments;
    private String[] arrayTitles;
    Toolbar toolbar;

    private final WifiScannReceiver wifiScannReceiver = new WifiScannReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;

        versionName = BuildConfig.VERSION_NAME;
        currentVersionCode = "" + BuildConfig.VERSION_CODE;

//        String lastVersionCode = Configuration.loadString("versionCode", this);

        // first time launch or from update
//        if (lastVersionCode.equals("") || lastVersionCode.equals("22")) {
//            downloadCoursesPrompt();
//        }

        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        dis_height = displaymetrics.heightPixels;
        dis_width = displaymetrics.widthPixels;
        scale = displaymetrics.density;

        setContentView(R.layout.activity_drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View view) {
//                invalidateOptionsMenu();
//                setTitle(mTitle);
                invalidateOptionsMenu();
                // If mPendingRunnable is not null, then add to the message queue
                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                    mPendingRunnable = null;
                }
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        navi_menu = navigationView.getMenu();

        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
//            runSplashScreen();
        } else { // restore from a destroyed activity
            if (navi_menu.getItem(0).isChecked()) {
                setTitle(arrayTitles[0]);
            } else if (navi_menu.getItem(1).isChecked()) {
                setTitle(arrayTitles[1]);
            } else if (navi_menu.getItem(2).isChecked()) {
                setTitle(arrayTitles[2]);
            } else if (navi_menu.getItem(3).isChecked()) {
                setTitle(arrayTitles[3]);
            } else if (navi_menu.getItem(4).isChecked()) {
                setTitle(arrayTitles[4]);
            } else if (navi_menu.getItem(5).isChecked()) {
                setTitle(arrayTitles[5]);
            }
        }

        arrayTitles = getResources().getStringArray(R.array.defaultTimetable);
        arrayFragments = new Fragment[]{
                scheduleListFragment,
                gradeFragment,
                historicFragment,
                debtFragment,
                messageFragment,
                placeFragment
        };
        //google analytics
        if (!initialized)
//            AnalyticsTrackers.initialize(this);
            initialized = true;
//        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

//        Configuration.saveString("versionCode", "" + currentVersionCode, this);
//        Configuration.saveString("versionName", "" + versionName, this);
//        CalendarUtils.createAlarm(getApplication());
//        AlarmManagerHelper.setAlarm(getApplication());

        new SimpleAlarmManager(this).setup(SimpleAlarmManager.INTERVAL_DAY, 8, 30, 0).register(15).start();
    }

    private void selectFragment(final int index) {
        mPendingRunnable = new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                selectTitleAndFragment(index);
            }
        };
        closeDrawer();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        else if(backPressed.isSearchActive()) {
//            backPressed.closeSearchView();
//            Toast.makeText(activity, "SearchActive", Toast.LENGTH_SHORT).show();
//        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
//        boolean CLOSE_DRAWER = true;
        int index = 0;

        if (id == R.id.fall_timetable) {
            index = 0;
        } else if (id == R.id.grade_menu) {
            index = 1;
        } else if (id == R.id.drawer_menu_history) {
            index = 2;
        } else if (id == R.id.drawer_menu_ticket) {
            index = 3;
        } else if (id == R.id.drawer_menu_notification) {
            index = 4;
        } else if (id == R.id.drawer_menu_map) {
            index = 5;
        } else if (id == R.id.setting) {
//            CLOSE_DRAWER = false;
            startActivity(new Intent(DrawerActivity.this, SettingsActivity.class));
        }
        selectFragment(index);
        return true;
    }

    private void closeDrawer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }, 200); // default 10
    }

    /**
     * update course info
     */
    public void downloadCoursesPrompt() {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Retrieving data...");
        progress.setCancelable(false);

        final String username = UserInfo.getUsername(this);
        if (!username.isEmpty() && !UserInfo.getPassword(this).isEmpty()) {
            if (UserInfo.isUserPassChanged(this)) {
//                acorn = new Acorn(username, UserInfo.getPassword(this));
            }
            progress.show();
//            downloadCourseData(acorn, null);

        }
        // prompt user to input username and password
        else {
            UserInfo.promptInputUserPassAndUpdateCourseData(this, progress, null);

        }
    }

//    public void downloadCourseData(final Acorn acorn, final SwipeRefreshLayout swipeContainer){
//        final Context context = this;
//        acorn.doLogin(new SimpleListener() {
//            @Override
//            public void success() {
//                acorn.getCourseManager().loadCourses();
//                List<Course> courseList = new ArrayList<>();
//
//                List<EnrolledCourse> enrolledCourseList =  acorn.getCourseManager().getAppliedCourses();
//                for(EnrolledCourse enrolledCourse: enrolledCourseList){
//                    Log.i("downloadCourseData", enrolledCourse.toString());
//                    try{
//                        courseList.add(new Course(enrolledCourse));
//                    } catch(Exception e){
//                        Gson gson = new Gson();
//                        Log.i("downloadCourseData", "Error" + e.getMessage() + "\n" + gson.toJson(enrolledCourse));
//                        Tracker t = AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
//                        t.send(new HitBuilders.ExceptionBuilder()
//                                .setDescription( "Error \n" + gson.toJson(enrolledCourse))
//                                .setFatal(true)
//                                .build());
//                    }
//                }
//
////                List<PlannedCourse> plannedCourseList =  acorn.getCourseManager().getPlannedCourses();
////                for(PlannedCourse plannedCourse: plannedCourseList){
////                    Log.i("downloadCourseData", plannedCourse.toString());
////                    courseList.add(new Course(plannedCourse));
////                }
//
////                Collections.sort(courseList, new Comparator<Course>() {
////                    @Override
////                    public int compare(Course o1, Course o2) {
////                        return  TimetableFragment.getCourseSession(o1) - TimetableFragment.getCourseSession(o2);
////                    }
////                });
//
////                Gson gson = new Gson();
////
////                String json =  gson.toJson(courseList);
////                TimetableFragment.setCourseJson(json);
////                Configuration.saveString("courseJson", json, context);
//
//
//                // this method may be called by another class
//                if(progress != null)
//                    progress.cancel();
//
//                // finish up
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        // re-initialize
////                        timetable_fall_fragment.refresh();
////                        timetable_winter_fragment.refresh();
////                        timetable_summer1_fragment.refresh();
////                        timetable_summer2_fragment.refresh();
////
////                        for (SwipeRefreshLayout swipeContainer : TimetableFragment.swipeContainers) {
////                            swipeContainer.setRefreshing(false);
////                        }
////                        TimetableFragment.updating = false;
////                    }
////                });
//            }
//
//            @Override
//            public void failure(Exception e) {
//                e.printStackTrace();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage(e.getMessage())
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(final DialogInterface dialog, int id) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        dialog.cancel();
//                                        // this method may be called by another class
//                                        if(progress != null)
//                                            progress.cancel();
//                                        for (SwipeRefreshLayout swipeContainer : TimetableFragment.swipeContainers) {
//                                            swipeContainer.setRefreshing(false);
//                                        }
//                                        TimetableFragment.updating = false;
//                                    }
//                                });
//                            }
//                        });
//                // clear the password if the user input a wrong password
//                if(e.getMessage().contains("Username"))
//                    UserInfo.clearPassword(context);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        builder.create().show();
//                    }
//                });
//
//            }
//        });
//
//
//    }

//    public void setToDefaultTimetable(){
//        // reset to default timetable
//        String defaultTimetable = Configuration.loadString("defaultTimetable", activity);
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navi_menu = navigationView.getMenu();
//
//        System.out.println("setToDefaultTimetable to " + defaultTimetable);
//        if (defaultTimetable.equals("0") || defaultTimetable.isEmpty()) {
//            fragmentManager.beginTransaction().replace(R.id.flContent, timetable_fall_fragment).commit();
//            setTitle("Fall Timetable");
//            navi_menu.getItem(0).setChecked(true);
//        }
//        else if(defaultTimetable.equals("1")) {
////            fragmentManager.beginTransaction().replace(R.id.flContent, timetable_winter_fragment).commit();
////            setTitle("Winter Timetable");
////            navi_menu.getItem(2).setChecked(true);
//            fragmentManager.beginTransaction().replace(R.id.flContent, scheduleListFragment).commit();
//            setTitle("Winter Timetable");
//            navi_menu.getItem(0).setChecked(true);
//        }
//    }

    @Override
    public void setUserInfo(String... data) {

        if (data[0] != null) {
            Gson gson = new Gson();
            Student student = gson.fromJson(data[0], Student.class);

            View headerView = navigationView.getHeaderView(0);
            TextView userName = headerView.findViewById(R.id.drawer_header_user_name);
            userName.setText(student.getShortName() == null ? "Anónimo" : student.getShortName());
            TextView course = headerView.findViewById(R.id.drawer_header_course);
            course.setText(student.getCourse());
//        TextView period = headerView.findViewById(R.id.drawer_header_period);
//        period.setText(data[2] + "°");
        }
    }

    @Override
    public void runLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void runSplashScreen() {
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
    }

    @Override
    public void setMainActivity(String s) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navi_menu = navigationView.getMenu();
        selectTitleAndFragment(Integer.parseInt(s));
    }

    private void selectTitleAndFragment(int index) {
//        setTitle(arrayTitles[index]);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, arrayFragments[index], arrayTitles[index]);
        fragmentTransaction.commitAllowingStateLoss();
        navi_menu.getItem(index).setChecked(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setMainActivity(presenter.getMainActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.loadPreferences();

//        registerReceiver(wifiScannReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        toolbar.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out));
        toolbar.setTitle(title);
        toolbar.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (navi_menu.getItem(0).isChecked()) {
            savedInstanceState.putString("title", "0");
        } else if (navi_menu.getItem(1).isChecked()) {
            savedInstanceState.putString("title", "1");
        } else if (navi_menu.getItem(2).isChecked()) {
            savedInstanceState.putString("title", "2");
        } else if (navi_menu.getItem(3).isChecked()) {
            savedInstanceState.putString("title", "3");
        } else if (navi_menu.getItem(4).isChecked()) {
            savedInstanceState.putString("title", "4");
        } else if (navi_menu.getItem(5).isChecked()) {
            savedInstanceState.putString("title", "5");
        }

        savedInstanceState.putLong("isSplash", System.currentTimeMillis());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.getString("title") != null) {
            selectTitleAndFragment(Integer.parseInt(savedInstanceState.getString("title")));
        }

        Long timeDiff = System.currentTimeMillis() - savedInstanceState.getLong("isSplash");
        if (timeDiff > 20L) runSplashScreen();

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.dropView();
        String t = String.valueOf(System.currentTimeMillis());
        presenter.savePreferences(t);

//        unregisterReceiver(wifiScannReceiver);
        super.onDestroy();
    }

    private void turnOffAlarm() {
        Intent intent = new Intent("FACAPE_MOBILE_ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "turn off alarm");
    }
}
