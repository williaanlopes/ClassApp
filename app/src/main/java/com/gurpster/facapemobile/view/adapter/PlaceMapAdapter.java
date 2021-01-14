package com.gurpster.facapemobile.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

//import com.github.nitrico.mapviewpager.MapViewPager;
//import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.gurpster.facapemobile.view.MapViewPager;
import com.gurpster.facapemobile.view.fragments.PlaceFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 21/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class PlaceMapAdapter extends MapViewPager.MultiAdapter {

    public static final String[] PAGE_TITLES = { "England", "France", "Spain",
            "Portugal", "Italy", "Belgium" };

    public static final String[] ENGLAND_TITLES = { "London" };
    public static final String[] FRANCE_TITLES = { "Paris" };
    public static final String[] SPAIN_TITLES = { "Barcelona", "Madrid", "Valencia" };
    public static final String[] PORTUGAL_TITLES = { };
    public static final String[] ITALY_TITLES = { "Milan", "Rome" };
    public static final String[] BELGIUM_TITLES = { "Brussels" };

    public static float zoom = 6f;

    public static final CameraPosition LONDON
            = CameraPosition.fromLatLngZoom(new LatLng(51.5287352,-0.381784), zoom);
    public static final CameraPosition PARIS
            = CameraPosition.fromLatLngZoom(new LatLng(48.859,2.2074722), zoom);
    public static final CameraPosition BARCELONA
            = CameraPosition.fromLatLngZoom(new LatLng(41.3948976,2.0787274), zoom);
    public static final CameraPosition MADRID
            = CameraPosition.fromLatLngZoom(new LatLng(40.4381311,-3.8196227), zoom);
    public static final CameraPosition VALENCIA
            = CameraPosition.fromLatLngZoom(new LatLng(39.4079665,-0.5015975), zoom);
    public static final CameraPosition MILAN
            = CameraPosition.fromLatLngZoom(new LatLng(45.4628329,9.107692), zoom);
    public static final CameraPosition ROME
            = CameraPosition.fromLatLngZoom(new LatLng(41.9102415,12.3959121), zoom);
    public static final CameraPosition BRUSSELS
            = CameraPosition.fromLatLngZoom(new LatLng(50.8550625,4.3053499), zoom);

    private LinkedList<CameraPosition> england;
    private LinkedList<CameraPosition> france;
    private LinkedList<CameraPosition> spain;
    private LinkedList<CameraPosition> portugal;
    private LinkedList<CameraPosition> italy;
    private LinkedList<CameraPosition> belgium;

    public PlaceMapAdapter(FragmentManager fm) {
        super(fm);

        // camera positions
        england = new LinkedList<>();
        france = new LinkedList<>();
        spain = new LinkedList<>();
        portugal = new LinkedList<>();
        italy = new LinkedList<>();
        belgium = new LinkedList<>();

        england.add(LONDON);
        france.add(PARIS);
        spain.add(BARCELONA);
        spain.add(MADRID);
        spain.add(VALENCIA);
        italy.add(MILAN);
        italy.add(ROME);
        belgium.add(BRUSSELS);
    }

    @Override
    public int getCount() {
        return PAGE_TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

    @Override
    public String getMarkerTitle(int page, int position) {
        switch (page) {
            case 0: return ENGLAND_TITLES[position];
            case 1: return FRANCE_TITLES[position];
            case 2: return SPAIN_TITLES[position];
            case 3: return PORTUGAL_TITLES[position];
            case 4: return ITALY_TITLES[position];
            case 5: return BELGIUM_TITLES[position];
        }
        return null;
    }

    @Override
    public List<CameraPosition> getCameraPositions(int page) {
        switch (page) {
            case 0: return england;
            case 1: return france;
            case 2: return spain;
            case 3: return portugal;
            case 4: return italy;
            case 5: return belgium;
        }
        return null;
    }
}
