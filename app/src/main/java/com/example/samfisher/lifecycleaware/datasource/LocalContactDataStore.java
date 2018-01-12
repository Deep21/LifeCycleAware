package com.example.samfisher.lifecycleaware.datasource;

import com.example.samfisher.lifecycleaware.Task;
import io.reactivex.Observable;
import io.realm.RealmResults;
import javax.inject.Inject;

/**
 * Created by Samfisher on 09/01/2018.
 */

public class LocalContactDataStore implements ContactLocalDataStore<RealmResults<Task>> {

  @Inject
  public LocalContactDataStore() {
  }

  @Override
  public Observable<RealmResults<Task>> getList() {
    return null;
  }

  @Override
  public Observable<RealmResults<Task>> getContact(int id) {
    return null;
  }


/*
  @Override
  public Observable<RealmResults<Task>> getList() {
    return Observable.create(e -> {
      Realm realm = Realm.getDefaultInstance();
      RealmResults<Task> contactRealmResults = realm.where(Task.class).findAll();
      e.onNext(contactRealmResults);
      e.onComplete();
    });
  }*/
}
