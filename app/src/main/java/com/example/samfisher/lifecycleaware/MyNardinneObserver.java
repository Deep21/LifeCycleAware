package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class MyNardinneObserver implements LifecycleObserver {

    private static final String TAG = "MyNardinneObserver";
    private Lifecycle lifecycle;

    public MyNardinneObserver(Lifecycle lifecycle) {

        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate()
    {
        Log.d(TAG, "nardinne: " + "on CREATE");
        Log.d(TAG, "nardinne: " + "state " + lifecycle.getCurrentState());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause()
    {
        Log.d(TAG, "nardinne: " + "ON_PAUSE");
        Log.d(TAG, "nardinne: " + "state " + lifecycle.getCurrentState());

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume()
    {
        Log.d(TAG, "nardinne: " + "ON_RESUME");
        Log.d(TAG, "nardinne: " + "state " + lifecycle.getCurrentState());

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart()
    {
        Log.d(TAG, "nardinne: " + "ON_START");
        Log.d(TAG, "nardinne: " + "state " + lifecycle.getCurrentState());

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop()
    {
        Log.d(TAG, "nardinne: " + "ON_STOP");
        Log.d(TAG, "nardinne: " + "state " + lifecycle.getCurrentState());

    }
}
