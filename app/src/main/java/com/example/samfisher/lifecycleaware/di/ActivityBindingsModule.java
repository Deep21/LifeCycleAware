package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.Main2Activity;
import com.example.samfisher.lifecycleaware.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Samfisher on 04/09/2017.
 */

@Module
abstract class ActivityBindingsModule {

  @ActivityScoped
  @ContributesAndroidInjector
  abstract MainActivity bindActivity();

  @ActivityScoped
  @ContributesAndroidInjector
  abstract Main2Activity bindActivity2();

}
