package com.example.samfisher.lifecycleaware.di;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.example.samfisher.lifecycleaware.view.AddTaskActivity;
import com.example.samfisher.lifecycleaware.view.DetailTaskActivity;
import com.example.samfisher.lifecycleaware.view.DetailTaskFragment;
import com.example.samfisher.lifecycleaware.view.SearchTaskFragment;
import com.example.samfisher.lifecycleaware.view.TaskAddFragment;
import com.example.samfisher.lifecycleaware.view.TaskListActivity;
import com.example.samfisher.lifecycleaware.view.TaskListFragment;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Samfisher on 04/09/2017.
 */

@Module
abstract class ActivityBindingsModule {

  @PerActivity
  @ContributesAndroidInjector
  abstract TaskListActivity bindActivity();

  @PerFragment
  @ContributesAndroidInjector
  abstract TaskListFragment bindContactListFragment();

  @PerActivity
  @ContributesAndroidInjector
  abstract DetailTaskActivity bindDetailContactActivity();

  @PerFragment
  @ContributesAndroidInjector
  abstract DetailTaskFragment bindDetailContactFragment();

  @PerActivity
  @ContributesAndroidInjector
  abstract AddTaskActivity bindAddContactDetailFragment();

  @PerFragment
  @ContributesAndroidInjector
  abstract TaskAddFragment bindContactAddFragment();


  @PerFragment
  @ContributesAndroidInjector
  abstract SearchTaskFragment bindSearchTaskFragment();

  @Provides
  @PerActivity
  static FragmentManager activityFragmentManager(FragmentActivity activity) {
    return activity.getSupportFragmentManager();
  }
}
