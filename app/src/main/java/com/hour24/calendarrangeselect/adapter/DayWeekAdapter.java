package com.hour24.calendarrangeselect.adapter;

import android.content.Context;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hour24.calendarrangeselect.BR;
import com.hour24.calendarrangeselect.R;
import com.hour24.calendarrangeselect.model.ModelDayWeek;

import java.util.ArrayList;

/**
 * Created by N16326 on 2018. 6. 7..
 */

public class DayWeekAdapter extends RecyclerView.Adapter<DayWeekAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ModelDayWeek> mDayWeekList;

    public DayWeekAdapter(Context context, ArrayList<ModelDayWeek> dayWeekList) {
        this.mContext = context;
        this.mDayWeekList = dayWeekList;
    }

    @Override
    public int getItemCount() {
        return mDayWeekList.size();
    }

    @Override
    public DayWeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.day_week_item, parent, false).getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            ModelDayWeek item = mDayWeekList.get(position);
            holder.getBinding().setVariable(BR.model, item);
            holder.getBinding().executePendingBindings();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}

