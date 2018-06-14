package com.hour24.calendarrangeselect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hour24.calendarrangeselect.fragment.CalendarFragment;
import com.hour24.calendarrangeselect.model.ModelCalendar;

import java.util.ArrayList;

/**
 * Created by N16326 on 2018. 6. 1..
 */

public class CalendarViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ModelCalendar> mCalendarList;

    public CalendarViewPagerAdapter(FragmentManager fragmentManager, ArrayList<ModelCalendar> calendarList) {
        super(fragmentManager);
        this.mCalendarList = calendarList;
    }

    @Override
    public int getCount() {
        return mCalendarList.size(); // 120개월 (10년)
    }

    @Override
    public Fragment getItem(int position) {

        ModelCalendar calendar = mCalendarList.get(position);

        Bundle args = new Bundle();
        args.putSerializable(ModelCalendar.class.getSimpleName(), calendar);

        Fragment fragment = new CalendarFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
