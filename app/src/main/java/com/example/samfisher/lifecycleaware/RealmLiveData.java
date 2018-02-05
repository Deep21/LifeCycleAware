package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Samfisher on 08/01/2018.
 */

public class RealmLiveData<T extends RealmModel> extends LiveData<RealmResults<T>> {

  private static final String TAG = "RealmLiveData";
  private RealmResults<T> results;
  private RealmChangeListener<RealmResults<T>> changeListener = new RealmChangeListener<RealmResults<T>>() {
    @Override
    public void onChange(RealmResults<T> results) {
      Log.d(TAG, "onChange: " + results);
      setValue(results);
    }
  };

  public RealmLiveData(RealmResults<T> results) {
    this.results = results;
  }

  @Override
  protected void onActive() {
    results.addChangeListener(changeListener);
    super.onActive();
  }

  @Override
  protected void onInactive() {
    results.removeChangeListener(changeListener);
    super.onInactive();
  }

  @Override
  protected void setValue(RealmResults<T> value) {
    super.setValue(value);
  }
}
