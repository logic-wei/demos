package com.example.weipeng.andemos.CameraDemo;

import java.util.List;

public interface CameraView {

    void setFlashOptions(List<String> options);
    void setWBValue(String value);
    void setFocusValue(String value);
    void setShutterValue(String value);
    void setISOValue(String value);
    void onPictureTaken();
}
