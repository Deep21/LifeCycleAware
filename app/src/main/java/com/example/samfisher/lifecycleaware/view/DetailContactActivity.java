package com.example.samfisher.lifecycleaware.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.samfisher.lifecycleaware.R;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class DetailContactActivity extends AppCompatActivity implements HasSupportFragmentInjector{
  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
  public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_contact);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setUpFab();
    loadDetailFragment();
  }

  public void loadDetailFragment() {
    DetailContactFragment contactDetailFragment = (DetailContactFragment) getSupportFragmentManager().findFragmentByTag(DetailContactFragment.TAG);
    if (contactDetailFragment == null) {
      Bundle b = getIntent().getExtras();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.frame_layout, DetailContactFragment.newInstance(), DetailContactFragment.TAG)
          .commit();
    }
  }

  public void setUpFab() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show());
  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
