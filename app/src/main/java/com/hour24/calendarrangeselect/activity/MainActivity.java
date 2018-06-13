package com.hour24.calendarrangeselect.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button mBackMonth;
    private Button mNextMonth;
    private TextView mCurrentMonth;
    private TextView mStartDay;
    private TextView mEndDay;
    private RecyclerView mDayWeekRecyclerview;
    private ViewPager mCalendarViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackMonth = (Button) findViewById(R.id.back_month); // 이전달
        mNextMonth = (Button) findViewById(R.id.next_month); // 다음달
        mCurrentMonth = (TextView) findViewById(R.id.current_month); // 현재달
        mStartDay = (TextView) findViewById(R.id.start_day); // 시작일
        mEndDay = (TextView) findViewById(R.id.end_day); // 종료일
        mDayWeekRecyclerview = (RecyclerView) findViewById(R.id.day_week_recyclerview); // 일요일 ~ 토요일
        mCalendarViewpager = (ViewPager) findViewById(R.id.calendar_viewpager); // 달력

        View[] views = {mBackMonth, mNextMonth, mCurrentMonth};
        for (View view : views) {
            view.setOnClickListener(this);
        }

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