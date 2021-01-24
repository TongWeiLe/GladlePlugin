package com.test.gladleplugin.dispatcher;

import android.os.Looper;
import android.os.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

public class DelayInitDispatcher {

    Queue<Task> queue = new LinkedList<>();


    public DelayInitDispatcher addTask(Task task) {
        queue.add(task);
        return this;
    }


    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {

            while (!queue.isEmpty()) {
                Task task = queue.poll();

                task.run();

            }

            return !queue.isEmpty();
        }
    };

    public void initDispatcher() {

        Looper.myQueue().addIdleHandler(mIdleHandler);
    }


}
