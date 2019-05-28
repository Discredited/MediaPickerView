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

public class MediaPickerImageViewHolder extends MediaPickerViewHolder {

    private AppCompatImageView ivImageCover;
    private AppCompatImageView ivImageDelete;
    private AppCompatTextView tvUploadProgress;

    public MediaPickerImageViewHolder(@NonNull View itemView, ImageLoadEngine engine, MediaPickerInterface pickerInterface, OnItemViewClickListener<PickerBean> listener) {
        super(itemView, engine, pickerInterface, listener);
        ivImageCover = itemView.findViewById(R.id.iv_image_cover);
        ivImageDelete = itemView.findViewById(R.id.iv_image_delete);
        tvUploadProgress = itemView.findViewById(R.id.tv_upload_progress);
    }

    @Override
    void bindViewHolder(MediaPickerViewHolder holder, int position, final PickerBean itemData) {
        if (null != loadEngine) {
            if (itemData.isLoadUrl()) {
                loadEngine.loadImageCover(itemView.getContext(), itemData.pickerCoverUrl, ivImageCover);
            }
            if (itemData.isLoadDeleteIcon()) {
                ivImageDelete.setImageResource(itemData.pickerDeleteResourceId);
            }
        }

        if (itemData.isHiddenDeleteIcon) {
            ivImageDelete.setVisibility(View.GONE);
        } else {
            ivImageDelete.setVisibility(View.VISIBLE);
            ivImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != itemViewClickListener) {
                        itemViewClickListener.onItemViewClick(v, getAdapterPosition(), itemData);
                    }
                }
            });
        }

        //关于上传
        if (itemData.isAutoUpload) {
            if (null != mediaPickerInterface) {
                mediaPickerInterface.uploadMedia(getAdapterPosition(), itemData, tvUploadProgress);
            }
        }
    }
}