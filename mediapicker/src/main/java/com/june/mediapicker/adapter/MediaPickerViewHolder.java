package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
import com.june.mediapicker.utils.OnItemViewClickListener;

/**
 * 多媒体选择基类ViewHolder
 * 支持多类型扩展
 */
public abstract class MediaPickerViewHolder extends RecyclerView.ViewHolder {

    protected ImageLoadEngine loadEngine;
    protected MediaPickerInterface mediaPickerInterface;
    protected OnItemViewClickListener<PickerBean> itemViewClickListener;

    public MediaPickerViewHolder(@NonNull View itemView, ImageLoadEngine engine, MediaPickerInterface pickerInterface, OnItemViewClickListener<PickerBean> listener) {
        super(itemView);
        this.loadEngine = engine;
        this.itemViewClickListener = listener;
        this.mediaPickerInterface = pickerInterface;
    }

    abstract void bindViewHolder(MediaPickerViewHolder holder, int position, PickerBean itemData);
}