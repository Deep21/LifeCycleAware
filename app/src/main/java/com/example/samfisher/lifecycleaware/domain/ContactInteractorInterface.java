package com.example.samfisher.lifecycleaware.domain;

import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Samfisher on 11/01/2018.
 */

public interface ContactInteractorInterface<T> {

  Observable<List<T>> getList();

  Observable<T> get(int i);

}
