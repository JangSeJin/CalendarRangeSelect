package com.hour24.calendarrangeselect.activity;

import android.app.Fragment;
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
import com.hour24.calendarrangeselect.fragment.DateFragment;
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
    private int mCurrentPosition = 0; // view
    private int mMaxIndex = 0;

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

        // 현재 월 세팅
        mBinding.setModel(mDateList.get(0));

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

    // color 세팅
    private ModelDate setTextColor(ModelDate date) {
        // 일요일 : #ff0000
        // 토요일 : #0000ff

        // 선택된 값
        //  ㄴ textColor : #ffffff
        //  ㄴ background : R.drawable.selected_date

        date.setBackground(R.drawable.selector_circle);

        if (date.getStyle() == ModelDate.Style.TODAY_BEFORE) {
            date.setTextColor("#8c8c8c");
        } else if (date.isFirstSelected() && date.isSecondSelected()) { // 당일
            date.setTextColor("#ffffff");
            date.setBackground(R.drawable.selected_date);
        } else if (date.isFirstSelected()) { // 시작
            date.setTextColor("#ffffff");
            date.setBackground(R.drawable.selected_date_start);
        } else if (date.isSecondSelected()) { // 끝
            date.setTextColor("#ffffff");
            date.setBackground(R.drawable.selected_date_finish);
        } else if (date.isRange()) {
            date.setTextColor("#ffffff");
            date.setBackground("#703f51b5");
        } else {
            if (date.getDayOfWeek() == Calendar.SUNDAY) {
                date.setTextColor("#ff0000");
            } else if (date.getDayOfWeek() == Calendar.SATURDAY) {
                date.setTextColor("#0000ff");
            } else {
                date.setTextColor("#000000");
            }

        }

        return date;
    }

    // fragment updaate
    private void monthPageUpdate() {
        // 현재 페이지 좌, 우만 새로고침
        int startPager = 0;
        int endPager = 0;
        if (mCurrentPosition == 0) {
            // 첫번째 페이지
            startPager = 0;
            endPager = 1;
        } else {
            startPager = mCurrentPosition - 1;
            endPager = mCurrentPosition + 1;
        }

        // 새로고침
        for (int i = startPager; i <= endPager; i++) {
            ModelDate dateModel = mDateList.get(i);
            DateFragment dateFragment = (DateFragment) mDateViewPagerAdapter.getFragment(i);
            dateFragment.notifyDataSetChanged(dateModel);
        }
    }

    public ArrayList<ModelDate> getDateList() {

        mDateList = new ArrayList<>();

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
                date.setDayOfWeek(calendarDate.get(Calendar.DAY_OF_WEEK));
                date.setIndex(index++);

                // 오늘 날짜보다 앞 날짜 비교
                if (Utils.getCompareToDate(String.format("%s-%s-%s", curYear, curMonth, curDate), String.format("%s-%s-%s", year, month, j)) > 0) {
                    date.setStyle(ModelDate.Style.TODAY_BEFORE);
                } else {
                    date.setStyle(ModelDate.Style.NORMALITY);
                }

                // text color
                setTextColor(date);

                // Add Array List
                dateList.add(date);

                // 날짜 갯수
                mMaxIndex++;
            }

            model.setDateList(dateList);

            // Add Array List
            monthList.add(model);

            // 다음달
            calendar.add(Calendar.MONTH, 1);
        }

        return monthList;
    }

    // By DataAdapter.class
    public void checkRangeDate(ModelDate model) {

        /**
         * first, second 선택에 따른 구분 처리
         */
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

        // 날짜 세팅
        mBinding.setStart(firstSelect);
        mBinding.setFinish(secondSelect);

        /**
         * 위 처리에 따른 Array 초기화
         */
        {
            int index = 0;
            for (ModelDate monthModel : mDateList) {
                for (ModelDate dateModel : monthModel.getDateList()) {

                    // 모든값 false 처리
                    dateModel.setFirstSelected(false);
                    dateModel.setSecondSelected(false);
                    dateModel.setRange(false);

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
        /**
         * 최종 값 처리
         */
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


            /**
             * 선택된 값 사이 처리
             */
            int index = 0;
            int firstIndex = -1;
            int secondIndex = mMaxIndex;
            for (ModelDate monthModel : mDateList) {
                for (ModelDate dateModel : monthModel.getDateList()) {

                    // 시작점
                    if (dateModel.isFirstSelected() && (index == dateModel.getIndex())) {
                        firstIndex = index;

                        // 날짜 세팅
                        mBinding.setStart(dateModel);
                    }

                    // 끝점
                    if (dateModel.isSecondSelected() && (index == dateModel.getIndex())) {
                        secondIndex = index;

                        // 날짜 세팅
                        mBinding.setFinish(dateModel);
                    }

                    // 범위 설정
                    if ((firstIndex > 0 && firstIndex < index) && index < secondIndex) {
                        dateModel.setRange(true);
                    }

                    index++;
                }
            }
        }

        /**
         * UI 처리
         */
        for (ModelDate monthModel : mDateList) {
            for (ModelDate dateModel : monthModel.getDateList()) {
                setTextColor(dateModel);
            }
        }

        // update
        monthPageUpdate();
    }
}