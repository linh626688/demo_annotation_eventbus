package com.helix.test_androidannotation_eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import org.androidannotations.annotations.EService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Random;

/**
 * Created by Giang Hoang on 23/06/2016.
 */
@EService
public class ServiceDowload extends Service {
    private Random random = new Random();
    private boolean continues = false;
    private boolean[] check = new boolean[4];


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        Log.i("Service", "Create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onServiceEvent(ServiceEvent serviceEvent) {
        Log.i("Service", "Receive Event");
        prepare();

        startDowload1();
        startDowload2();
        startDowload3();
        startDowload4();
        startCheck();

    }

    private void startCheck() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (continues) {
                    if (check[0] == true && check[1] == true && check[2] == true && check[3] == true) {
                        Log.i("Service", "Finish");
                        EventBus.getDefault().post(new Message(1, 5));
                        continues = false;
                    }
                }
            }
        }).start();
    }

    private void prepare() {
        continues = true;
        for (int i = 0; i < check.length; i++) {
            check[i] = false;
        }
    }

    private void startDowload4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (continues) {
                    if (check[3] == false) {
                        count++;
                    }
                    count++;
                    SystemClock.sleep(random.nextInt(500) + 100);
                    EventBus.getDefault().post(new Message(count, 4));
                    if (count == 100) {
                        check[3] = true;
                    }

                }
            }
        }).start();
    }

    private void startDowload3() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (continues) {
                    if (check[2] == false) {
                        count++;
                    }
                    SystemClock.sleep(random.nextInt(500) + 100);
                    EventBus.getDefault().post(new Message(count, 3));
                    if (count == 100) {
                        check[2] = true;
                    }
                }
            }
        }).start();
    }

    private void startDowload2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (continues) {
                    if (check[1] == false) {
                        count++;
                    }
                    ;
                    SystemClock.sleep(random.nextInt(500) + 100);
                    EventBus.getDefault().post(new Message(count, 2));
                    if (count == 100) {
                        check[1] = true;
                    }
                }
            }
        }).start();
    }

    private void startDowload1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (continues) {
                    if (check[0] == false) {
                        count++;
                    }
                    SystemClock.sleep(random.nextInt(500) + 100);
                    EventBus.getDefault().post(new Message(count, 1));
                    if (count == 100) {
                        check[0] = true;
                    }
                }
            }
        }).start();
    }
}
