package com.june.mediapicker.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.june.mediapicker.adapter.MediaPickerAdapter;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.OnItemViewClickListener;

import java.util.List;

/**
 * 多媒体文件选择View
 * 只负责多媒体文件选择开启、选择后显示、上传进度显示....
 * 尽可能支持样式自定义
 * 支持图片选择插拔式配置
 * 支持图片加载插拔式配置
 * 支持图片上传插拔式配置
 */
public class MediaPickerView extends RecyclerView {

    private int column;

    private int currentCount;
    private int maxCount;
    private MediaPickerAdapter adapter;

    public MediaPickerView(@NonNull Context context) {
        this(context, null);
    }

    public MediaPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        column = 3;
        currentCount = 0;
        maxCount = 9;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setLayoutManager(new GridLayoutManager(getContext(), column));
        setHasFixedSize(true);

        adapter = new MediaPickerAdapter();
        setAdapter(adapter);

        initMediaPicker();
    }

    private void initMediaPicker() {
        //初始添加一个addItem
        PickerBean pickerBean = new PickerBean();
        pickerBean.pickerType = PickerBean.PICKER_TYPE_ADD;
        adapter.addPickerBean(pickerBean, 0);
        adapter.notifyDataSetChanged();
    }


    public void setOnItemViewClickListener(OnItemViewClickListener<PickerBean> listener) {
        adapter.setItemViewClickListener(listener);
    }

    public void setImageLoadEngine(ImageLoadEngine engine) {
        adapter.setLoadEngine(engine);
    }

    public void setPickerList(List<PickerBean> list, boolean isClear) {
        adapter.addPickerList(list, isClear);
    }
}