package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Samfisher on 29/11/2017.
 */

public class RxLiveData<T> extends LiveData<T> {

  private CompositeDisposable compositeDisposable;

  public RxLiveData() {
    compositeDisposable = new CompositeDisposable();
  }

  @Override
  protected void setValue(T value) {
    super.setValue(value);
  }

  @Override
  protected void onActive() {
    super.onActive();
  }

  @Override
  protected void onInactive() {
    dispose();
    super.onInactive();
  }

  private void dispose() {
    if (!compositeDisposable.isDisposed()) {
      Log.d("RxLiveData", "dispose: ");
      compositeDisposable.dispose();
    }
  }

  public void addDisposable(Disposable disposable) {
    compositeDisposable.add(disposable);
  }
}
