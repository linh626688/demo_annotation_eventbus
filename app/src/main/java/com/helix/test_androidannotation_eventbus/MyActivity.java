package com.helix.test_androidannotation_eventbus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@EActivity(R.layout.activity_main)
public class MyActivity extends Activity {


    @ViewById(R.id.progess_1)
    ProgressBar progressBar1;

    @DrawableRes(R.drawable.custom_progressbar)
    Drawable drawableRes;

    @ViewById(R.id.progess_2)
    ProgressBar progressBar2;

    @ViewById(R.id.progess_3)
    ProgressBar progressBar3;

    @ViewById(R.id.progess_4)
    ProgressBar progressBar4;
    private int count = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("OnCreate", "AAAA");
        EventBus.getDefault().register(this);
        Intent intent = new Intent(MyActivity.this, ServiceDowload_.class);
        startService(intent);

    }

    @Background
    void doInBackGruond(int thread, int percent) {
        publishProgress(thread, percent);


    }

    @UiThread
    void publishProgress(int thread, int percent) {
        switch (thread) {
            case 1:
                progressBar1.setProgress(percent);
                break;
            case 2:
                progressBar2.setProgress(percent);
                break;
            case 3:
                progressBar3.setProgress(percent);
                break;
            case 4:
                progressBar4.setProgress(percent);
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
                stopService(new Intent(MyActivity.this, ServiceDowload.class));
                break;
        }

      /*This is the main UI thread here I do cool stuff with the JSON objects*/
    }


    @Click
    void btn_start() {
        count = 0;
        Toast.makeText(this, "Click Button Start", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new ServiceEvent());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageMain(Message message) {
        Log.i("Signal", message.percent + " : " + message.follow);

        doInBackGruond(message.follow, message.percent);

    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
