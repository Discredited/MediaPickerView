package com.june.mediapicker.utils;

import android.support.v7.widget.AppCompatTextView;

import com.june.mediapicker.bean.PickerBean;

public interface MediaPickerInterface {

    void requestPermission();

    void startMediaPicker(int currentCount, int maxCount);

    void uploadMedia(int position, PickerBean bean, AppCompatTextView progressView);

    boolean deleteMediaBean(int position, PickerBean pickerBean);
}
