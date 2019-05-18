package com.june.mediapicker.utils;

import com.june.mediapicker.bean.PickerBean;

public interface MediaPickerInterface {

    void requestPermission();

    void startMediaPicker(int currentCount, int maxCount);

    void uploadMediaList();

    boolean deleteMediaBean(int position, PickerBean pickerBean);
}
