package com.example.samfisher.lifecycleaware;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.example.samfisher.lifecycleaware.di.App;
import com.example.samfisher.lifecycleaware.di.DaggerAppComponent;

import dagger.android.AndroidInjection;
import timber.log.Timber;

/**
 * l
 * Created by Samfisher on 11/12/2017.
 */

public class DaggerInjector {

    public static void init(App app) {
        DaggerAppComponent
                .builder()
                .application(app)
                .build()
                .inject(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.i("onActivityCreated");
                handle(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    static void handle(Activity activity){
        AndroidInjection.inject(activity);
    }
}
