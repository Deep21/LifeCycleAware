package com.example.samfisher.lifecycleaware;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Samfisher on 07/01/2018.
 */

abstract class AbstractUseCase<T, Params> {

  protected abstract Observable<T> getObservable(Params params);

  public Observable<T> excecute(Params params) {
    return getObservable(params)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

}

