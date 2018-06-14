package com.hour24.calendarrangeselect.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.adapter.DayWeekAdapter;
import com.hour24.calendarrangeselect.model.ModelDayWeek;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Context mContext;

    // Views
    private Button mBackMonth;
    private Button mNextMonth;
    private TextView mCurrentMonth;
    private TextView mStartDay;
    private TextView mEndDay;
    private RecyclerView mDayWeekRecyclerview;
    private ViewPager mCalendarViewpager;

    // Adapter
    private DayWeekAdapter mDayWeekAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        initLayout();
        initVariable();

    }

    private void initLayout() {

        mBackMonth = (Button) findViewById(R.id.back_month); // 이전달
        mNextMonth = (Button) findViewById(R.id.next_month); // 다음달
        mCurrentMonth = (TextView) findViewById(R.id.current_month); // 현재달
        mStartDay = (TextView) findViewById(R.id.start_day); // 시작일
        mEndDay = (TextView) findViewById(R.id.end_day); // 종료일
        mDayWeekRecyclerview = (RecyclerView) findViewById(R.id.day_week_recyclerview); // 일요일 ~ 토요일
        mCalendarViewpager = (ViewPager) findViewById(R.id.calendar_viewpager); // 달력

        mDayWeekRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 7));
        mDayWeekRecyclerview.setItemAnimator((new DefaultItemAnimator()));

        View[] views = {mBackMonth, mNextMonth, mCurrentMonth};
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void initVariable() {

        ModelDayWeek modelDayWeek = new ModelDayWeek();
        mDayWeekAdapter = new DayWeekAdapter(mContext, modelDayWeek.getDayWeekList());
        mDayWeekRecyclerview.setAdapter(mDayWeekAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_month: // 이전달
                break;

            case R.id.next_month: // 다음달
                break;

            case R.id.current_month: // 현재달
                break;
        }
    }
}