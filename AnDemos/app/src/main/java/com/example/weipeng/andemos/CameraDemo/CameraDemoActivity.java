package com.example.weipeng.andemos.CameraDemo;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

public class CameraDemoActivity extends DemoActivity {

    @Override
    public String getDemoTitle() {
        return "CameraDemo";
    }

    @Override
    public String getDemoContent() {
        return "Camera示例";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.camera_demo);
    }
}
