package com.example.weipeng.andemos.CameraDemo;

import android.view.View;

public interface AbstractCamera {

    interface ResultListener {
        void onResult(int result);
    }

    interface OpenListener {
        void onOpened(View view);
    }

    void open(OpenListener listener);
    void close();

    void takePicture(ResultListener listener);
    String setWBPercent(int per);
    String setFocusPercent(int per);
    String setShutterPercent(int per);
    String setISOPercent(int per);
}
