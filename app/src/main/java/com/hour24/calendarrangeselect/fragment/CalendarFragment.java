package com.hour24.calendarrangeselect.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.model.ModelCalendar;


public class CalendarFragment extends Fragment {

    private String TAG = CalendarFragment.class.getSimpleName();

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        getViewCode();
        initLayout(view);
        initVariable();

    }


    public void getViewCode() {
        try {
            if (getArguments() != null) {
                ModelCalendar calendar = (ModelCalendar) getArguments().getSerializable(ModelCalendar.class.getSimpleName());
                Log.d(TAG, "year : " + calendar.getYear());
                Log.d(TAG, "month : " + (calendar.getMonth() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initLayout(View view) {

    }

    public void initVariable() {

    }
}