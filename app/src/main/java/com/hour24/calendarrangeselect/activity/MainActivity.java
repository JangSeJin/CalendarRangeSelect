package com.hour24.calendarrangeselect.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.ColorSpace;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.adapter.DateViewPagerAdapter;
import com.hour24.calendarrangeselect.adapter.DayWeekAdapter;
import com.hour24.calendarrangeselect.databinding.ActivityMainBinding;
import com.hour24.calendarrangeselect.model.ModelDate;
import com.hour24.calendarrangeselect.model.ModelDayWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Context mContext;
    private ActivityMainBinding mBinding;

    // Views
    private Button mBackMonth;
    private Button mNextMonth;
    private TextView mCurrentMonth;
    private TextView mStartDate;
    private TextView mFinishedDate;
    private RecyclerView mDayWeekRecyclerView;
    private ViewPager mDateViewPager;

    // Adapter
    private DayWeekAdapter mDayWeekAdapter;
    private DateViewPagerAdapter mDateViewPagerAdapter;

    private ArrayList<ModelDate> mDateList;
    private int mMonthCount = 10 * 12; // 10년(120개월)
    private int mCurrentPosition = 0; // view page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = MainActivity.this;

        initLayout();
        initVariable();
        initEventListener();

    }

    private void initLayout() {

        mBackMonth = (Button) findViewById(R.id.back_month); // 이전달
        mNextMonth = (Button) findViewById(R.id.next_month); // 다음달
        mCurrentMonth = (TextView) findViewById(R.id.current_month); // 현재달
        mStartDate = (TextView) findViewById(R.id.started_date); // 시작일
        mFinishedDate = (TextView) findViewById(R.id.finished_date); // 종료일
        mDayWeekRecyclerView = (RecyclerView) findViewById(R.id.day_week_recyclerview); // 일요일 ~ 토요일
        mDateViewPager = (ViewPager) findViewById(R.id.date_viewpager); // 달력

        mDayWeekRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        mDayWeekRecyclerView.setItemAnimator((new DefaultItemAnimator()));

        View[] views = {mBackMonth, mNextMonth, mCurrentMonth};
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void initVariable() {

        // day week
        ModelDayWeek modelDayWeek = new ModelDayWeek();
        mDayWeekAdapter = new DayWeekAdapter(mContext, modelDayWeek.getDayWeekList());
        mDayWeekRecyclerView.setAdapter(mDayWeekAdapter);

        // date
        // dateList 를 현재 월 기준 120개월 만들어 View Pager 삽입 (10년)
        mDateList = getDateList();
        mDateViewPagerAdapter = new DateViewPagerAdapter(getSupportFragmentManager(), mDateList);
        mDateViewPager.setAdapter(mDateViewPagerAdapter);

    }

    private void initEventListener() {
        // view page scroll
        mDateViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mBinding.setModel(mDateList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_month: // 이전달
                if (mCurrentPosition > 0) {
                    mDateViewPager.setCurrentItem(mCurrentPosition - 1, true);
                }
                break;

            case R.id.next_month: // 다음달
                if (mDateList.size() > mCurrentPosition) {
                    mDateViewPager.setCurrentItem(mCurrentPosition + 1, true);
                }
                break;

            case R.id.current_month: // 현재달
                break;
        }
    }

    public ArrayList<ModelDate> getDateList() {

        // 1. 현재월 구함
        // 2. 현재월 기준 120개월 세팅 (10년)

        // 기준 년, 월 세팅
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        calendar.set(curYear, curMonth, 1);

        // View Pager 를 세팅하기 위한 작업
        ArrayList<ModelDate> monthList = new ArrayList<>();
        for (int i = 0; i < mMonthCount; i++) {

            // 년, 월 세팅
            ModelDate month = new ModelDate();
            month.setYear(calendar.get(Calendar.YEAR));
            month.setMonth(calendar.get(Calendar.MONTH));

            int startDate = calendar.get(Calendar.DAY_OF_WEEK);
            month.setStartDayOfWeek(startDate); // 1일 시작 요일

            //  시작 일 만큼 공백 만들어줌
            ArrayList<ModelDate> dateList = new ArrayList<>();
            for (int j = 0; j < startDate - 1; j++) {
                dateList.add(j, new ModelDate(ModelDate.Style.EMPTY));
            }

            // 일 세팅
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            for (int j = 1; j <= lastDate; j++) {

                // 일
                ModelDate date = new ModelDate();
                date.setDate(j);

                // Add Array List
                dateList.add(date);

            }
            month.setDateList(dateList);

            // Add Array List
            monthList.add(month);

            // 다음달
            calendar.add(Calendar.MONTH, 1);
        }

        // 현재 월 세팅
        mBinding.setModel(monthList.get(0));

        return monthList;
    }
}