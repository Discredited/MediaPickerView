package com.june.mediapicker.utils;

import android.view.View;

public interface OnItemViewClickListener<T> {

    void onItemViewClick(View view, int position, T itemData);
}