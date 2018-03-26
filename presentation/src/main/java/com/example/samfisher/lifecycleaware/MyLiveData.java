package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.util.Log;

/**
 * Created by Samfisher on 29/11/2017.
 */

public class MyLiveData extends LiveData<String> {

    @Override
    protected void onActive() {
        Log.d("TaskListActivity", "onActive: ");
        super.onActive();
    }

    @Override
    protected void onInactive() {
        Log.d("TaskListActivity", "onInactive: ");
        super.onInactive();
    }

    @Override
    protected void setValue(String value) {
        super.setValue(value);
    }
}
