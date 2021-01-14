package com.gurpster.facapemobile.view.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.entity.Debt;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.listener.OnDebtClickListener;
import com.gurpster.facapemobile.view.activities.DebtWebViewActivity;
import com.gurpster.facapemobile.view.adapter.DebtAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by Williaan Lopes (d3x773r) on 25/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@ActivityScoped
public class DebtFragment extends DaggerFragment implements DebtContract.View {

    @Inject DebtContract.Presenter presenter;

    private static ProgressDialog dialog;

    private LinearLayout root;
    private LinearLayout noRoot;
    private Button refreshView;
    private RecyclerView mRecyclerView;
    private DebtAdapter mAdapter;

    @Inject
    public DebtFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_debt, container, false);
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
        root = view.findViewById(R.id.fragment_debt_list);
        noRoot = view.findViewById(R.id.empty_view);
        refreshView = view.findViewById(R.id.refresh_view);
        mRecyclerView = view.findViewById(R.id.recycler_view_debts);
    }

    private void init() {

        dialog.show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        OnDebtClickListener debtClick = new OnDebtClickListener() {
            @Override
            public void onItemClick(Debt debt) {
            }

            @Override
            public void onLongItemClick(Debt debt) {
            }

            @Override
            public void onDownloadClick(Debt debt) {
//                presenter.downloadFile(debt);
                Toast.makeText(getContext(), "Debt " + debt.getType() + " Clicked!", Toast.LENGTH_SHORT).show();
            }
        };

        mAdapter = new DebtAdapter(getContext(), debtClick);
        mRecyclerView.setAdapter(mAdapter);

        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void messageDownload(String message) {
        Toast.makeText(getActivity(), message, //To notify the Client that the file is being downloaded
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showList(List<Debt> debts) {
        dialog.dismiss();
        getActivity().setTitle(getContext().getResources().getString(R.string.title_debts));
        noRoot.setVisibility(View.GONE);
        root.setVisibility(View.VISIBLE);
        mAdapter.setList(debtsFactory());
    }

    @Override
    public void showWebView() {
        dialog.dismiss();
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
    public void stopRefresh() {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    private List<Debt> debtsFactory() {
        List<Debt> list = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            list.add(new Debt("Mensalidade", 345.75d, 315.25d, 2.75d,"30/05/18", "10491.03359 37900.000003 00004.576450 5 75410000039959"));
        return list;
    }
}
