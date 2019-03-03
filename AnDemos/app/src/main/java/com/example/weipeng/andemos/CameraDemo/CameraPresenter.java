package com.example.weipeng.andemos.CameraDemo;

public interface CameraPresenter {

    void takePicture();
    /**
     * 为了方便起见，拍照参数设置界面统一用0-100的滑动条实现
     * @param per 百分比
     */
    void setWBPercent(int per);
    void setFocusPercent(int per);
    void setShutterPercent(int per);
    void setISOPercent(int per);
}
