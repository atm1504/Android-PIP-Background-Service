package com.atm1504.pipwidget;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        // RunTimer();
                        makeApiCall();
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

    public  void makeApiCall(){
        Call<List<User>> call = RetrofitClient.getInstance().getMyApi().getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> myheroList = response.body();
//                String[] oneHeroes = new String[myheroList.size()];

                Log.d("ATM", "Got response from server successfully");
                Log.d("ATM",myheroList.toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("ATM", "Failed to fetch data from internet");
            }

        });
    }

}