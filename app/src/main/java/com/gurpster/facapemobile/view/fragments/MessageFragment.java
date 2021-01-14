package com.gurpster.facapemobile.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.view.adapter.MessageAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MessageFragment extends DaggerFragment implements MessageContract.View {

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    @Inject
    MessageContract.Presenter presenter;

    private LinearLayout root;
    private LinearLayout noRoot;

    @Inject
    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
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

        root = view.findViewById(R.id.fragment_message_root);
        noRoot = view.findViewById(R.id.empty_view);
        mRecyclerView = view.findViewById(R.id.card_recycler_view);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    private void init() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MessageAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater manuInflater) {
        super.onCreateOptionsMenu(menu, manuInflater);
        menu.clear();
    }

    @Override
    public void showMessages(List<String> messages) {
        getActivity().setTitle(getContext().getResources().getString(R.string.title_messages));
        mAdapter.setList(messages);
        noRoot.setVisibility(View.GONE);
        root.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView() {
        noRoot.setVisibility(View.VISIBLE);
        root.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
