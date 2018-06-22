package com.hour24.calendarrangeselect.util;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.model.ModelDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by N16326 on 2018. 6. 14..
 */

public class Utils {

    /**
     * @author 장세진
     * @description Text Color 세팅
     */
    @BindingAdapter({"textColor"})
    public static void setTextColor(TextView view, String color) {
        try {
            if (color == null) {
                return;
            }
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author 장세진
     * @description Background Color 세팅
     */
    @BindingAdapter({"background"})
    public static void setBackground(View view, Object resource) {
        try {
            if (resource instanceof Integer) {
                view.setBackgroundResource((int) resource);
            } else if (resource instanceof String) {
                view.setBackgroundColor(Color.parseColor(String.valueOf(resource)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    }

    /**
     * @author 장세진
     * @description 날짜비교 0 == 같음, 1 == 큼, -1 == 작음
     */
    public static int getCompareToDate(String today, String compare) {
        try {
            SimpleDateFormat format = getSimpleDateFormat();
            Date todayDate = format.parse(today);
            Date compareDate = format.parse(compare);
            return todayDate.compareTo(compareDate);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @author 장세진
     * @description 날짜비교 0 == 같음, 1 == 큼, -1 == 작음
     */
    public static void setLogDate(String description, ModelDate model) {
        int year = model.getYear();
        int month = model.getMonth();
        int date = model.getDate();
        Log.e("sjjang", description + " - " + "index : " + model.getIndex() + " / Date " + String.format("%s-%s-%s", year, month, date));
    }
}
