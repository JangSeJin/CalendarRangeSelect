package com.hour24.calendarrangeselect.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ModelDayWeek {

    public String dayWeek;
    public String textColor;
    public ArrayList<ModelDayWeek> dayWeekList;

    public ModelDayWeek() {
        setDayWeekList();
    }

    public ModelDayWeek(String dayWeek, String textColor) {
        this.dayWeek = dayWeek;
        this.textColor = textColor;
    }

    public void setDayWeekList() {

        dayWeekList = new ArrayList<>();
        dayWeekList.add(new ModelDayWeek("일", "#ff0000"));
        dayWeekList.add(new ModelDayWeek("월", "#000000"));
        dayWeekList.add(new ModelDayWeek("화", "#000000"));
        dayWeekList.add(new ModelDayWeek("수", "#000000"));
        dayWeekList.add(new ModelDayWeek("목", "#000000"));
        dayWeekList.add(new ModelDayWeek("금", "#000000"));
        dayWeekList.add(new ModelDayWeek("토", "#0000ff"));

    }

}
