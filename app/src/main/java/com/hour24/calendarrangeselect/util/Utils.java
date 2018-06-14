package com.hour24.calendarrangeselect.util;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.TextView;

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
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
