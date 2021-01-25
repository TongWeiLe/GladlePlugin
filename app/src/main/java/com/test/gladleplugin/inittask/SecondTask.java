package com.test.gladleplugin.inittask;

import android.util.Log;

import com.test.gladleplugin.alpha.Task;

public class SecondTask extends Task {
    private static final String TAG = "SecondTask";
    public SecondTask(String name) {
        super(name);
    }

    public SecondTask(String name, int threadPriority) {
        super(name, threadPriority);
    }

    public SecondTask(String name, boolean isInUiThread) {
        super(name, isInUiThread);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            Log.i(TAG, "second task finish" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
