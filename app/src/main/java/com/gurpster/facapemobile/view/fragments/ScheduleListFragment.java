package com.gurpster.facapemobile.view.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Schedule;
import com.gurpster.facapemobile.listener.OnItemClickListener;
import com.gurpster.facapemobile.stickyheaders.StickyHeaderLayoutManager;
import com.gurpster.facapemobile.view.adapter.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

//import org.zakariya.stickyheaders.StickyHeaderLayoutManager;


public class ScheduleListFragment extends DaggerFragment implements ScheduleListContract.View, OnItemClickListener {

    private static ProgressDialog dialog;

    @Inject
    ScheduleListContract.Presenter presenter;

//    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;
    private ScheduleAdapter mAdapter;
    private List<Schedule> schedules = new ArrayList<>();

    @Inject
    public ScheduleListFragment() {
    }

    public static ScheduleListFragment newInstance() {
        return new ScheduleListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getActivity().getResources().getString(R.string.places_load_data_dialog));
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initViews(View view) {

//        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.fragment_grade_swipe_refresh);
        mEmptyView = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.recycler_view_schedule_list);

    }

    private void init() {

//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(getContext(), "Refresh!", Toast.LENGTH_SHORT).show();
//            }
//        });

        mAdapter = new ScheduleAdapter(this);
//        mAdapter.setSubjects(new Schedule().generateScheduleItens());
        mRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater manuInflater) {
        super.onCreateOptionsMenu(menu, manuInflater);
        menu.clear();
        manuInflater.inflate(R.menu.menu_schedule_type, menu);

//        MenuItem item1 = menu.findItem(R.id.schedule_perspective_two);
//        item1.setVisible(false);
//        MenuItem item2 = menu.findItem(R.id.schedule_perspective_one);
//        item2.setVisible(true);
        MenuItem item3 = menu.findItem(R.id.search);
        item3.setVisible(true);

        SearchManager searchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) item3.getActionView();
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getActivity().getComponentName()) : null);
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

//        if (id == R.id.schedule_perspective_one) {
//
//            // Create new fragment and transaction
//            TimetableFragment newFragment = new TimetableFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack if needed
//            transaction.replace(R.id.flContent, newFragment);
//            transaction.addToBackStack(null);
//            // Commit the transaction
//            transaction.commit();
//
//            return true;
//        }
//        } else if (id == R.id.menu_sort_normal) {
//            item.setCheckable(true);
//            return true;
//        } else if (id == R.id.menu_sort_invert) {
//            item.setCheckable(true);
//            return true;
//        } else if (id == R.id.menu_sort_by_current_day) {
//            item.setCheckable(true);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(Schedule schedule) {
        Toast.makeText(getContext(), "Item click: " + schedule.getSubjectName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        intent.putExtra("id", subject.getId());
//        intent.putExtra("name", subject.getName());
//        startActivity(intent, SubjectInfo.class);
    }

    @Override
    public void onLongItemClick(Schedule schedule) {
        Toast.makeText(getContext(), "Item long click: " + schedule.getSubjectName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getContext(), SubjectInfoActivity.class);
//        intent.putExtra("id", 0);
//        intent.putExtra("name", subject.getName());
//        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadSchedules();
    }

    private void tuto() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        final TapTargetSequence sequence = new TapTargetSequence(getActivity())
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forToolbarNavigationIcon(toolbar, "This is the back button").id(1),

                        // Likewise, this tap target will target the search button
                        TapTarget.forToolbarMenuItem(toolbar, R.id.schedule_perspective_one, "Aqui você pode mudar a perspectiva", "Escolha entre uma lista ou grade")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2)

                        // You can also target the overflow button in your toolbar
//                        TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(")
//                                .id(3),

                        // This tap target will target our droid buddy at the given target rect
//                        TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
//                                .cancelable(false)
////                                .icon(droid)
//                                .id(4)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
//                        ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
//                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
//                                .setTitle("Uh oh")
//                                .setMessage("You canceled the sequence")
//                                .setPositiveButton("Oops", null).show();
//                        TapTargetView.showFor(dialog,
//                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
//                                        .cancelable(false)
//                                        .tintTarget(false), new TapTargetView.Listener() {
//                                    @Override
//                                    public void onTargetClick(TapTargetView view) {
//                                        super.onTargetClick(view);
//                                        dialog.dismiss();
//                                    }
//                                });
                    }
                });

        sequence.start();
    }

    @Override
    public void onDestroy() {
        presenter.dropView();
        super.onDestroy();
    }

    @Override
    public void showSchedules(final List<Schedule> schedules, final int i) {
        dialog.dismiss();
        getActivity().setTitle(getContext().getResources().getString(R.string.title_schedule));
        if(schedules.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    mAdapter.setList(schedules, i);
                }
            }, 300);
        }
    }

    @Override
    public void configure(int i) {
        mAdapter.sort(i);
    }
}
