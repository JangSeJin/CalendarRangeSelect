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
import com.hour24.calendarrangeselect.activity.MainActivity;
import com.hour24.calendarrangeselect.model.ModelDate;

import java.util.ArrayList;

/**
 * Created by N16326 on 2018. 6. 7..
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private String TAG = DateAdapter.class.getSimpleName();

    private Context mContext;
    private MainActivity mMainActivity;

    private ModelDate mDate;
    private ArrayList<ModelDate> mDateList;

    public DateAdapter(Context context, ModelDate date) {
        this.mContext = context;
        this.mDate = date;
        this.mDateList = date.getDateList();

        mMainActivity = (MainActivity) mContext;
    }

    @Override
    public int getItemCount() {
        // 일 + 시작일 == 전체 아이템 갯수
        return mDateList.size() + (mDate.getStartDayOfWeek() - 1);
    }

    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.date_item, parent, false).getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            // 의미없는 빈공간을 주기 위해 아래 구문을 실시
            ModelDate item;
            if (position < (mDate.getStartDayOfWeek() - 1)) {
                item = new ModelDate();
            } else {
                item = mDateList.get(position - (mDate.getStartDayOfWeek() - 1));
            }

            holder.getBinding().setVariable(BR.model, item);
            holder.getBinding().setVariable(BR.handler, new BindingHandler());
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

    // Handlers
    public class BindingHandler {

        // xml 에 정의
        public void onClick(final View view, ModelDate model) {
            try {
                switch (view.getId()) {
                    case R.id.date:
                        mMainActivity.checkRangeDate(model);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

