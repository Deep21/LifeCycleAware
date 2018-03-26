package com.example.samfisher.lifecycleaware.datasource;

import com.example.samfisher.lifecycleaware.realm.RealmTask;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.realm.RealmResults;
import java.util.List;

/**
 * Created by Samfisher on 09/01/2018.
 */

public interface TaskLocalDataStore<T> {

  Observable<List<T>> getList();

  Observable<T> getContact(int id);

  Completable postTask(RealmTask realmTask);

}
