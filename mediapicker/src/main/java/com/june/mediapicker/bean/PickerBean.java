package com.june.mediapicker.bean;

import android.text.TextUtils;

public class PickerBean {

    public static final int PICKER_TYPE_ADD = 0;
    public static final int PICKER_TYPE_IMAGE = 1;
    public static final int PICKER_TYPE_VIDEO = 2;

    public PickerBean() {
    }

    public PickerBean(String url) {
        this.pickerCoverUrl = url;
        this.pickerType = getPickerType(url);
    }

    //picker type
    public int pickerType;
    public int pickerCoverResourceId;  //封面资源ID
    public String pickerCoverUrl;  //封面网络或者本地地址

    public boolean isHiddenDeleteIcon;  //是否隐藏删除按钮

    public boolean isAutoUpload = true;  //是否自动上传
    public String uploadSuccessUrl;  //上传成功的返回路径

    public long videoDuration; //视频时长

    public boolean isLoadResource() {
        return pickerCoverResourceId != 0;
    }

    public boolean isLoadUrl() {
        return !TextUtils.isEmpty(pickerCoverUrl);
    }

    public void setUploadSuccess(String successUrl) {
        isAutoUpload = false;
        uploadSuccessUrl = successUrl;
    }

    public boolean isUploadSuccess() {
        return !TextUtils.isEmpty(uploadSuccessUrl) && !isAutoUpload;
    }

    public int getPickerType(String url) {
        if (TextUtils.isEmpty(url)) {
            return PICKER_TYPE_ADD;
        }
        if (url.endsWith(".png") ||
                url.endsWith(".jpg") ||
                url.endsWith(".jpeg") ||
                url.endsWith(".webp")) {
            return PICKER_TYPE_IMAGE;
        }
        if (url.endsWith(".mp4")) {
            return PICKER_TYPE_VIDEO;
        }

        return PICKER_TYPE_ADD;
    }
}
