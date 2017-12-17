package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class Main2Activity extends AppCompatActivity {

  private ContactViewModel model;
  @Inject
  ViewModelFactory viewModelFactory;
  private String TAG = "Main2Activity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    model = ViewModelProviders.of(this, viewModelFactory).get(ContactViewModel.class);
    Log.d(TAG, "onCreate: " + model);
    observeOnContactSelected();
  }

  private void observeOnContactSelected() {
    model.getContactById().observe(this,
        resource -> Log.d(TAG, "observeOnContactSelected: " + resource));
  }

}
