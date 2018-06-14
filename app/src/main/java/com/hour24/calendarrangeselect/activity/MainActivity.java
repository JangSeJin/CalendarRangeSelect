package com.hour24.calendarrangeselect.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.adapter.CalendarViewPagerAdapter;
import com.hour24.calendarrangeselect.adapter.DayWeekAdapter;
import com.hour24.calendarrangeselect.databinding.ActivityMainBinding;
import com.hour24.calendarrangeselect.model.ModelCalendar;
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
    private TextView mStartDay;
    private TextView mEndDay;
    private RecyclerView mDayWeekRecyclerView;
    private ViewPager mCalendarViewPager;

    // Adapter
    private DayWeekAdapter mDayWeekAdapter;
    private CalendarViewPagerAdapter mCalendarViewPagerAdapter;

    private ArrayList<ModelCalendar> mCalendarList;
    private int mMonthCount = 1 * 12; // 10년(120개월)
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
        mStartDay = (TextView) findViewById(R.id.start_day); // 시작일
        mEndDay = (TextView) findViewById(R.id.end_day); // 종료일
        mDayWeekRecyclerView = (RecyclerView) findViewById(R.id.day_week_recyclerview); // 일요일 ~ 토요일
        mCalendarViewPager = (ViewPager) findViewById(R.id.calendar_viewpager); // 달력

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

        // day
        // dayList 를 현재 월 기준 120개월 만들어 View Pager 삽입 (10년)
        mCalendarList = getCalendarList();
        mCalendarViewPagerAdapter = new CalendarViewPagerAdapter(getSupportFragmentManager(), mCalendarList);
        mCalendarViewPager.setAdapter(mCalendarViewPagerAdapter);

    }

    private void initEventListener() {
        // view page scroll
        mCalendarViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mBinding.setModel(mCalendarList.get(position));
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
                    mCalendarViewPager.setCurrentItem(mCurrentPosition - 1, true);
                }
                break;

            case R.id.next_month: // 다음달
                if (mCalendarList.size() > mCurrentPosition) {
                    mCalendarViewPager.setCurrentItem(mCurrentPosition + 1, true);
                }
                break;

            case R.id.current_month: // 현재달
                break;
        }
    }

    public ArrayList<ModelCalendar> getCalendarList() {

        // 1. 현재월 구함
        // 2. 현재월 기준 120개월 세팅 (10년)

        // 기준 년, 월 세팅
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        calendar.set(curYear, curMonth, 1);

        // View Pager 를 세팅하기 위한 작업
        ArrayList<ModelCalendar> calendarList = new ArrayList<>();
        for (int i = 0; i < mMonthCount; i++) {

            ModelCalendar modelCalendar = new ModelCalendar();
            modelCalendar.setYear(calendar.get(Calendar.YEAR));
            modelCalendar.setMonth(calendar.get(Calendar.MONTH));
            modelCalendar.setStartDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)); // 1일 시작 요일
            calendarList.add(modelCalendar);

            // 다음달
            calendar.add(Calendar.MONTH, 1);
        }

        // 현재 월 세팅
        mBinding.setModel(calendarList.get(0));

        return calendarList;
    }
}