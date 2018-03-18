package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.samfisher.lifecycleaware.RxLiveData;
import com.example.samfisher.lifecycleaware.SingleLiveEvent;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.domain.interactor.TaskRetrieveInteractor;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.reactivestreams.Subscription;
import org.w3c.dom.Entity;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class TaskDetailViewModel extends ViewModel {

  private static final String TAG = "TaskDetailViewModel";
  private TaskRetrieveInteractor taskInteractor;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MutableLiveData<List<TaskEntity>> data = new MutableLiveData<>();
  private MediatorLiveData<Resource> tMediatorLiveDatas;
  private RxLiveData<Resource<TaskEntity>> listGenericLiveData = new RxLiveData<>();
  private PublishSubject<String> publishSubject = PublishSubject.create();
  private MutableLiveData<Resource<List<TaskEntity>>> resourceMutableLiveData = new MediatorLiveData<>();
  private MediatorLiveData<Resource> mediatorLiveData = new MediatorLiveData<>();
  private SingleLiveEvent<Integer> taskId = new SingleLiveEvent<>();

  @Inject
  public TaskDetailViewModel(TaskRetrieveInteractor taskInteractor) {
    this.taskInteractor = taskInteractor;
    configureAutoComplete();
  }

  public SingleLiveEvent<Integer> getTaskId() {
    return taskId;
  }

  public MediatorLiveData<Resource> getTask() {
    if (tMediatorLiveDatas == null) {
      tMediatorLiveDatas = new MediatorLiveData<>();
      tMediatorLiveDatas.addSource(taskId, this::loadTask);
      tMediatorLiveDatas.addSource(error, resource -> tMediatorLiveDatas.setValue(resource));
    }
    return tMediatorLiveDatas;
  }

  /**
   *
   * @param id
   */
  public void loadTask(int id) {
    taskInteractor.get(id)
        .doOnSubscribe(this::handleDisposable)
        .subscribe(
            taskEntity -> listGenericLiveData.setValue(Resource.success(taskEntity)),
            throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
        );
  }

  public void setPublishSubject(String value) {
    publishSubject.onNext(value);
  }

  /**
   * Search task
   */
  public void configureAutoComplete() {
    publishSubject
        .debounce(300, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .switchMap(s -> taskInteractor.search(s).toObservable()).subscribeOn(Schedulers.io())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(s -> resourceMutableLiveData.setValue(Resource.success(s)), throwable -> Log.d(TAG, "configureAutoComplete: " + throwable));
  }

  public LiveData<Resource> getSearchLiveData() {
    if (mediatorLiveData != null) {
      mediatorLiveData = new MediatorLiveData<>();
      mediatorLiveData.addSource(resourceMutableLiveData, stringResource -> mediatorLiveData.setValue(stringResource));
    }
    return mediatorLiveData;
  }

  private void handleDisposable(Disposable disposable) {
    listGenericLiveData.addDisposable(disposable);
  }

  public void setTaskId(int taskId) {
    this.taskId.setValue(taskId);
  }

  public MutableLiveData<List<TaskEntity>> getData() {
    return data;
  }

  public void setData(List<TaskEntity> data) {
    this.data.setValue(data);
  }
}
