package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.samfisher.lifecycleaware.viewmodel.TaskViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.viewmodel.ContactViewModelFactory;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class TaskListActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  private static final String TAG = "TaskListActivity";
  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
  @Inject
  ContactViewModelFactory contactViewModelFactory;
  TaskViewModel taskViewModel;
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.fab_add_task)
  FloatingActionButton floatingActionButton;
  @BindView(R.id.nav_view)
  NavigationView navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setupToolbar();
    setupNavigationDrawer();
    setUpFab();

    SearchTaskFragment taskListFragment = (SearchTaskFragment) getSupportFragmentManager()
        .findFragmentByTag(SearchTaskFragment.TAG);

    if (taskListFragment == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.frame_layout, SearchTaskFragment.newInstance(), SearchTaskFragment.TAG)
          .commit();
    }

    taskViewModel = obtainViewModel(this, contactViewModelFactory);
    observeRecyclerViewClick();
  }

  /**
   *
   */
  private void observeRecyclerViewClick() {
    taskViewModel.getSingleLiveDataTaskId().observe(this, integer -> {
      Intent intent = new Intent(this, DetailTaskActivity.class);
      intent.putExtra(DetailTaskActivity.EXTRA_TASK_ID, integer.intValue());
      startActivityForResult(intent, 100);
    });
  }

  private void setupNavigationDrawer() {
    drawerLayout = findViewById(R.id.drawer_layout);
    drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
    setupDrawerContent(navigationView);
  }

  private void setupDrawerContent(NavigationView navigationView) {
    navigationView.setNavigationItemSelectedListener(
        menuItem -> {
          switch (menuItem.getItemId()) {

            default:
              break;
          }
          // Close the navigation drawer when an item is selected.
          menuItem.setChecked(true);
          drawerLayout.closeDrawers();
          return true;
        });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // Open the navigation drawer when the home icon is selected from the toolbar.
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar ab = getSupportActionBar();
    ab.setHomeAsUpIndicator(R.drawable.ic_menu);
    ab.setDisplayHomeAsUpEnabled(true);
  }

  public static TaskViewModel obtainViewModel(FragmentActivity activity,
      ContactViewModelFactory contactViewModelFactory) {
    return ViewModelProviders.of(activity, contactViewModelFactory).get(TaskViewModel.class);
  }

  private void setUpFab() {
    floatingActionButton.setOnClickListener(v -> {
      Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
      startActivityForResult(intent, 100);
    });
  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
