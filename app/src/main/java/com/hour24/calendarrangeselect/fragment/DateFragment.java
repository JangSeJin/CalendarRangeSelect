package com.hour24.calendarrangeselect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.adapter.DateAdapter;
import com.hour24.calendarrangeselect.model.ModelDate;

import java.util.ArrayList;


public class DateFragment extends Fragment {

    private String TAG = DateFragment.class.getSimpleName();
    private Context mContext;

    // Views
    private RecyclerView mDateRecyclerView;

    // Adapter
    private DateAdapter mDateAdapter;

    // Model
    private ModelDate mDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        getDate();
        initLayout(view);
        initVariable();

    }

    public void getDate() {
        try {
            if (getArguments() != null) {
                mDate = (ModelDate) getArguments().getSerializable(ModelDate.class.getSimpleName());
                Log.d(TAG, "year : " + mDate.getYear());
                Log.d(TAG, "month : " + (mDate.getMonth() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initLayout(View view) {
        mDateRecyclerView = (RecyclerView) view.findViewById(R.id.date_recyclerview);
        mDateRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        mDateRecyclerView.setItemAnimator((new DefaultItemAnimator()));
    }

    public void initVariable() {
        mDateAdapter = new DateAdapter(mContext, mDate);
        mDateRecyclerView.setAdapter(mDateAdapter);
    }

    public void notifyDataSetChanged(ModelDate date) {
        if (mDateAdapter != null) {
            mDate = date;
            mDateAdapter.notifyDataSetChanged();
        }
    }
}