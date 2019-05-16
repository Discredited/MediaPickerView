package com.june.mediapicker.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

public interface ImageLoadEngine {

    void loadImageCover(Context context, int resourceId, AppCompatImageView ivCover);

    void loadImageCover(Context context, String url, AppCompatImageView ivCover);
}
