package com.gurpster.facapemobile.view.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.data.entity.Semester;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.util.InternetUtils;
import com.gurpster.facapemobile.util.ViewGroupUtils;
import com.gurpster.facapemobile.util.ViewUtils;
import com.gurpster.facapemobile.view.BackPressed;
import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.view.adapter.GradeAdapter;
import com.gurpster.facapemobile.model.Grade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.google.common.base.Preconditions.checkNotNull;

@ActivityScoped
public class GradeFragment extends DaggerFragment implements GradeContract.View {

    private static ProgressDialog dialog;

    @Inject
    GradeContract.Presenter presenter;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private LinearLayout noGrades;
    private GradeAdapter mAdapter;
    private Spinner spinner;
    private SearchView searchView;
    private ProgressDialog progress;

    private boolean isSearchViewActive = false;
    private int currentSelection = 0;

    @Inject
    public GradeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_grade, container, false);
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

        swipeContainer = view.findViewById(R.id.fragment_grade_swipe_refresh);
        noGrades = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.recycler_view_grades);

    }

    private void init() {

        dialog.show();

        progress = new ProgressDialog(getActivity());

        swipeContainer.setColorSchemeResources(
                R.color.belizeHole,
                R.color.colorPrimaryDark
        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if(InternetUtils.hasActiveInternetConnection()) {
//                    presenter.loadGrades(true);
//                } else {
//                    Toast.makeText(getContext(), "Dispositivo sem conexão com a internet!", Toast.LENGTH_LONG).show();
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        swipeContainer.setRefreshing(false);
//                    }
//                }, 6000);
            }
        });

        GradeItemListener itemListener = new GradeItemListener() {
            @Override
            public void onGradeClick(Grades clickedGrade) {
                Toast.makeText(getContext(), "item: " + clickedGrade.getSubject() + " clicked!", Toast.LENGTH_SHORT).show();
            }
        };

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

//        mAdapter = new GradeAdapter(new ArrayList<Grades>(0));
        mAdapter = new GradeAdapter(itemListener);
        mRecyclerView.setAdapter(mAdapter);

//        List<String> list = new ArrayList<>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);

//        swipeContainer.setEnabled(!isSearchViewActive);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        spinner = (Spinner) menu.findItem(R.id.spinner).getActionView();
        presenter.loadData();

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.animate();
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchViewActive = true;
                swipeContainer.setEnabled(false);
                spinner.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchViewActive = false;
                spinner.setVisibility(View.VISIBLE);
                swipeContainer.setEnabled(true);
                return false;
            }
        });

    }

    private void spinner(Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentSelection != i) {
                    String s = adapterView.getItemAtPosition(i).toString();
                    currentSelection = i;
                    presenter.loadOldGrades(Integer.parseInt(s.split("\\.")[0]), Integer.parseInt(s.split("\\.")[1]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadGrades(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showGrades(List<Grades> grades) {
        dialog.dismiss();
        getActivity().setTitle(getContext().getResources().getString(R.string.title_grades));
        if(grades.isEmpty()) {
            noGrades.setVisibility(View.VISIBLE);
            swipeContainer.setVisibility(View.GONE);
        } else {
            mAdapter.replaceData(grades);
            swipeContainer.setVisibility(View.VISIBLE);
            noGrades.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSemesters(List<Semester> semesters) {

        List<String> list = new ArrayList<>();
        list.add("2017.2");
        list.add("2017.1");
        list.add("2016.2");
        list.add("2016.1");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner(spinner);
    }

    @Override
    public void stopRefresh() {
        if(swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        progress.setTitle("Loading");
        progress.setMessage("Retrieving data...");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void stopLoading() {
        if(progress.isShowing())
            progress.dismiss();
    }

    @Override
    public void showError(String message) {
        dialog.dismiss();
//        ViewUtils.showSnackbar(, message, ViewUtils.SNACKBAR_ERROR, true);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public interface GradeItemListener {
        void onGradeClick(Grades clickedGrade);
    }
}
