package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.samfisher.lifecycleaware.ContactListAdapter.OnItemClickListener;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;

import java.util.List;
import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Inject
  ViewModelFactory viewModelFactory;
  @BindView(R.id.contact_recycler_view)
  RecyclerView recyclerView;
  LinearLayoutManager mLayoutManager;
  @Inject
  ContactListAdapter contactListAdapter;
  private Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate: ");
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    recyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(contactListAdapter);
    ContactViewModel contactViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactViewModel.class);
    contactViewModel.getContacts().observe(this, resource -> {
      contactListAdapter.setContactList((List<ContactModel>)resource.data);
      contactListAdapter.notifyDataSetChanged();
    });
    contactListAdapter.setOnItemClickListener(position -> {
      Log.d(TAG, "onCreate: " + position);
      contactViewModel.setContact(position);
      contactViewModel.getContact().observe(MainActivity.this, contact -> Log.d(TAG, "onChanged: " + contact));

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
    unbinder.unbind();
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
