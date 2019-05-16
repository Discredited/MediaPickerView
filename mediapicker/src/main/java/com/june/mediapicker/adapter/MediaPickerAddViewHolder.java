package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.OnItemViewClickListener;

public class MediaPickerAddViewHolder extends MediaPickerViewHolder {

    private AppCompatImageView ivAddCover;

    public MediaPickerAddViewHolder(@NonNull View itemView, ImageLoadEngine engine, OnItemViewClickListener<PickerBean> listener) {
        super(itemView,engine,listener);
        ivAddCover = itemView.findViewById(R.id.iv_add_cover);
    }

    @Override
    void bindViewHolder(MediaPickerViewHolder holder, int position, PickerBean itemData) {
        if (null != loadEngine) {
            if (itemData.isLoadResource()) {
                loadEngine.loadImageCover(itemView.getContext(), itemData.pickerCoverResourceId, ivAddCover);
            } else {
                loadEngine.loadImageCover(itemView.getContext(), R.drawable.ic_media_add, ivAddCover);
            }
        }
    }
}
