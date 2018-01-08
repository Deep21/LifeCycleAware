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
  }

  @Override
  protected void setValue(T value) {
    super.setValue(value);
  }

  @Override
  protected void onActive() {
    compositeDisposable = new CompositeDisposable();
    super.onActive();
  }

  @Override
  protected void onInactive() {
    dispose();
    super.onInactive();
  }

  private void dispose() {
    Log.d("GENERIC", "dispose: ");
    if (!compositeDisposable.isDisposed()) {
      compositeDisposable.dispose();
    }
  }

  public void addDisposable(Disposable disposable) {
    compositeDisposable.add(disposable);
  }
}
