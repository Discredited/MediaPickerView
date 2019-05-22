package com.june.mediapicker.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.june.mediapicker.R;
import com.june.mediapicker.adapter.MediaPickerAdapter;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
import com.june.mediapicker.utils.MediaPickerItemDecoration;
import com.june.mediapicker.utils.OnItemViewClickListener;

import java.util.ArrayList;
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
    private MediaPickerInterface mediaPickerInterfaceImpl;

    private boolean isPermissionGranted;  //权限是否准备就绪

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

        addItemDecoration(new MediaPickerItemDecoration(getContext(), R.drawable.shape_rect_media_picker_item_decoration));

        adapter = new MediaPickerAdapter();
        adapter.setItemViewClickListener(new OnItemViewClickListener<PickerBean>() {
            @Override
            public void onItemViewClick(View view, int position, PickerBean itemData) {
                if (view.getId() == R.id.iv_add_cover) {
                    //添加照片
                    if (isPermissionGranted) {
                        //执行多媒体选择
                        if (null != mediaPickerInterfaceImpl) {
                            mediaPickerInterfaceImpl.startMediaPicker(currentCount, maxCount);
                        }
                    } else {
                        //申请权限
                        if (null != mediaPickerInterfaceImpl) {
                            mediaPickerInterfaceImpl.requestPermission();
                            //注意申请权限以后需要设置 isPermissionGranted
                        }
                    }
                } else if (view.getId() == R.id.iv_image_delete || view.getId() == R.id.iv_video_delete) {
                    //删除image或者video
                    if (null != mediaPickerInterfaceImpl) {
                        boolean canDelete = mediaPickerInterfaceImpl.deleteMediaBean(position, itemData);
                        if (canDelete) {
                            //移除
                        }
                    }
                }
            }
        });
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

    public void setPermissionGranted(boolean isGranted) {
        isPermissionGranted = isGranted;
    }

    public void startMediaPicker() {
        if (null != mediaPickerInterfaceImpl) {
            mediaPickerInterfaceImpl.startMediaPicker(currentCount, maxCount);
        }
    }

    public void setMediaPickerInterface(MediaPickerInterface pickerInterface) {
        mediaPickerInterfaceImpl = pickerInterface;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener<PickerBean> listener) {
        adapter.setItemViewClickListener(listener);
    }

    public void setImageLoadEngine(ImageLoadEngine engine) {
        adapter.setLoadEngine(engine);
    }

    public void setPickerList(List<String> list, int mediaType, boolean isClear) {
        setPickerList(convertToPicker(list, mediaType), isClear);
    }

    public void setPickerList(List<PickerBean> list, boolean isClear) {
        adapter.addPickerList(list, isClear);
        adapter.notifyDataSetChanged();
    }

    public List<PickerBean> convertToPicker(List<String> list, int mediaType) {
        ArrayList<PickerBean> pickerList = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (String url : list) {
                PickerBean pickerBean = new PickerBean();
                pickerBean.pickerType = mediaType;
                pickerBean.pickerCoverUrl = url;
                pickerList.add(pickerBean);
            }
        }
        return pickerList;
    }
}