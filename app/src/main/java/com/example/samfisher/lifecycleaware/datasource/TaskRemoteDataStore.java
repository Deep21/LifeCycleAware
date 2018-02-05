package com.example.samfisher.lifecycleaware.datasource;

import io.reactivex.Completable;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Samfisher on 09/01/2018.
 */

public interface TaskRemoteDataStore<T> {

  Observable<List<T>> getList();

  Observable<T> get(int id);

  Observable<T> post(T t);

  Completable delete(int id);

  Completable edit(T t);

  Observable<List<T>> search(String keyword);
}
