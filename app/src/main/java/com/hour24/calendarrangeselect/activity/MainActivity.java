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
import android.widget.ImageView;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.adapter.DateViewPagerAdapter;
import com.hour24.calendarrangeselect.adapter.DayWeekAdapter;
import com.hour24.calendarrangeselect.databinding.ActivityMainBinding;
import com.hour24.calendarrangeselect.model.ModelDate;
import com.hour24.calendarrangeselect.model.ModelDayWeek;
import com.hour24.calendarrangeselect.util.Utils;
import com.hour24.calendarrangeselect.widget.WrapContentHeightViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private ActivityMainBinding mBinding;

    // Views
    private ImageView mBackMonth;
    private ImageView mNextMonth;
    private TextView mCurrentMonth;
    private TextView mStartDate;
    private TextView mFinishedDate;
    private RecyclerView mDayWeekRecyclerView;
    private WrapContentHeightViewPager mDateViewPager;

    // Adapter
    private DayWeekAdapter mDayWeekAdapter;
    private DateViewPagerAdapter mDateViewPagerAdapter;

    private ArrayList<ModelDate> mDateList;
    private int mMonthCount = 10 * 12; // 10년(120개월)
    private int mCurrentPosition = 0; // view page

    // 날짜선택 Model
    private ModelDate firstSelect;
    private ModelDate secondSelect;

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

        mBackMonth = (ImageView) findViewById(R.id.back_month); // 이전달
        mNextMonth = (ImageView) findViewById(R.id.next_month); // 다음달
        mCurrentMonth = (TextView) findViewById(R.id.current_month); // 현재달
        mStartDate = (TextView) findViewById(R.id.started_date); // 시작일
        mFinishedDate = (TextView) findViewById(R.id.finished_date); // 종료일
        mDayWeekRecyclerView = (RecyclerView) findViewById(R.id.day_week_recyclerview); // 일요일 ~ 토요일
        mDateViewPager = (WrapContentHeightViewPager) findViewById(R.id.date_viewpager); // 달력

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
        // 3. ArrayList 에 데이터를 넣을때 index 도 같이 넣어줌.

        // index
        int index = 0;

        // 기준 년, 월 세팅
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDate = calendar.get(Calendar.DATE);
        calendar.set(curYear, curMonth, 1);

        // View Pager 를 세팅하기 위한 작업
        ArrayList<ModelDate> monthList = new ArrayList<>();
        for (int i = 0; i < mMonthCount; i++) {

            // 년, 월 세팅
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int startDate = calendar.get(Calendar.DAY_OF_WEEK);

            ModelDate model = new ModelDate();

            model.setCurYear(curYear); // 현재연도
            model.setCurMonth(curMonth); // 현재월
            model.setCurDate(curDate); // 현재일

            model.setYear(year); // 달력연도
            model.setMonth(month); // 달력월
            model.setStartDayOfWeek(startDate); // 1일 시작 요일

            ArrayList<ModelDate> dateList = new ArrayList<>();
            // 일 세팅
            int lastDate = calendar.getActualMaximum(Calendar.DATE);
            for (int j = 1; j <= lastDate; j++) {

                // 일
                Calendar calendarDate = Calendar.getInstance(Locale.KOREA);
                calendarDate.set(year, month, j);

                ModelDate date = new ModelDate();
                date.setYear(calendarDate.get(Calendar.YEAR));
                date.setMonth(calendarDate.get(Calendar.MONTH));
                date.setDate(calendarDate.get(Calendar.DATE));
                date.setIndex(index++);

                // 오늘 날짜보다 앞 날짜 비교
                if (getCompareToDate(String.format("%s-%s-%s", curYear, curMonth, curDate), String.format("%s-%s-%s", year, month, j)) > 0) {
                    date.setStyle(ModelDate.Style.TODAY_BEFORE);
                } else {
                    date.setStyle(ModelDate.Style.NORMALITY);
                }

                // 일요일 : #ff0000
                // 토요일 : #0000ff
                if (date.getStyle() == ModelDate.Style.TODAY_BEFORE) {
                    date.setTextColor("#8c8c8c");
                } else {
                    if (calendarDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        date.setTextColor("#ff0000");
                    } else if (calendarDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        date.setTextColor("#0000ff");
                    } else {
                        date.setTextColor("#000000");
                    }
                }

                // Add Array List
                dateList.add(date);

            }
            model.setDateList(dateList);

            // Add Array List
            monthList.add(model);

            // 다음달
            calendar.add(Calendar.MONTH, 1);
        }

        // 현재 월 세팅
        mBinding.setModel(monthList.get(0));

        return monthList;
    }

    /**
     * @author 장세진
     * @description 날짜비교 0 == 같음, 1 == 큼, -1 == 작음
     */
    public int getCompareToDate(String today, String compare) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            Date todayDate = format.parse(today);
            Date compareDate = format.parse(compare);
            return todayDate.compareTo(compareDate);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // check
    public void checkRangeDate(ModelDate model) {

        // 1. FirstSelected == false > FirstSelected = true
        // 2. FirstSelected == true > SecondSelected = true
        // 3. SecondSelected == true > FirstSelected = true, SecondSelected = false;
        if (firstSelect == null && secondSelect == null) {
            firstSelect = model;
        } else if (firstSelect != null && secondSelect == null) {
            secondSelect = model;
        } else if (firstSelect != null && secondSelect != null) {
            firstSelect = model;
            secondSelect = null;
        } else {
            firstSelect = null;
            secondSelect = null;
        }

        // 위 처리에 따른 Array 초기화
        {
            int index = 0;
            for (ModelDate monthModel : mDateList) {
                for (ModelDate dateModel : monthModel.getDateList()) {

                    // 모든값 false 처리
                    dateModel.setFirstSelected(false);
                    dateModel.setSecondSelected(false);

                    // 선택된 값 true 처리
                    if (firstSelect != null && index == firstSelect.getIndex()) {
                        dateModel.setFirstSelected(true);
                    }

                    if (secondSelect != null && index == secondSelect.getIndex()) {
                        dateModel.setSecondSelected(true);
                    }

                    index++;
                }
            }
        }

        // 최종 값 처리
        // 1. model.firstSelect, model.secondSelect 기간 비교
        // 2. model.firstSelect > model.secondSelect 일 경우 값 변경
        {
            if (firstSelect != null && secondSelect != null) {
                if (firstSelect.getIndex() > secondSelect.getIndex()) {
                    // Array 에도 값 변경
                    for (ModelDate monthModel : mDateList) {
                        for (ModelDate dateModel : monthModel.getDateList()) {

                            // 선택된 순서 변경
                            if ((firstSelect.getIndex() == dateModel.getIndex()) && dateModel.isFirstSelected()) {
                                dateModel.setFirstSelected(false);
                                dateModel.setSecondSelected(true);
                            }

                            if ((secondSelect.getIndex() == dateModel.getIndex()) && dateModel.isSecondSelected()) {
                                dateModel.setFirstSelected(true);
                                dateModel.setSecondSelected(false);
                            }
                        }
                    }
                }
            }
        }

        // test
        Log.e("sjjang", "----------------------------");
        for (ModelDate monthModel : mDateList) {
            for (ModelDate dateModel : monthModel.getDateList()) {

                if (dateModel.isFirstSelected()) {
                    Utils.setLogDate("checkRangeDate - isFirstSelected", dateModel);
                }

                if (dateModel.isSecondSelected()) {
                    Utils.setLogDate("checkRangeDate - isSecondSelected", dateModel);
                }
            }
        }
    }
}