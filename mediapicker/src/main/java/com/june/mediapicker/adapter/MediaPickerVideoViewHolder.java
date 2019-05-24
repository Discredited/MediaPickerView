package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
import com.june.mediapicker.utils.OnItemViewClickListener;

import java.util.Locale;

public class MediaPickerVideoViewHolder extends MediaPickerViewHolder {

    private AppCompatImageView ivVideoCover;
    private AppCompatImageView ivVideoDelete;
    private AppCompatTextView tvVideoDuration;
    private AppCompatTextView tvUploadProgress;

    public MediaPickerVideoViewHolder(@NonNull View itemView, ImageLoadEngine engine, MediaPickerInterface pickerInterface, OnItemViewClickListener<PickerBean> listener) {
        super(itemView, engine, pickerInterface, listener);
        ivVideoCover = itemView.findViewById(R.id.iv_video_cover);
        ivVideoDelete = itemView.findViewById(R.id.iv_video_delete);
        tvVideoDuration = itemView.findViewById(R.id.iv_video_duration);
        tvUploadProgress = itemView.findViewById(R.id.tv_upload_progress);
    }

    @Override
    void bindViewHolder(MediaPickerViewHolder holder, int position, final PickerBean itemData) {
        //关于图片加载
        if (null != loadEngine) {
            if (itemData.isLoadUrl()) {
                loadEngine.loadImageCover(itemView.getContext(), itemData.pickerCoverUrl, ivVideoCover);
            }
        }

        tvVideoDuration.setText(String.format(Locale.CHINA, "%02d:%02d", itemData.videoDuration % 60, itemData.videoDuration / 60));

        //关于删除按钮
        if (itemData.isHiddenDeleteIcon) {
            ivVideoDelete.setVisibility(View.GONE);
        } else {
            ivVideoDelete.setVisibility(View.VISIBLE);
            ivVideoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != itemViewClickListener) {
                        itemViewClickListener.onItemViewClick(v, getAdapterPosition(), itemData);
                    }
                }
            });
        }
    }
}