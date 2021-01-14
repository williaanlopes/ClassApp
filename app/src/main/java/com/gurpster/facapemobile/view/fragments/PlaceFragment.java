package com.gurpster.facapemobile.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.view.adapter.PlaceAdapter;
import com.gurpster.facapemobile.view.adapter.PlaceMapAdapter;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Williaan Lopes (d3x773r) on 21/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class PlaceFragment extends Fragment {

    private Toolbar toolbar;
    private int index;

//    @Inject
    public PlaceFragment() { }

    public static PlaceFragment newInstance(int i) {
        PlaceFragment f = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt("INDEX", i);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) index = args.getInt("INDEX", 0);

        ViewCompat.setElevation(getView(), 10f);
        ViewCompat.setElevation(toolbar, 4f);

        toolbar.setTitle(PlaceMapAdapter.PAGE_TITLES[index]);
        toolbar.inflateMenu(R.menu.menu_map);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
