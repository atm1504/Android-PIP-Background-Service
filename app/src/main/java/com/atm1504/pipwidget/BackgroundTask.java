package com.atm1504.pipwidget;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundTask extends Worker {
    public BackgroundTask(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        runLongLoop();
        return Result.success();
    }

    public void runLongLoop() {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RunTimer();
                        runLongLoop();
                    }
                }, 10000);
                Looper.loop();
            }
        };
        thread.start();
    }

    public void RunTimer() {
        new CountDownTimer(9000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("ATM", "seconds remaining:  " + millisUntilFinished);
            }

            public void onFinish() {
                Log.d("ATM", "Finished the timer");
            }

        }.start();
    }

}