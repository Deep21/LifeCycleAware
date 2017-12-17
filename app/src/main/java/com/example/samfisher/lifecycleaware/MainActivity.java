package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import dagger.android.AndroidInjection;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  @Inject
  ViewModelFactory viewModelFactory;
  @Inject
  ContactListAdapter contactListAdapter;
  @BindView(R.id.contact_recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.main_layout_id)
  View view;
  @BindView(R.id.fab)
  FloatingActionButton fab;
  ContactViewModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    fab.setOnClickListener(v -> model.loadContact());
    contactListAdapter.setOnItemClickListener(position -> {
      model.setContactId(position);
    });
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(contactListAdapter);
    model = ViewModelProviders.of(this, viewModelFactory).get(ContactViewModel.class);
    showContacts();
    observeOnContactSelected();
  }

  private void observeOnContactSelected() {
    model.getContactById().observe(this,
        resource -> Log.d(TAG, "observeOnContactSelected: " + ((Contact)resource.data).getCompany()));
  }

  private void showContacts() {
    model.getContacts().observe(this, resource -> {
      Log.d(TAG, "onCreate: " + resource);
      contactListAdapter.setContactList((List<Contact>) resource.data);
      contactListAdapter.notifyDataSetChanged();
    });
  }

  @Override
  protected void onStop() {
    Log.d(TAG, "onStop: ");
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, "onDestroy: ");
    super.onDestroy();
  }

  @Override
  protected void onRestart() {
    Log.d(TAG, "onRestart: ");
    super.onRestart();
  }

  @Override
  protected void onResume() {
    Log.d(TAG, "onResume: ");
    super.onResume();
  }

  @Override
  protected void onStart() {
    Log.d(TAG, "onStart: ");
    super.onStart();
  }
}
