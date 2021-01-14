package com.gurpster.facapemobile;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gurpster.facapemobile.data.entity.Grades;
import com.gurpster.facapemobile.view.adapter.GradeAdapter;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.model.Grade;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class GradeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private GradeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        mAdapter = new GradeAdapter(staticData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViews(){
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);

        return true;
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

                if(!newText.isEmpty())
                    mAdapter.getFilter().filter(newText);

                return true;
            }
        });
    }

    private List<Grades> staticData() {
        List<Grades> grades = new ArrayList<>();

        grades.add(new Grades("Teoria dos Grafos", "Matriculado", "5", "0.0", "0.0", "0.0", "0.0", "0.0", "Aprovado"));
        grades.add(new Grades("Banco de Dados 2", "Matriculado", "0", "0.0", "0.0", "0.0", "0.0", "0.0", "Aprovado"));
        grades.add(new Grades("Arquitetura de Computadores", "Matriculado", "11", "0.0", "0.0", "0.0", "0.0", "0.0", "Aprovado"));
        grades.add(new Grades("Computação Grafica", "Matriculado", "1", "0.0", "0.0", "0.0", "0.0", "0.0", "Aprovado"));
        grades.add(new Grades("Projeto de Sistemas", "Matriculado", "2", "0.0", "0.0", "0.0", "0.0", "0.0", "Aprovado"));

        return grades;
    }

}