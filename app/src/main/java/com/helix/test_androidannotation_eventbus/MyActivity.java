package com.helix.test_androidannotation_eventbus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
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

//    @Background
//    void doInBackGruond() {
//        publishProgress(50);
//
//
//    }

//    @UiThread
//    void publishProgress(int progress) {
//        progressBar1.setMax(100);
//        progressBar1.setProgress(progress);
//
//      /*This is the main UI thread here I do cool stuff with the JSON objects*/
//    }


    @Click
    void btn_start() {
        count = 0;
        Toast.makeText(this, "Click Button Start", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyActivity.this, ServiceDowload.class);
        startService(intent);
        EventBus.getDefault().post(new ServiceEvent());
//        doInBackGruond();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage1Main(Message message) {
        Log.i("Signal", message.percent + " : " + message.follow);
        switch (message.follow) {
            case 1:
                progressBar1.setProgress(message.percent);
                break;
            case 2:
                progressBar2.setProgress(message.percent);
                break;
            case 3:
                progressBar3.setProgress(message.percent);
                break;
            case 4:
                progressBar4.setProgress(message.percent);
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
                break;
        }

    }

//    private int doSomeTasks() {
//        while (fileSize <= 1000000) {
//
//            fileSize++;
//
//            if (fileSize == 100000) {
//                return 10;
//            } else if (fileSize == 200000) {
//                return 20;
//            } else if (fileSize == 300000) {
//                return 30;
//            }
//            // ...add your own
//
//        }
//
//        return 100;
//    }
}
