package com.example.weipeng.andemos.ServiceDemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceDemoActivity extends DemoActivity {

    private static final String TAG = "ServiceDemoActivity";

    private List<Button> mAllButtons = new ArrayList<>();
    private MyService.MyBinder mMyBinder;
    private ServiceConnection mMyServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyBinder = null;
        }
    };

    @Override
    public String getDemoTitle() {
        return "ServiceDemo";
    }

    @Override
    public String getDemoContent() {
        return "四大组件-Service示例";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_demo);

        mAllButtons.add((Button) findViewById(R.id.button_start_myservice));
        mAllButtons.add((Button) findViewById(R.id.button_stop_myservice));
        mAllButtons.add((Button) findViewById(R.id.button_bind_myservice));
        mAllButtons.add((Button) findViewById(R.id.button_unbind_myservice));
        mAllButtons.add((Button) findViewById(R.id.button_call_binder));
        mAllButtons.add((Button) findViewById(R.id.button_start_myintentservice));
        for (Button button: mAllButtons) {
            button.setOnClickListener(new OnViewClick());
        }
    }

    class OnViewClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_start_myservice: {
                    startService(new Intent(getBaseContext(), MyService.class));
                }break;
                case R.id.button_stop_myservice: {
                    stopService(new Intent(getBaseContext(), MyService.class));
                }break;
                case R.id.button_bind_myservice: {
                    bindService(new Intent(getBaseContext(), MyService.class), mMyServiceConn, BIND_AUTO_CREATE);
                }break;
                case R.id.button_unbind_myservice: {
                    unbindService(mMyServiceConn);
                }break;
                case R.id.button_call_binder: {
                    if (mMyBinder == null) {
                        Log.i(TAG, "mMyBinder is null");
                        break;
                    } else {
                        String result;
                        result = mMyBinder.handle("hello");
                        Toast.makeText(getBaseContext(), "result=" + result, Toast.LENGTH_SHORT).show();
                    }
                }break;
                case R.id.button_start_myintentservice: {
                    MyIntentService.startTest(getBaseContext(), "world");
                }break;
            }
        }
    }
}
