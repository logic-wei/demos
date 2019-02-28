package com.example.weipeng.andemos.ServiceDemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public static final String ACTION_TEST = "com.example.weipeng.andemos.ServiceDemo.action.TEST";

    public static final String EXTRA_TEST = "com.example.weipeng.andemos.ServiceDemo.extra.TEST";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (ACTION_TEST.equals(intent.getAction())) {
                test(intent.getStringExtra(EXTRA_TEST));
            }
        }
    }

    void test(String param) {
        Log.i(TAG, "test() thread="+Thread.currentThread().getName());
        try {
            Log.i(TAG, "test after 1s");
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        Log.i(TAG, "test:param="+param);
    }

    public static void startTest(Context context, String param) {
        Log.i(TAG, "startTest() thread="+Thread.currentThread().getName());
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_TEST);
        intent.putExtra(EXTRA_TEST, param);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "oncreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
