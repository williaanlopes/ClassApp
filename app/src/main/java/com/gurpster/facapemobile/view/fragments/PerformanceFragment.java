package com.gurpster.facapemobile.view.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.view.adapter.GradeAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

@ActivityScoped
public class PerformanceFragment extends DaggerFragment implements PerformanceContract.View {

    @Inject
    PerformanceContract.Presenter presenter;

    private RecyclerView mRecyclerView;
    private LinearLayout noGrades;
    private GradeAdapter mAdapter;

    private PieChart mPieChart;
    private BarChart mBarChart;

    private Typeface tf;

    @Inject
    public PerformanceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_performance, container, false);
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

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mPieChart = (PieChart) view.findViewById(R.id.performance_piechart);

        // create a new chart object
//        mBarChart = new BarChart(getActivity());
//        mBarChart.getDescription().setEnabled(false);
//        mBarChart.setOnChartGestureListener(this);

//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
//        mv.setChartView(mBarChart); // For bounds control
//        mBarChart.setMarker(mv);

//        mBarChart.setDrawGridBackground(false);
//        mBarChart.setDrawBarShadow(false);

//        mBarChart.setData(generateBarData(1, 20000, 12));

//        Legend l = mBarChart.getLegend();
//        l.setTypeface(tf);

//        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        mBarChart.getAxisRight().setEnabled(false);

//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setEnabled(false);

        // programatically add the chart
//        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
//        parent.addView(mBarChart);

        noGrades = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.card_recycler_view);

    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return PieData
     */
    private PieData generatePieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<>();
        entries1.add(new PieEntry((float) 94.00, "Cursadas"));
        entries1.add(new PieEntry((float) 1.00, "Não\nCursadas"));
        entries1.add(new PieEntry((float) 5.00, "Restantes"));
//        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Cursadas"));
//        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Não\nCursadas"));
//        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Restantes"));

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        d.setValueTypeface(tf);

        return d;
    }

    public final int[] COLORS = {
            Color.rgb(0, 89, 255),
            Color.rgb(234, 54, 57),
            Color.rgb(24, 204, 20),
            Color.rgb(140, 234, 255),
            Color.rgb(255, 247, 140)
    };

    private void init() {

//        mRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
//        mAdapter = new GradeAdapter(itemListener);
//        mRecyclerView.setAdapter(mAdapter);

        mPieChart.getDescription().setEnabled(false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        mPieChart.setCenterTextTypeface(tf);
        mPieChart.setCenterText(generateCenterText());
        mPieChart.setCenterTextSize(10f);
        mPieChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        mPieChart.setHoleRadius(45f);
        mPieChart.setTransparentCircleRadius(50f);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        mPieChart.setData(generatePieData());

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

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Revenues\nQuarters 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }
}
