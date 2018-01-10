package com.example.samfisher.lifecycleaware.datasource;

import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Samfisher on 09/01/2018.
 */

public interface ContactRemoteDataStore<T> {

  Observable<List<T>> getList();

  Observable<T> getContact(int id);
}
