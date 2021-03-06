package com.june.mediapicker.matisse;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.june.mediapicker.R;
import com.june.mediapicker.bean.PickerBean;
import com.june.mediapicker.utils.ImageLoadEngine;
import com.june.mediapicker.utils.MediaPickerInterface;
import com.june.mediapicker.utils.OnItemViewClickListener;
import com.june.mediapicker.widget.MediaPickerView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

/**
 * 知乎Matisse的简单使用
 */
public class SimpleMatisseActivity extends AppCompatActivity implements OnItemViewClickListener<PickerBean> {

    private static final int REQUEST_CODE_CHOOSE = 23;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private MediaPickerView mediaPickerView;

    public static void start(Context context) {
        Intent starter = new Intent(context, SimpleMatisseActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_matisse);

        initMediaPicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || null == data) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHOOSE) {
            //照片选择
            List<String> list = Matisse.obtainPathResult(data);
            if (null != list) {
                mediaPickerView.addPickerList(list, false);
            }
        }
    }

    @Override
    public void onItemViewClick(View view, int position, PickerBean itemData) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_media_picker_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_picker:
                //提交选择
                boolean uploadComplete = mediaPickerView.isUploadComplete();
                ToastUtils.showShort(uploadComplete ? "上传成功" : "上传还没有结束");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMediaPicker() {
        mediaPickerView = findViewById(R.id.media_picker_view);
        mediaPickerView.setMediaPickerInterface(new MediaPickerInterface() {
            @Override
            public void requestPermission() {
                checkPermission();
            }

            @Override
            public void startMediaPicker(int currentCount, int maxCount) {
                startMatisse();
            }

            @Override
            public void uploadMedia(int position, final PickerBean bean, final AppCompatTextView progressView) {
                if (null != progressView) {
                    progressView.setText("正在上传......");
                    progressView.setVisibility(View.VISIBLE);
                    progressView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressView.setText("");
                            progressView.setVisibility(View.GONE);

                            bean.setUploadSuccess("上传成功");
                        }
                    }, position * 1000);
                }
            }

            @Override
            public boolean deleteMediaBean(int position, PickerBean pickerBean) {
                return true;
            }
        });
        mediaPickerView.setImageLoadEngine(new ImageLoadEngine() {
            @Override
            public void loadImageCover(Context context, int resourceId, AppCompatImageView ivCover) {
                Glide.with(context).load(resourceId).into(ivCover);
            }

            @Override
            public void loadImageCover(Context context, String url, AppCompatImageView ivCover) {
                Glide.with(context).load(url).apply(new RequestOptions().centerCrop()).into(ivCover);
            }
        });
    }

    private void checkPermission() {
        //权限检查
        if (PermissionUtils.isGranted(permissions)) {
            //权限检查通过
            ToastUtils.showShort("权限检查通过");
            mediaPickerView.setPermissionGranted(true);
            mediaPickerView.startMediaPicker();
        } else {
            //申请权限
            PermissionUtils.permission(permissions)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            ToastUtils.showShort("权限检查通过");
                            mediaPickerView.setPermissionGranted(true);
                            mediaPickerView.startMediaPicker();
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                            ToastUtils.showShort("权限检查被拒绝");
                        }
                    })
                    .request();
        }
    }

    private void startMatisse() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }
}
