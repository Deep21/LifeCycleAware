package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import com.example.samfisher.lifecycleaware.view.ContactFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "MainActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyViewModel model = ViewModelProviders.of(this, viewModelFactory).get(MyViewModel.class);
        model.init().observe(this, listResource -> {
            Log.d(TAG, "onChanged: " + listResource.data);
            Log.d(TAG, "onChanged: " + listResource.status);
            Log.d(TAG, "onChanged: " + listResource.message);
        });
        Timber.d(model + "");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ContactFragment.newInstance(), "ContactFragment")
                .commit();
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
