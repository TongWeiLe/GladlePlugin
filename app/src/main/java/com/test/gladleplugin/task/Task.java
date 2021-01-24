package com.test.gladleplugin.task;

import android.os.Process;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Task implements ITask {

    // 获得当前CPU的核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    // 设置线程池的核心线程数2-4之间,但是取决于CPU核数
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    // 创建线程池
    ExecutorService executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE);


    // 当前Task依赖的Task数量（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
    private CountDownLatch taskCountDownLatch = new CountDownLatch(dependentArr() == null ? 0 : dependentArr().size());

    /**
     * 当前Task等待，让依赖的Task先执行
     */
    @Override
    public void startLock() {
        try {
            taskCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依赖的Task执行完一个
     */
    @Override
    public void unlock() {
        taskCountDownLatch.countDown();
    }

    /**
     * 是否需要尽快执行，解决特殊场景的问题：一个Task耗时非常多但是优先级却一般，很有可能开始的时间较晚，
     * 导致最后只是在等它，这种可以早开始。
     */
    public boolean needRunAsSoon() {
        return false;
    }

    /**
     * Task的优先级，运行在主线程则不要去改优先级
     */
    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    /**
     * Task执行在哪个线程池，默认在IO的线程池；
     */
    @Override
    public ExecutorService runOnExecutor() {

        return executorService;
    }

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     */
    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 当前Task依赖的Task集合（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
     */
    @Override
    public List<Class<? extends ITask>> dependentArr() {
        return null;
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }

    @Override
    public Runnable getTailRunnable() {
        return null;
    }
}

