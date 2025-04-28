package com.app.widgettest.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.widgettest.R;

public class ConfigActivity extends AppCompatActivity {

    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);
        //获取Widget的ID
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mAppWidgetId = bundle.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        //如果没有获取到ID，则结束Activity
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        //设置返回结果为取消
        setResult(RESULT_CANCELED);

        //初始化点击事件
        initListeners();



    }

    private void initListeners() {
        findViewById(R.id.btn_update).setOnClickListener(v -> {
            //更新Widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
            remoteViews.setTextColor(R.id.tv_widget, getResources().getColor(R.color.red));
            appWidgetManager.updateAppWidget(mAppWidgetId, remoteViews);

            //设置返回信息，并关闭Activity
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
    }
}