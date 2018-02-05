package com.example.samfisher.lifecycleaware.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.viewmodel.ContactViewModelFactory;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class DetailTaskActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  private static final String TAG = "DetailTaskActivity";
  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
  public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";
  @Inject
  ContactViewModelFactory contactViewModelFactory;

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    Log.d(TAG, "onSaveInstanceState: set 5");
    outState.putInt("key", 5);
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState.getInt("key"));
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_contact);
    setUpToolBar();
    setUpFab();
    loadDetailFragment();
  }

  public void loadDetailFragment() {
    Bundle b = getIntent().getExtras();
    DetailTaskFragment detailTaskFragment = (DetailTaskFragment) getSupportFragmentManager().findFragmentByTag(DetailTaskFragment.TAG);
    if (detailTaskFragment == null) {
      int taskId = b.getInt(DetailTaskActivity.EXTRA_TASK_ID);
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.task_container, DetailTaskFragment.newInstance(taskId), DetailTaskFragment.TAG)
          .commit();
    }
  }

  private void setUpToolBar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void setUpFab() {
    FloatingActionButton fab = findViewById(R.id.fab_edit_task);
    fab.setOnClickListener(
        view -> {
          Intent intent = new Intent(DetailTaskActivity.this, AddTaskActivity.class);
          startActivityForResult(intent, 100);
        });
  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
