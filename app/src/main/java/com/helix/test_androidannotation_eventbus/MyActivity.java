package com.helix.test_androidannotation_eventbus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;

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

    @Background
    void thisGETJSON() {
        progressBar1.setProgress(50);
        publishProgress(50);

    }

    @UiThread
    void publishProgress(int progress) {

        Toast.makeText(this, "Progressbar Run", Toast.LENGTH_SHORT).show();

      /*This is the main UI thread here I do cool stuff with the JSON objects*/
    }

    @Click
    void btn_start() {
        count = 0;
        Toast.makeText(this, "Click Button Start", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            public void run() {
                while (count < 100) {

                    // process some tasks
                    count = doSomeTasks();

                    // your computer is too fast, sleep 1 second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar1.setProgress(count);
                        }
                    });
                }

                // ok, file is downloaded,
                if (count >= 100) {

                    // sleep 2 seconds, so that you can see the 100%
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

    }

    private int doSomeTasks() {
        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }

        return 100;
    }
}
