package com.example.weipeng.andemos.Camera1Demo;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

public class Camera1DemoActivity extends DemoActivity implements SurfaceHolder.Callback {

    private static final String TAG = "Camera1DemoActivity";
    //ui
    Button mShutterButton;
    FrameLayout mPreviewLayout;
    SurfaceView mPreviewView;

    //camera
    Camera mCamera;
    Camera.Parameters mParameters;

    //others
    HandlerThread mCameraHandlerThread;
    Handler mCameraHandler;

    @Override
    public String getDemoTitle() {
        return "Camera1Demo";
    }

    @Override
    public String getDemoContent() {
        return "Camera1示例";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        mCameraHandlerThread = new HandlerThread("CameraHandlerThread");
        mCameraHandlerThread.start();
        mCameraHandler = new Handler(mCameraHandlerThread.getLooper());
        setContentView(R.layout.camera1_demo);
        initViews();
    }

    private void initViews() {
        mShutterButton = findViewById(R.id.button_shutter);
        mShutterButton.setOnClickListener(new Camera1DemoActivity.ShutterClickListener());

        mPreviewLayout = findViewById(R.id.layout_preview);
        mPreviewView = new SurfaceView(this);
        mPreviewLayout.addView(mPreviewView);
        mPreviewView.getHolder().addCallback(this);
    }

    class ShutterClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera = Camera.open();
                    mParameters = mCamera.getParameters();
                    mCamera.setDisplayOrientation(90);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera.stopPreview();
                    mCamera.release();
                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera.setPreviewDisplay(holder);
                    mCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
        Log.i(TAG, "surfaceChanged");
        //if (holder.getSurface() == null)
        //    return;
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera.stopPreview();
                    mCamera.setPreviewDisplay(holder);
                    //mParameters.setPreviewSize(width, height);
                    //mCamera.setParameters(mParameters);
                    mCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
