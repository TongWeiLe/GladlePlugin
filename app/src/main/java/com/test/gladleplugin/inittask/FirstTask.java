package com.test.gladleplugin.inittask;

import android.content.Context;
import android.util.Log;

import com.test.gladleplugin.alpha.Task;

public class FirstTask extends Task {

    private static final String TAG = "FirstTask";

    private String packageName;

    private Context context;
    public FirstTask(String name, Context context) {
        this(name, 1);
        this.context = context;
    }

    public FirstTask(String name, int threadPriority) {
        super(name, threadPriority);
    }

    public FirstTask(String name, boolean isInUiThread) {
        super(name, isInUiThread);
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {

            packageName = context.getPackageName();

        }

        Log.i(TAG, "first task finish" + packageName + "   "+ System.currentTimeMillis());
    }
}
