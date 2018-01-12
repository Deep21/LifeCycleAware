package com.example.samfisher.lifecycleaware.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.example.samfisher.lifecycleaware.ContactViewModel;
import com.example.samfisher.lifecycleaware.R;
import com.example.samfisher.lifecycleaware.di.ViewModelFactory;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  private static final String TAG = "MainActivity";
  @Inject
  DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
  @Inject
  ViewModelFactory viewModelFactory;
  ContactViewModel contactViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    ContactListFragment contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag(ContactListFragment.TAG);
    if (contactListFragment == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.frame_layout, ContactListFragment.newInstance(), ContactListFragment.TAG)
          .commit();
    }
    contactViewModel = obtainViewModel(this, viewModelFactory);
    contactViewModel.getContactId().observe(this, integer -> {
      Toast.makeText(MainActivity.this, "" + integer, Toast.LENGTH_SHORT).show();
      Log.d(TAG, "onChanged: " + integer.toString());
    });
  }


  public static ContactViewModel obtainViewModel(FragmentActivity activity,
      ViewModelFactory viewModelFactory) {
    return ViewModelProviders.of(activity, viewModelFactory).get(ContactViewModel.class);
  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return fragmentDispatchingAndroidInjector;
  }
}
