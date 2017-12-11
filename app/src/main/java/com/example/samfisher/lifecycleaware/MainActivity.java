package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.samfisher.lifecycleaware.di.ViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.next);
        MyViewModel model = ViewModelProviders.of(this, viewModelFactory).get(MyViewModel.class);
        model.init().observe(this, listResource -> {
            Log.d(TAG, "onChanged: " + listResource.data);
            Log.d(TAG, "onChanged: " + listResource.status);
            Log.d(TAG, "onChanged: " + listResource.message);
        });
        btn.setOnClickListener(v -> Log.d(TAG, "onChanged: " + model.getContacts()));
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
