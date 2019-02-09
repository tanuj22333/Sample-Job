package com.tanuj.samplejob.background;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tanuj.samplejob.Utils;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotifyWorker extends Worker {

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {

        Utils.showNotification(getApplicationContext());
        return Worker.Result.success();
    }
}
