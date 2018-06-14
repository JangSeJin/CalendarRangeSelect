package com.hour24.calendarrangeselect.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by N16326 on 2018. 6. 14..
 */

@Data
public class ModelDay implements Serializable {
    public int year;
    public int month;
    public int day;
    public int startDayOfWeek; // 해당월 첫 시작 요일
}
