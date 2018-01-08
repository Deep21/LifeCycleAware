package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Samfisher on 08/01/2018.
 */

public class RealmLiveData<T extends RealmModel> extends LiveData<RealmResults<T>> {

  public RealmLiveData() {
  }

  @Override
  protected void onActive() {
    super.onActive();
  }

  @Override
  protected void onInactive() {
    super.onInactive();
  }

  @Override
  protected void setValue(RealmResults<T> value) {
    super.setValue(value);
  }
}
