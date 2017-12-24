package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.ContactDetailFragment;
import com.example.samfisher.lifecycleaware.ContactListFragment;
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
  abstract ContactListFragment bindContactListFragment();

  @ActivityScoped
  @ContributesAndroidInjector
  abstract ContactDetailFragment bindContactDetailFragment();

}
