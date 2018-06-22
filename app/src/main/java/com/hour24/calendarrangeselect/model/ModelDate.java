package com.hour24.calendarrangeselect.model;

import com.hour24.calendarrangeselect.R;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * Created by N16326 on 2018. 6. 14..
 */

@Data
public class ModelDate implements Serializable {

    public @interface Style {
        public int EMPTY = 1001;
        public int NORMALITY = 1002;
        public int TODAY_BEFORE = 1003;
    }

    // array index
    public int index = 0;

    // 스타일
    public int style = Style.EMPTY; // default

    // 현재
    public int curYear;
    public int curMonth;
    public int curDate;

    // 달력
    public int year;
    public int month;
    public int date = 0;
    public int dayOfWeek;

    // 달력 표기를 위한 값
    public int startDayOfWeek; // 해당월 첫 시작 요일
    public int lastDate; // 마지막 일

    public ArrayList<ModelDate> dateList; // 일 리스트

    public boolean isFirstSelected = false; // 첫번째 클릭
    public boolean isSecondSelected = false; // 두번째 클릭
    public boolean isRange = false; // 시작과 끝 사이 값

    public String textColor; // text Color
    public Object background = R.drawable.selector_circle; // 배경

    public String startDate; // 선택된 값
    public String finishDate; // 선택된 값

    public ModelDate() {

    }

    public ModelDate(int style) {
        this.style = style;
    }
}
