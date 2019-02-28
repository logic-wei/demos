package com.example.weipeng.andemos.BroadcastDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weipeng.andemos.DemoActivity;
import com.example.weipeng.andemos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BroadcastDemoActivity extends DemoActivity {

    private TextView mBatText;
    private List<Button> mAllButtons = new ArrayList<>();
    private OnClickListener mOnClickListener = new OnClickListener();

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
        mAllButtons.add((Button) findViewById(R.id.button_blog_broadcast));
        for (Button button: mAllButtons) {
            button.setOnClickListener(mOnClickListener);
        }
    }

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_blog_broadcast:{
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://github.com/logic-wei/blog/blob/master/articles/Android/%E5%9B%9B%E5%A4%A7%E7%BB%84%E4%BB%B6-%E5%B9%BF%E6%92%AD/main.md"));
                    startActivity(intent);
                }break;
            }
        }
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
