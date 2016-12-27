package com.mobile.adsdk.utils;


import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class ThreadManager {
    private static final String THREAD_IO_NAME = "threadIO";
    private static IOHandler sIOHandler;
    private static UIHandler sUIHandler;

    private ThreadManager() {

    }


    public static void init() {
        sUIHandler = new UIHandler(Looper.getMainLooper());
        HandlerThread threadIO = new HandlerThread(THREAD_IO_NAME);
        threadIO.start();
        sIOHandler = IOHandler.getInstance(threadIO.getLooper());
        Process.setThreadPriority(threadIO.getThreadId(), Process.THREAD_PRIORITY_BACKGROUND);
    }

    public static void destroy() {
        if (sIOHandler != null) {
            sIOHandler.removeCallbacksAndMessages(null);
        }
        if (sUIHandler != null) {
            sUIHandler.removeCallbacksAndMessages(null);
        }
    }

    public static void postTaskToIOHandler(Runnable r) {
        sIOHandler.post(r);
    }

    public static void postDelayedTaskToIOHandler(Runnable r, long delayMillis) {
        sIOHandler.postDelayed(r, delayMillis);
    }

    public static void sendMessageToIOHandler(Message msg) {
        sIOHandler.sendMessage(msg);
    }

    public static void sendDelayedMessageToIOHandler(Message msg, long delayMillis) {
        sIOHandler.sendMessageDelayed(msg, delayMillis);
    }


    public static void postTaskToUIHandler(Runnable r) {
        sUIHandler.post(r);
    }

    public static void postDelayedTaskToUIHandler(Runnable r, long delayMillis) {
        sUIHandler.postDelayed(r, delayMillis);
    }

    public static void sendMessageToUIHandler(Message msg) {
        sUIHandler.sendMessage(msg);
    }

    public static void sendDelayedMessageToUIHandler(Message msg, long delayMillis) {
        sUIHandler.sendMessageDelayed(msg, delayMillis);
    }

    public static IOHandler getIOHandler() {
        return sIOHandler;
    }

    public static UIHandler getUIHandler() {
        return sUIHandler;
    }


    public static void removeAllUITask() {
        sUIHandler.removeCallbacksAndMessages(null);
    }
}
