package com.example.weipeng.andemos.BroadcastDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.util.Locale;

public class BroadcastDemoActivity extends DemoActivity {

    private TextView mBatText;

    private BroadcastReceiver mMyBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter mIntentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_demo);

        initViews();


        mIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mMyBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    private void initViews() {
        mBatText = findViewById(R.id.text_battery);
    }

    @Override
    public String getDemoTitle() {
        return "BroadcastDemo";
    }

    @Override
    public String getDemoContent() {
        return "四大组件-Broadcast示例";
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int soc = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int vol = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                int tem = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                String tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

                String info;

                info = String.format(Locale.getDefault(),
                        "soc=%d\nvoltage=%d\ntemperature=%d\ntechnology=%s\npower source=%d",
                        soc, vol, tem, tech, plugged);

                mBatText.setText(info);
            }
        }
    }
}
