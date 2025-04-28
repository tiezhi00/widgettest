package com.app.widgettest.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.app.widgettest.R;

import java.io.OptionalDataException;
import java.util.LinkedList;
import java.util.Queue;

public class WidgetProvider extends android.appwidget.AppWidgetProvider{
    private static final String TAG = "WidgetProvider";
    private static Queue<Integer> widgetIds=new LinkedList<Integer>();

    public static void updateAppWidget(Context context,String text) {
        Log.d(TAG, "updateAppWidget: ");
        //更新Widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        remoteViews.setTextViewText(R.id.tv_widget,text);
        //添加到所有的Widget
        for (int appWidgetId : new LinkedList<>(widgetIds)) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG, "onUpdate: ");
        //保存所有的Widget ID
        for (int appWidgetId : appWidgetIds) {
            widgetIds.add(appWidgetId);
            Log.i(TAG, "widgetId:" + appWidgetId + ",Size:" + widgetIds.size());
        }
        Log.i(TAG, "onUpdate: appWidgetIds.length=" + appWidgetIds.length);
        //启动服务
        context.startService(new Intent(context,WidgetService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(TAG, "onDeleted: ");
        //去除要删除的Widget ID
        for (int appWidgetId : appWidgetIds) {
            if (widgetIds.contains(appWidgetId)) {
                widgetIds.remove((Object) appWidgetId);
            }
            Log.i(TAG, "widgetIds:" + appWidgetId + ",Size:" + widgetIds.size());
        }
        Log.i(TAG,"appWidgetIds.length:"+appWidgetIds.length);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i(TAG, "onDisabled: ");
        //停止服务
        context.stopService(new Intent(context,WidgetService.class));
    }
}
