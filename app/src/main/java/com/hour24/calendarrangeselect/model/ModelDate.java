package com.hour24.calendarrangeselect.model;

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

    public int index = 0;

    public int style = Style.EMPTY; // default

    public int curYear;
    public int curMonth;
    public int curDate;

    public int year;
    public int month;
    public int date = 0;

    public int startDayOfWeek; // 해당월 첫 시작 요일
    public int lastDate; // 마지막 일

    public ArrayList<ModelDate> dateList;

    public boolean isFirstSelected;
    public boolean isSecondSelected;

    public String textColor;

    public ModelDate() {

    }

    public ModelDate(int style) {
        this.style = style;
    }
}
