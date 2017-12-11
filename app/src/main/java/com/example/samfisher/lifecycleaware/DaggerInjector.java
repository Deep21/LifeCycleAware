package com.example.samfisher.lifecycleaware;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.samfisher.lifecycleaware.di.App;
import com.example.samfisher.lifecycleaware.di.DaggerAppComponent;
import com.example.samfisher.lifecycleaware.view.Injectable;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
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

    static void handle(Activity activity) {
        //on vérifie si on implémente HasSupportFragmentInjector
        if (activity instanceof HasSupportFragmentInjector) {
            Timber.i("HasSupportFragmentInjector");
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity)
                    .getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                            super.onFragmentActivityCreated(fm, f, savedInstanceState);
                            Timber.i("onFragmentActivityCreated");
                            if (f instanceof Injectable) {
                                Timber.i("Injectable");
                                AndroidSupportInjection.inject(f);
                            }
                        }
                    }, true);

        }
    }
}
