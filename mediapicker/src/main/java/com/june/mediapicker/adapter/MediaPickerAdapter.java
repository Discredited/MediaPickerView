package com.june.mediapicker.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
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
    private MediaPickerInterface mediaPickerInterface;
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
            return new MediaPickerImageViewHolder(inflater.inflate(R.layout.item_media_picker_image_layout, viewGroup, false), loadEngine, mediaPickerInterface, itemViewClickListener);
        }
        if (type == PickerBean.PICKER_TYPE_VIDEO) {
            return new MediaPickerVideoViewHolder(inflater.inflate(R.layout.item_media_picker_image_layout, viewGroup, false), loadEngine, mediaPickerInterface, itemViewClickListener);
        }
        return new MediaPickerAddViewHolder(inflater.inflate(R.layout.item_media_picker_add_layout, viewGroup, false), loadEngine, mediaPickerInterface, itemViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaPickerViewHolder holder, int i) {
        holder.bindViewHolder(holder, i, items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<PickerBean> getItemDataList() {
        return items;
    }

    public void setLoadEngine(ImageLoadEngine engine) {
        this.loadEngine = engine;
    }

    public void setMediaPickerInterface(MediaPickerInterface pickerInterface) {
        this.mediaPickerInterface = pickerInterface;
    }

    public void setItemViewClickListener(OnItemViewClickListener<PickerBean> listener) {
        this.itemViewClickListener = listener;
    }

    public void addPickerBean(PickerBean bean, int position) {
        items.add(position, bean);
    }

    public void addPickerList(List<PickerBean> list, boolean isClear) {
        if (isClear) {
            items.clear();
        }

        //判断当前是否包含add?
        int size = items.size();
        if (size == 0) {
            //添加一个add
            PickerBean add = new PickerBean();
            add.pickerType = PickerBean.PICKER_TYPE_ADD;
            items.add(add);
            items.addAll(list);

            notifyDataSetChanged();
        } else {
            PickerBean pickerBean = items.get(size - 1);
            if (pickerBean.pickerType != PickerBean.PICKER_TYPE_ADD) {
                PickerBean add = new PickerBean();
                add.pickerType = PickerBean.PICKER_TYPE_ADD;
                items.add(add);
            }
            items.addAll(size - 1, list);
            notifyItemRangeInserted(size - 1, list.size());
        }
    }

    public void removePickerBean(int position) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }

        items.remove(position);
        notifyItemRemoved(position);
    }

    public void removePickerBean(int position, int column) {
        if (position < 0 || position >= getItemCount()) {
            return;
        }


        int lastRowStart = getItemCount() - ((getItemCount() % column) == 0 ? column : (getItemCount() % column));
        boolean isLastRow = position >= lastRowStart;


        items.remove(position);
        notifyItemRemoved(position);
        //如果position在最后一行，则需要刷新整个最后一行，否则分割线会有问题

        if (!isLastRow && getItemCount() % column == 0) {
            lastRowStart = getItemCount() - ((getItemCount() % column) == 0 ? column : (getItemCount() % column));
            isLastRow = true;
        }

        if (isLastRow) {
            notifyItemRangeChanged(lastRowStart, getItemCount() - 1);
        }
    }

    public boolean isUploadComplete() {
        if (null == items || items.size() == 0) {
            return false;
        }
        for (PickerBean bean : items) {
            if (bean.pickerType == PickerBean.PICKER_TYPE_ADD) {
                continue;
            }
            if (!bean.isUploadSuccess()) {
                return false;
            }
        }
        return true;
    }
}