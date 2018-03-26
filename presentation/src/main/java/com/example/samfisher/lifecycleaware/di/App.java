package com.example.samfisher.lifecycleaware.di;

import android.app.Activity;
import android.app.Application;

import com.example.samfisher.lifecycleaware.BuildConfig;

import com.squareup.leakcanary.LeakCanary;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by Samfisher on 04/09/2017.
 */

public class App extends Application implements HasActivityInjector {

  @Inject
  DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

  @Override
  public void onCreate() {
    DaggerAppComponent
        .builder()
        .application(this)
        .build()
        .inject(this);
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder()
        .name("myrealm.realm")
        .build();
    Realm.setDefaultConfiguration(config);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
    super.onCreate();
    LeakCanary.install(this);
  }

  @Override
  public AndroidInjector<Activity> activityInjector() {
    return activityDispatchingAndroidInjector;
  }

}

