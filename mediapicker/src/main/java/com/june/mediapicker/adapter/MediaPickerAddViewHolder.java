package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
import com.june.mediapicker.utils.OnItemViewClickListener;

public class MediaPickerAddViewHolder extends MediaPickerViewHolder {

    private AppCompatImageView ivAddCover;

    public MediaPickerAddViewHolder(@NonNull View itemView, ImageLoadEngine engine, MediaPickerInterface pickerInterface, OnItemViewClickListener<PickerBean> listener) {
        super(itemView, engine, pickerInterface, listener);
        ivAddCover = itemView.findViewById(R.id.iv_add_cover);
    }

    @Override
    void bindViewHolder(MediaPickerViewHolder holder, int position, final PickerBean itemData) {
        if (null != loadEngine) {
            if (itemData.isLoadResource()) {
                loadEngine.loadImageCover(itemView.getContext(), itemData.pickerCoverResourceId, ivAddCover);
            } else {
                //loadEngine.loadImageCover(itemView.getContext(), R.drawable.ic_media_add, ivAddCover);
                ivAddCover.setImageResource(R.drawable.ic_media_add);
            }
        }

        // TODO: 2019/5/18 绑定点击事件优化
        ivAddCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemViewClickListener) {
                    itemViewClickListener.onItemViewClick(v, getAdapterPosition(), itemData);
                }
            }
        });
    }
}
