package com.gurpster.facapemobile.view.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.view.adapter.GradeAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class HistoricFragment extends DaggerFragment implements HistoricContract.View {

    private static ProgressDialog dialog;

    @Inject HistoricContract.Presenter presenter;

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyView;
    private GradeAdapter mAdapter;

    @Inject
    public HistoricFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_historic, container, false);
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

    private void initViews(View view) {
        mEmptyView = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.recycler_view_historic);
        swipeContainer = view.findViewById(R.id.fragment_historic_swipe_refresh);
    }

    private void init() {
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
            }
        });
//        dialog.show();
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void onLoadDataSuccess() {
        getActivity().setTitle(getContext().getResources().getString(R.string.title_historics));
        dialog.dismiss();
    }

    @Override
    public void onLoadDataError() {
        dialog.dismiss();
    }
}
