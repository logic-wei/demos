package com.example.weipeng.andemos.CameraDemo;

public interface AbstractCamera {

    interface ResultListener {
        void onResult(int result);
    }

    void takePicture(ResultListener listener);
    String setWBPercent(int per);
    String setFocusPercent(int per);
    String setShutterPercent(int per);
    String setISOPercent(int per);
}
