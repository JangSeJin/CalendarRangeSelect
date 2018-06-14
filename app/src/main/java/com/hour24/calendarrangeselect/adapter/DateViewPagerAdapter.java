package com.hour24.calendarrangeselect.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hour24.calendarrangeselect.fragment.DateFragment;
import com.hour24.calendarrangeselect.model.ModelDate;

import java.util.ArrayList;

/**
 * Created by N16326 on 2018. 6. 1..
 */

public class DateViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ModelDate> mMonthList;

    public DateViewPagerAdapter(FragmentManager fragmentManager, ArrayList<ModelDate> monthList) {
        super(fragmentManager);
        this.mMonthList = monthList;
    }

    @Override
    public int getCount() {
        return mMonthList.size();
    }

    @Override
    public Fragment getItem(int position) {

        ModelDate date = mMonthList.get(position);

        Bundle args = new Bundle();
        args.putSerializable(ModelDate.class.getSimpleName(), date);

        Fragment fragment = new DateFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
