package com.test.gladleplugin.inittask;

import android.content.Context;
import android.util.Log;

import com.test.gladleplugin.alpha.Task;

public class FourthTask extends Task {

    public static final String TAG = "FourthTask";
    private Context context;
    public FourthTask(String name, Context context) {
        super(name);
    }

    public FourthTask(String name, int threadPriority) {
        super(name, threadPriority);
    }

    public FourthTask(String name, boolean isInUiThread) {
        super(name, isInUiThread);
    }

    @Override
    public void run() {

        for (int i = 0; i < 1000; i++) {
        }

        Log.i(TAG, "fourth task finish" + System.currentTimeMillis());
    }
}
