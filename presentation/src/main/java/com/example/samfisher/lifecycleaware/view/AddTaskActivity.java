package com.example.samfisher.lifecycleaware.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.viewmodel.ContactViewModelFactory;
import com.example.samfisher.lifecycleaware.viewmodel.TaskViewModel;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class AddTaskActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  private static final String TAG = "AddTaskActivity";

  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
  @Inject
  ContactViewModelFactory contactViewModelFactory;
  private TaskViewModel contactModel;

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState.getInt("nardinne"));
    super.onRestoreInstanceState(savedInstanceState);
    Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    Log.d(TAG, "onSaveInstanceState: ");
    outState.putInt("nardinne", 5);
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_contact);
    TaskAddFragment taskAddFragment = (TaskAddFragment) getSupportFragmentManager().findFragmentByTag(TaskAddFragment.TAG);

    if (taskAddFragment == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.frame_layout, TaskAddFragment.newInstance(), TaskAddFragment.TAG)
          .commit();
    }
  }



  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
