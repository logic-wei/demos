package com.example.weipeng.andemos.Camera1Demo;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.text.Format;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

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
                    setMaxPreviewSize();
                    updateSetting();
                    final Camera.Size size = mParameters.getPreviewSize();
                    Log.i(TAG, String.format(Locale.getDefault(), "set preview size:w=%d h=%d", size.width, size.height));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 坑啊：size中的width是指的纵向高度，height指的是横向宽度。。
                            onPreviewSizeChanged(size.height, size.width);
                        }
                    });
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
        Log.i(TAG, String.format(Locale.getDefault(), "width=%d height=%d", width, height));
        //if (holder.getSurface() == null)
        //    return;
        mCameraHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mCamera.stopPreview();
                    mCamera.setPreviewDisplay(holder);
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

    /**
     * 选一个比例最接近的预览尺寸
     * @param w 参考值
     * @param h 参考值
     */
    private void setOptimalPreviewSize(int w, int h) {
        List<Camera.Size> sizes = mParameters.getSupportedPreviewSizes();
        Camera.Size sizeSelected = sizes.get(getOptimalSizeIndex(sizes, w, h));
        Log.i(TAG, String.format(Locale.getDefault(), "selected size:%d * %d", sizeSelected.width, sizeSelected.height));
        mParameters.setPreviewSize(sizeSelected.width, sizeSelected.height);
    }

    private void setMaxPreviewSize() {
        List<Camera.Size> sizes = mParameters.getSupportedPreviewSizes();
        Camera.Size sizeSelected = sizes.get(getMaxSizeIndex(sizes));
        Log.i(TAG, String.format(Locale.getDefault(), "selected size:%d * %d", sizeSelected.width, sizeSelected.height));
        mParameters.setPreviewSize(sizeSelected.width, sizeSelected.height);
    }

    private int getMaxSizeIndex(List<Camera.Size> sizes) {
        int maxSize = 0;
        int maxSizeIndex = 0;

        for (int i = 0; i < sizes.size(); i += 1) {
            int curSize = sizes.get(i).width * sizes.get(i).height;
            if (curSize > maxSize) {
                maxSize = curSize;
                maxSizeIndex = i;
            }
            //Log.i(TAG, String.format(Locale.getDefault(), "index=%d w=%d h=%d", i, sizes.get(i).width, sizes.get(i).height));
        }

        return maxSizeIndex;
    }

    private int getOptimalSizeIndex(List<Camera.Size> sizes, int w, int h) {
        //int h = Math.max(mPreviewView.getWidth(), mPreviewView.getHeight());
        //int w = Math.max(mPreviewView.getWidth(), mPreviewView.getHeight());
        float targetAspect = (float) h / (float) w;
        Log.i(TAG, String.format(Locale.getDefault(), "targetAspect=%f", targetAspect));
        float optAspect = -1f;
        int optSizeIndex = 0;

        for (int i = 0; i < sizes.size(); i += 1) {
            float curAspect = (float) sizes.get(i).height / (float) sizes.get(i).width;
            //Log.i(TAG, String.format(Locale.getDefault(), "curAspect=%f", curAspect));
            if (optAspect == -1) {
                optAspect = curAspect;
            } else {
                Log.i(TAG, String.format(Locale.getDefault(), "index=%d curAspect=%f diff=%f w=%d h=%d",
                        i, curAspect, Math.abs(curAspect - targetAspect), sizes.get(i).width, sizes.get(i).height));
                if (Math.abs(curAspect - targetAspect) < Math.abs(optAspect - targetAspect)) {
                    optAspect = curAspect;
                    optSizeIndex = i;
                }
            }
        }

        return optSizeIndex;
    }

    private void updateSetting() {
        mCamera.setParameters(mParameters);
    }

    private void onPreviewSizeChanged(int width, int height) {
        ViewGroup.LayoutParams params = mPreviewView.getLayoutParams();
        float previewRatio = (float) height / (float) width;
        DisplayMetrics metrics = getScreenSize();
        Log.i(TAG, "ratio="+previewRatio);
        Log.i(TAG, "w:"+metrics.widthPixels+" h:"+metrics.heightPixels);

        params.width = metrics.widthPixels;
        params.height = (int) (params.width * previewRatio);
        mPreviewView.setLayoutParams(params);
    }

    private DisplayMetrics getScreenSize() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics;
    }
}
