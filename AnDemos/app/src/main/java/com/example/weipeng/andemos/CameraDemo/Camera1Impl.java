package com.example.weipeng.andemos.CameraDemo;


import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public class Camera1Impl implements AbstractCamera {
    private static final String TAG = "Camera1Impl";

    private Handler mMainHandler;
    private HandlerThread mCameraThread;
    private Handler mCameraHandler;

    private int mCameraId;
    private Camera mCamera;
    private Camera.Parameters mParameters;

    Camera1Impl() {
        mMainHandler = new Handler();
        mCameraThread = new HandlerThread("CameraThread");
        mCameraHandler = new Handler(mCameraThread.getLooper());
    }

    @Override
    public void open(final OpenListener listener) {
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera = Camera.open();
                    mParameters = mCamera.getParameters();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public void close() {
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public String setWBPercent(int per) {
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
        return null;
    }

    @Override
    public String setShutterPercent(int per) {
        return null;
    }

    @Override
    public String setFocusPercent(int per) {
        return null;
    }

    @Override
    public String setISOPercent(int per) {
        return null;
    }

    @Override
    public void takePicture(ResultListener listener) {

    }
}
