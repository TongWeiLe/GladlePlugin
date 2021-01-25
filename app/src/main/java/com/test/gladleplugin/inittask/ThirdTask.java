package com.test.gladleplugin.inittask;

import android.util.Log;

import com.test.gladleplugin.alpha.Task;

public class ThirdTask extends Task {

    private static final String TAG = "ThirdTask";
    public ThirdTask(String name) {
        super(name);
    }

    public ThirdTask(String name, int threadPriority) {
        super(name, threadPriority);
    }

    public ThirdTask(String name, boolean isInUiThread) {
        super(name, isInUiThread);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
            Log.i(TAG, "thirdTask finish" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
