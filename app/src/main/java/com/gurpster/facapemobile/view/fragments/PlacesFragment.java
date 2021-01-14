package com.gurpster.facapemobile.view.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Place;
import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.view.activities.PlaceActivity;
import com.gurpster.facapemobile.view.adapter.MessageAdapter;
import com.gurpster.facapemobile.view.adapter.PlaceAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PlacesFragment extends DaggerFragment implements PlaceContract.View {

    private static ProgressDialog dialog;

    @Inject
    PlaceContract.Presenter presenter;

    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private LinearLayout root;
    private LinearLayout noRoot;

    @Inject
    public PlacesFragment() {
    }

    public static PlacesFragment newInstance() {
        return new PlacesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_place_, container, false);
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

        root = view.findViewById(R.id.fragment_root);
        noRoot = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.card_recycler_view);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadData();
    }

    private void init() {

        dialog.show();

        PlaceAdapter.PlaceActions placeActions = new PlaceAdapter.PlaceActions() {
            @Override
            public void actionSearch(boolean result) {
                if(!result) Toast.makeText(getContext(), "Nada encontrado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void actionCall(String phone) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:87"+phone));
                startActivity(intent);
            }

            @Override
            public void actionMail(String mail) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { mail });
                startActivity(Intent.createChooser(intent, "Email via..."));
            }
        };

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PlaceAdapter(placeActions);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater manuInflater) {
        super.onCreateOptionsMenu(menu, manuInflater);
        menu.clear();
    }

    @Override
    public void show(List<Place> places) {
        dialog.dismiss();
        getActivity().setTitle(getContext().getResources().getString(R.string.title_contacts));
        mAdapter.setList(places);
        noRoot.setVisibility(View.GONE);
        root.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView() {
        dialog.dismiss();
        noRoot.setVisibility(View.VISIBLE);
        root.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
