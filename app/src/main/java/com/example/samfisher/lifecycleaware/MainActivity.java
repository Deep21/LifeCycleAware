package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.samfisher.lifecycleaware.di.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.BackpressureStrategy;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    ViewModelFactory viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyViewModel model = ViewModelProviders.of(this, viewModelFactory).get(MyViewModel.class);
        model.getListContact().observe(this, contacts -> Log.d(TAG, "observeContacts: " + contacts));

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}
