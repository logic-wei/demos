package com.example.weipeng.andemos.Camera1Demo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Layout;
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
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.weipeng.andemos.CustomViewDemo.BreathButton;
import com.example.weipeng.andemos.CustomViewDemo.CustomView;
import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class Camera1DemoActivity extends DemoActivity implements SurfaceHolder.Callback {

    private static final String TAG = "Camera1DemoActivity";
    //ui
    Button mShutterButton;
    FrameLayout mPreviewLayout;
    SurfaceView mPreviewView;
    ToggleButton mSettingButton;
    TextView mWBText;
    TextView mFocusText;
    TextView mExposureText;
    SeekBar mWBSeek;
    SeekBar mFocusSeek;
    SeekBar mExposureSeek;
    View mSettingView;
    BreathButton mCustomView;

    //camera
    Camera mCamera;
    Camera.Parameters mParameters;

    //others
    HandlerThread mCameraHandlerThread;
    Handler mCameraHandler;
    HandlerThread mSaveHandlerThread;
    Handler mSaveHandler;

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
        mSaveHandlerThread = new HandlerThread("SaveHandlerThread");
        mCameraHandlerThread.start();
        mSaveHandlerThread.start();
        mCameraHandler = new Handler(mCameraHandlerThread.getLooper());
        mSaveHandler = new Handler(mSaveHandlerThread.getLooper());
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

        // setting ui
        mSettingButton = findViewById(R.id.button_setting);
        mWBText = findViewById(R.id.text_wb);
        mFocusText = findViewById(R.id.text_focus);
        mExposureText = findViewById(R.id.text_exposure);
        mWBSeek = findViewById(R.id.seek_wb);
        mFocusSeek = findViewById(R.id.seek_focus);
        mExposureSeek = findViewById(R.id.seek_exposure);
        mSettingView = findViewById(R.id.layout_camera1_setting);

        mSettingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSettingView.setVisibility(isChecked ? View.VISIBLE: View.GONE);
            }
        });

        SeekBar.OnSeekBarChangeListener onSettingChangeListener = new OnSettingSeekBarChangedListener();
        mWBSeek.setOnSeekBarChangeListener(onSettingChangeListener);
        mFocusSeek.setOnSeekBarChangeListener(onSettingChangeListener);
        mExposureSeek.setOnSeekBarChangeListener(onSettingChangeListener);

        // 自定义shutter button示例
        mCustomView = findViewById(R.id.button_custom_shutter);
        mCustomView.setColor(Color.BLUE);
        mCustomView.setBiggerBaseRadius(140);
        mCustomView.setSmallerBaseRadius(100);
        mCustomView.setBreathRange(10);
        mCustomView.setOnClickListener(new Camera1DemoActivity.ShutterClickListener());
    }

    private class OnSettingSeekBarChangedListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seek_wb:
                    mCameraHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String result = setWB(progress);
                            updateSetting();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWBText.setText(result);
                                }
                            });
                        }
                    });
                    break;
                case R.id.seek_focus:
                    mCameraHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String result = setFocus(progress);
                            updateSetting();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mFocusText.setText(result);
                                }
                            });
                        }
                    });
                    break;
                case R.id.seek_exposure:
                    mCameraHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            final String result = setExposure(progress);
                            updateSetting();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mExposureText.setText(result);
                                }
                            });
                        }
                    });
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class ShutterClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mCameraHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mCamera == null)
                        return;
                    try {
                        mCamera.autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                if (success)
                                    mCamera.takePicture(null, null, new JpegPictureCallback());
                                else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "failed to focus", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        }
    }

    class JpegPictureCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            mSaveHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        File parent = getExternalFilesDir("photo");//getFilesDir();
                        final File name = new File(parent.getAbsolutePath(), String.format(Locale.getDefault(), "pic-%d.jpeg", Calendar.getInstance().getTime().getTime()));
                        Log.i(TAG, "pic path="+name.getAbsolutePath());
                        if (!name.exists()) {
                            if (!name.createNewFile()) {
                                Log.e(TAG, "failed to create file");
                                return;
                            }
                        }
                        FileOutputStream outputStream = new FileOutputStream(name);
                        outputStream.write(data);
                        outputStream.close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getBaseContext(), "picture is taken:"+name.getPath(), Toast.LENGTH_SHORT).show();
                                Snackbar.make(mPreviewLayout, "picture is taken:"+name.getPath(), 1000).show();
                            }
                        });
                    } catch (Exception e) {
                        Log.i(TAG, e.toString());
                    }
                }
            });
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
                    setMaxPictureSize();
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
        Log.i(TAG, String.format(Locale.getDefault(), "selected preview size:%d * %d", sizeSelected.width, sizeSelected.height));
        mParameters.setPreviewSize(sizeSelected.width, sizeSelected.height);
    }

    private void setMaxPreviewSize() {
        List<Camera.Size> sizes = mParameters.getSupportedPreviewSizes();
        Camera.Size sizeSelected = sizes.get(getMaxSizeIndex(sizes));
        Log.i(TAG, String.format(Locale.getDefault(), "selected preview size:%d * %d", sizeSelected.width, sizeSelected.height));
        mParameters.setPreviewSize(sizeSelected.width, sizeSelected.height);
    }

    private void setMaxPictureSize() {
        List<Camera.Size> sizes = mParameters.getSupportedPictureSizes();
        Camera.Size sizeSelected = sizes.get(getMaxSizeIndex(sizes));
        Log.i(TAG, String.format(Locale.getDefault(), "selected picture size:%d * %d", sizeSelected.width, sizeSelected.height));
        mParameters.setPictureSize(sizeSelected.width, sizeSelected.height);
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

    static private int percentScaleTo(int percent, int targetRangeMin, int targetRangeMax) {
        int targetRange = targetRangeMax - targetRangeMin;
        int result = (int) ((float) percent * (float) targetRange / 100f) + targetRangeMin;

        if (result > targetRange)
            result = targetRange;

        return result;
    }

    private String setWB(int value) {
        List<String> supported = mParameters.getSupportedWhiteBalance();
        String result = supported.get(percentScaleTo(value, 0, supported.size() - 1));
        mParameters.setWhiteBalance(result);

        return result;
    }

    private String setFocus(int value) {
        List<String> supported = mParameters.getSupportedFocusModes();
        String result = supported.get(percentScaleTo(value, 0, supported.size() - 1));
        mParameters.setFocusMode(result);

        return result;
    }

    private String setExposure(int value) {
        int result = percentScaleTo(value,
                mParameters.getMinExposureCompensation(), mParameters.getMaxExposureCompensation());
        mParameters.setExposureCompensation(result);
        return "" + result;
    }
}
