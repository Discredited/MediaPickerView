package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.OnItemViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 多媒体选择适配器
 */
public class MediaPickerAdapter extends RecyclerView.Adapter<MediaPickerViewHolder> {

    private LayoutInflater inflater;
    private List<PickerBean> items = new ArrayList<>();

    private ImageLoadEngine loadEngine;
    private OnItemViewClickListener<PickerBean> itemViewClickListener;

    public MediaPickerAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).pickerType;
    }

    @NonNull
    @Override
    public MediaPickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (null == inflater) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        if (type == PickerBean.PICKER_TYPE_IMAGE) {
            return new MediaPickerImageViewHolder(inflater.inflate(R.layout.item_media_picker_image_layout, viewGroup, false), loadEngine, itemViewClickListener);
        }
        if (type == PickerBean.PICKER_TYPE_VIDEO) {
            return new MediaPickerVideoViewHolder(inflater.inflate(R.layout.item_media_picker_image_layout, viewGroup, false), loadEngine, itemViewClickListener);
        }
        return new MediaPickerAddViewHolder(inflater.inflate(R.layout.item_media_picker_add_layout, viewGroup, false), loadEngine, itemViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaPickerViewHolder holder, int i) {
        holder.bindViewHolder(holder, i, items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoadEngine(ImageLoadEngine engine) {
        loadEngine = engine;
    }

    public void setItemViewClickListener(OnItemViewClickListener<PickerBean> listener) {
        itemViewClickListener = listener;
    }

    public void addPickerBean(PickerBean bean, int position) {
        items.add(position, bean);
    }

    public void addPickerList(List<PickerBean> list, boolean isClear) {
        if (isClear) {
            items.clear();
        }
        items.addAll(list);
    }
}