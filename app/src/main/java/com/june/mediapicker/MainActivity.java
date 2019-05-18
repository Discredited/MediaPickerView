package com.june.mediapicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.june.mediapicker.matisse.SimpleMatisseActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_matisse).setOnClickListener(this);
        findViewById(R.id.tv_boxing).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_matisse:
                SimpleMatisseActivity.start(this);
                break;
        }
    }
}
