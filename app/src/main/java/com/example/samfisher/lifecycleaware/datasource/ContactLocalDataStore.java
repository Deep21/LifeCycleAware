package com.example.samfisher.lifecycleaware.datasource;

import io.reactivex.Observable;
import io.realm.RealmResults;
import java.util.List;

/**
 * Created by Samfisher on 09/01/2018.
 */

public interface ContactLocalDataStore<T> {

  Observable<T> getList();

  Observable<T> getContact(int id);
}
