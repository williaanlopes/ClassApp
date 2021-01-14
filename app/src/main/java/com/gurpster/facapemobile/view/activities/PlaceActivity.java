package com.gurpster.facapemobile.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Place;
import com.gurpster.facapemobile.util.MapUtils;
import com.gurpster.facapemobile.view.MapViewPager;
import com.gurpster.facapemobile.view.adapter.PlaceAdapter;
import com.gurpster.facapemobile.view.adapter.PlaceMapAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class PlaceActivity extends DaggerAppCompatActivity implements PlaceContract_.View, MapViewPager.Callback {

    private ViewPager viewPager;
    private MapViewPager mvp;
    private LinearLayout rootList;
    private FrameLayout rootMap;
    private PlaceAdapter mAdapter;
    private SupportMapFragment map;

    @Inject
    PlaceContract_.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PlaceAdapter.PlaceActions placeActions = new PlaceAdapter.PlaceActions() {
            @Override
            public void actionSearch(boolean result) {
                if(!result) Toast.makeText(PlaceActivity.this, "Nada encontrado!", Toast.LENGTH_SHORT).show();
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

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageMargin(MapUtils.dp(this, 18));
        MapUtils.setMargins(viewPager, 0, 0, 0, MapUtils.getNavigationBarHeight(this));

        mvp = new MapViewPager.Builder(this)
                .mapFragment(map)
                .viewPager(viewPager)
                .position(2)
                .adapter(new PlaceMapAdapter(getSupportFragmentManager()))
                .callback(this)
                .build();

        rootList = findViewById(R.id.root_list);
        rootMap = findViewById(R.id.root_map);
        RecyclerView mRecyclerView = findViewById(R.id.place_type_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new PlaceAdapter(placeActions);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.takeView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);

        MenuItem item1 = menu.findItem(R.id.action_list);
        item1.setVisible(false);
        MenuItem item2 = menu.findItem(R.id.action_map);
        item2.setVisible(true);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_map_search).getActionView();
        searchView.animate();
        search(searchView);

        return true;
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
    protected void onResume() {
        super.onResume();
        presenter.selectView();
    }

    @Override
    public void onMapViewPagerReady() {
        final GoogleMap mMap = mvp.getMap();
//        mMap.setMyLocationEnabled(true);
//        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//
//            @Override
//            public void onMyLocationChange(Location location) {
//                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("It's Me!"));
//            }
//        });
        mMap.setPadding(
                0,
                MapUtils.dp(this, 40),
                MapUtils.getNavigationBarWidth(this),
                viewPager.getHeight() + MapUtils.getNavigationBarHeight(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void showListViewType() {
        mAdapter.setList(placeFactory());
        rootMap.setVisibility(View.GONE);
        rootList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMapViewType() {
        rootList.setVisibility(View.GONE);
        rootMap.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    private List<Place> placeFactory() {
        List<Place> list = new ArrayList<>();
        list.add(new Place("Central de Atendimento", "", "3866 - 3200", "ouvidoria@facape.br", "One Name", 1d, 1d));
        list.add(new Place("Teste 3","", "3333 - 3333", "example@facape.br", "One Name", 1d, 1d));
        list.add(new Place("Teste 4","", "3333 - 3333", "example@facape.br", "One Name", 1d, 1d));
        list.add(new Place("Teste 5", "", "3333 - 3333", "example@facape.br", "One Name", 1d, 1d));
        return list;
    }
}
