package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(new MyNardinneObserver(getLifecycle()));
        Log.d(TAG, "onCreate: "+ getLifecycle().getCurrentState());
        boolean b = getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        Log.d(TAG, "onCreate: " + b);
    }
}
