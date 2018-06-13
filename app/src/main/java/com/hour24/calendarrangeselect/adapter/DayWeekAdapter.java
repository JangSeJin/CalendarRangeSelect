package com.hour24.calendarrangeselect.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.competition.ssan.ssan.BR;
import app.competition.ssan.ssan.R;
import app.competition.ssan.ssan.model.ModelAttach;
import app.competition.ssan.ssan.model.ModelTab;
import app.competition.ssan.ssan.view.MainActivity;
import app.competition.ssan.ssan.view.PhotoViewPagerFragment;

/**
 * Created by N16326 on 2018. 6. 7..
 */

public class DayWeekAdapter extends RecyclerView.Adapter<DayWeekAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ModelAttach> mImageList;

    public DayWeekAdapter(Context context, ArrayList<ModelAttach> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void removeItem(int position) {
        mImageList.remove(position);
        setPosition();
        notifyItemRemoved(position);
    }

    public void setPosition() {
        // position 정리
        for (int i = 0; i < mImageList.size(); i++) {
            ModelAttach attach = mImageList.get(i);
            attach.setPosition(i);
            mImageList.set(i, attach);
        }
    }

    @Override
    public DayWeekAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.image_horizontal_list_item, parent, false).getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            ModelAttach item = mImageList.get(position);
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
        public void onClick(final View view, final ModelAttach attach) {
            try {
                switch (view.getId()) {
                    case R.id.image:
                        // 글작성일때는 비활성화
                        Log.e("sjjang", "image");
                        Bundle args = new Bundle();
                        args.putSerializable(ModelTab.class.getSimpleName(), null);

                        PhotoViewPagerFragment photoViewPagerFragment = new PhotoViewPagerFragment();
                        photoViewPagerFragment.setArguments(args);

                        MainActivity activity = (MainActivity) mContext;
                        activity.addFragment(R.id.main_container, new PhotoViewPagerFragment());
                        break;

                    case R.id.remove:
                        Log.e("sjjang", "remove");
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(mContext.getString(R.string.write_alert_remove))
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                removeItem(attach.getPosition());
                                            }
                                        })
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

