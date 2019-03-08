package com.example.weipeng.andemos.CustomViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

public class CustomViewDemoActivity extends DemoActivity {

    CustomView mCustomView;
    BreathButton mBreathButton;

    @Override
    public String getDemoTitle() {
        return "CustomViewDemo";
    }

    @Override
    public String getDemoContent() {
        return "自定义UI控件示例";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_demo);
        mCustomView = findViewById(R.id.view_custom);
        mCustomView.setColor(Color.GRAY);
        mCustomView.setRadius(200);
        mCustomView.setRadiusOverlay(100);

        mBreathButton = findViewById(R.id.view_breath);
        mBreathButton.setColor(Color.LTGRAY);
        mBreathButton.setBiggerBaseRadius(250);
        mBreathButton.setSmallerBaseRadius(120);
        mBreathButton.setBreathRange(30);
    }
}
