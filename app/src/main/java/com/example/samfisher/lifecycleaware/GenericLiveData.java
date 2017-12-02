package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by Samfisher on 29/11/2017.
 */

public class GenericLiveData<T> extends LiveData<T> {
    private CompositeDisposable compositeDisposable;


    @Override
    protected void setValue(T value) {
        super.setValue(value);
    }

    @Override
    protected void onActive() {
        Timber.i("onActive");
        super.onActive();
    }

    public void get(Observable<T> tObservable) {
        Disposable disposable = tObservable.toFlowable(BackpressureStrategy.LATEST).subscribe(this::setValue);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onInactive() {
        Timber.i("onInactive");
        compositeDisposable.clear();
        super.onInactive();
    }
}
