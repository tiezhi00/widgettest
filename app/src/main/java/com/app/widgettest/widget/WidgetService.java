package com.app.widgettest.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WidgetService extends Service {
    private static final String TAG = "WidgetService";
    private boolean isThreadRuning = false;
    public WidgetService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)  {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "onStartCommand: isThreadRuning=" + isThreadRuning);
        if(!isThreadRuning){
            isThreadRuning = true;
            // 启动线程，执行耗时操作
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!Thread.interrupted()){
                        double random = Math.random();
                        //更新到Widget界面上
                        WidgetProvider.updateAppWidget(WidgetService.this,String.valueOf(random));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}